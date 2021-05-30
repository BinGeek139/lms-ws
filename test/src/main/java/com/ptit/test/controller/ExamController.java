package com.ptit.test.controller;

import com.ptit.test.config.GenericMapper;
import com.ptit.test.config.ResponseBodyDto;
import com.ptit.test.dto.*;
import com.ptit.test.entity.*;
import com.ptit.test.repository.*;
import com.ptit.test.util.ResponseCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/exam")
@RestController
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

    @GetMapping
    public String demo(){
        return "ok";
    }
    @Transactional
    @GetMapping("{code}")
    public ResponseEntity<ResponseBodyDto> getExamByCode(@PathVariable("code") String code, @RequestHeader(name = "user_id") String userId) throws Exception {
        List<Exam> exams = examRepository.findByCode(code);
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
            List<Question> questions = exam.getQuestions();
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


    @Transactional
    @PostMapping("submit/{idResult}")
    public ResponseEntity<ResponseBodyDto> submit(@RequestBody Submit submit) throws Exception {
        Optional<Result> optionalResult = resultRepository.findById(submit.getResultId());


        Map<String,Boolean> map=new HashMap<>();
        if(!optionalResult.isPresent()){
            throw new Exception("Mã Bài thi không tồn tại");
        }
        if(Instant.now().compareTo(optionalResult.get().getDeadline()) > 0){
            throw new Exception("Đã hết thời gian nộp bài");
        }
        int quantityCorrect=0;
        List<ResultDetail> resultDetailByResult = resultDetailRepository.findResultDetailByResult(optionalResult.get());
        for (String s : submit.getIdAnswer()) {
            Boolean isTrue=Boolean.FALSE;
            for (ResultDetail resultDetail : resultDetailByResult) {
                if(resultDetail.getResult().getExamId().equals(s)){
                    resultDetail.setIsPick(Boolean.TRUE);
                    if(resultDetail.getAnswer().getIsTrue().equals(1)){
                        isTrue=Boolean.TRUE;
                        quantityCorrect++;
                    }
                    break;
                }
            }
            map.put(s,isTrue);
        }
        ResultSubmitDto resultSubmitDto=new ResultSubmitDto();
        resultSubmitDto.setResults(map);
        Optional<Exam> byId = examRepository.findById(optionalResult.get().getExamId());
        Integer quantity=byId.get().getQuantity();
        resultSubmitDto.setQuantityCorrect(quantity);
        resultSubmitDto.setQuantityCorrect(quantityCorrect);
        ResponseBodyDto responseBodyDto = ResponseBodyDto.ofSuccess(resultSubmitDto);
        return ResponseEntity.ok(responseBodyDto);
    }

}
