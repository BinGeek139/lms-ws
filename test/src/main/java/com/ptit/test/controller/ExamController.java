package com.ptit.test.controller;

import com.ptit.test.config.GenericMapper;
import com.ptit.test.config.ResponseBodyDto;
import com.ptit.test.dto.*;
import com.ptit.test.entity.*;
import com.ptit.test.repository.*;
import com.ptit.test.util.ResponseCodeEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/exam")
@RestController
@CrossOrigin(origins = "*")
public class ExamController {
    @Autowired
    ExamRepository examRepository;

    @Autowired
    GenericMapper genericMapper;

    @Autowired
    ClazzRepository clazzRepository;

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    ResultDetailRepository resultDetailRepository;
    @Autowired
    UserRepository userRepository;
@Autowired
QuestionRepository questionRepository;

    @Transactional
    @GetMapping("{code}")
    @ApiOperation(value = "Lấy bài thi theo code")
    public ResponseEntity<ResponseBodyDto> getExamByCode(@PathVariable("code") String code, @RequestHeader(name = "user_id") String userId) throws Exception {
        var exams = examRepository.findByCode(code);
        ResponseBodyDto responseBodyDto;
        if (exams.size() > 0) {
            User user=userRepository.findById(userId).orElse(null);
            Exam exam = exams.get(0);
            ExamDto examDto = genericMapper.mapToTypeNotNullProperty(exam, ExamDto.class);
            Result result = new Result();
            result.setExamId(exam.getId());
            result.setUser(user);
            result.setDeadline(Instant.now().plusSeconds(exam.getTimeLimit() + 2));
            Result resultSave = resultRepository.save(result);
            List<Question> questions = questionRepository.findByExam_Id(exam.getId());
            List<QuestionDto> questionDtos = new ArrayList<>();
            for (Question question : questions) {
                QuestionDto questionDto = new QuestionDto();
                genericMapper.mapSrcToDestNotNullProperty(question, questionDto);
                List<Answer> answers = question.getAnswers();
                List<AnswerDto> answerDtos = genericMapper.mapToListOfTypeNotNullProperty(answers, AnswerDto.class);
                List<Integer> order = genOrder(answerDtos.size());

                for (int i = 0; i < answerDtos.size(); i++) {
                    answerDtos.get(i).setOrdinal(order.get(i));
                    ResultDetail resultDetail = new ResultDetail();
                    resultDetail.setAnswer(answers.get(i));
                    resultDetail.setIsPick(false);
                    resultDetail.setResult(resultSave);
                    resultDetail.setOrdinal(order.get(i));
                    resultDetailRepository.save(resultDetail);
                }
                questionDto.setAnswerDtos(answerDtos);
                questionDtos.add(questionDto);
            }

            examDto.setQuestionDtoList(questionDtos);
            examDto.setIdResult(result.getId());
            responseBodyDto = new ResponseBodyDto(ResponseCodeEnum.R_200.name(), "Thành công", examDto);
        } else {
            responseBodyDto = new ResponseBodyDto(ResponseCodeEnum.R_404.name(), "Bài kiểm tra không tồn tại", ExamDto.class);
        }
        return ResponseEntity.ok(responseBodyDto);
    }

    private List<Integer> genOrder(int size) {
        Set<Integer> integerSet = new LinkedHashSet<Integer>();
        Random rng = new Random();
        while (integerSet.size() < size) {
            integerSet.add(rng.nextInt(size) + 1);
        }
        List<Integer> integers = integerSet.stream().collect(Collectors.toList());
        return integers;
    }

@Autowired
AnswerRepository answerRepository;
    @ApiOperation(value = "nộp bài thi",
            notes = "resultId: resultId lấy ở phần response của api lấy bài thi theo id \n" +
                    "idAnswer: list Danh sách các đáp án mà người dùng chọn")
    @Transactional
    @PostMapping("submit/{idResult}")
    public ResponseEntity<ResponseBodyDto> submit(@RequestBody Submit submit) throws Exception {
        Optional<Result> optionalResult = resultRepository.findById(submit.getResultId());
        Map<String,Boolean> map=new HashMap<>();
        if(!optionalResult.isPresent()){
            throw new Exception("Mã Bài thi không tồn tại");
        }
//        if(Instant.now().compareTo(optionalResult.get().getDeadline()) > 0){
//            throw new Exception("Đã hết thời gian nộp bài");
//        }
        int quantityCorrect=0;
        String id=optionalResult.get().getId();
        for (String s : submit.getIdAnswer()) {
            Boolean isTrue=Boolean.FALSE;
            Integer check=answerRepository.checkAnswer(s);
               if(Integer.valueOf(1).equals(check)){
                        isTrue=Boolean.TRUE;
                        quantityCorrect++;
                    }

        }
        ResultSubmitDto resultSubmitDto=new ResultSubmitDto();
        resultSubmitDto.setResults(map);
        Optional<Exam> byId = examRepository.findById(optionalResult.get().getExamId());
        Integer quantity=byId.get().getQuantity();
        resultSubmitDto.setQuantity(quantity);
        resultSubmitDto.setQuantityCorrect(quantityCorrect);
        ResponseBodyDto responseBodyDto = ResponseBodyDto.ofSuccess(resultSubmitDto);
        return ResponseEntity.ok(responseBodyDto);
    }
    @Autowired
    UserClazzRepository userClazzRepository;

    @ApiOperation(value = "Lấy danh sách lớp học theo user đang đăng nhập",
            notes = "user_id: id của user được trả về khi đăng nhập ")
    @Transactional
    @GetMapping("clazz")
    public ResponseEntity<ResponseBodyDto> getAllByUserId(@RequestHeader(name = "user_id") String userId){
        List<UserClazz> byUser_id=userClazzRepository.findByUser_Id(userId);
        List<Clazz> collect = byUser_id.stream().map(userClazz -> {
            return userClazz.getClazz();
        }).collect(Collectors.toList());
        List<ClazzResponse> clazzResponses = genericMapper.mapToListOfType(collect, ClazzResponse.class);
        return ResponseEntity.ok(ResponseBodyDto.ofSuccess(clazzResponses));
    }

    @ApiOperation(value = "Tham gia một lớp học",
            notes = "user_id: id của user được trả về khi đăng nhập \n " +
                    "code: Mã lớp, được lấy ở bảng clazz, trường code")
    @Transactional
    @GetMapping("clazz/join")
    public ResponseEntity<ResponseBodyDto> joinClazz(@RequestHeader(name = "user_id") String userId,
                                                     String code) throws Exception {
        var opClass = clazzRepository.findByCode(code);
        if(opClass.size() == 0){
            throw new Exception("Lớp không tồn tại");
        }
        var optionalUser= userRepository.findById(userId);
        if(!optionalUser.isPresent()){
            throw new Exception("Sinh Viên không tồn tại");
        }
        UserClazz userClazz=new UserClazz();
        userClazz.setClazz(opClass.get(0));
        userClazz.setUser(optionalUser.get());
        userClazz.setCreatedAt(new Date(System.currentTimeMillis()));
        userClazz.setCreatedBy(userId);
        userClazzRepository.save(userClazz);
        return ResponseEntity.ok(ResponseBodyDto.ofSuccess("ok"));
    }

    @Transactional
    @GetMapping("clazz/clazz-id")
    @ApiOperation(value = "Lấy danh sách bài thi  theo lớp học ",
            notes = "idClazz: id của clazz")
    public ResponseEntity<ResponseBodyDto> getExam(String idClazz) throws Exception {
       var exams= examRepository.findByClazz_Id(idClazz);
        return ResponseEntity.ok(ResponseBodyDto.ofSuccess(genericMapper.mapToListOfType(exams,ExamResponse.class)));
    }

}
