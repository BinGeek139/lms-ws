package com.ptit.management.controler;

import com.ptit.management.config.GenericMapper;
import com.ptit.management.config.ResponseBodyDto;
import com.ptit.management.dto.*;
import com.ptit.management.entity.Answer;
import com.ptit.management.entity.Clazz;
import com.ptit.management.entity.Exam;
import com.ptit.management.entity.Question;
import com.ptit.management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/exam")
@RestController
public class QuizController {
    @Autowired
    ExamRepository examRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @GetMapping("test")
    public String demo() {
        return "ok";
    }

    @Autowired
    GenericMapper genericMapper;
    //    @Transactional
//    @PostMapping
//    public ResponseEntity<ResponseBodyDto> create(@RequestBody QuizRequest quizRequest, @RequestHeader(name = "user_id") String userId) throws Exception {
//        String code = Long.toHexString(Instant.now().getEpochSecond());
//        Exam exam = new Exam();
//        exam.setCreatedBy(userId);
//        exam.setCreatedAt(new Date(System.currentTimeMillis()));
//        exam.setCode(code);
//        exam.setName(quizRequest.getName());
//        exam.setQuantity(quizRequest.getQuantity());
//        exam.setTimeLimit(quizRequest.getTimeLimit());
//        Exam save = examRepository.save(exam);
//        if (!Objects.isNull(quizRequest.getQuestions())) {
//            quizRequest.getQuestions().forEach(questionRequest -> {
//                Question question = new Question();
//                question.setName(questionRequest.getNameQuestion());
//                question.setStatus(1);
//                question.setExam(save);
//                question = questionRepository.save(question);
//
//                Answer answer = new Answer();
//                answer.setName(questionRequest.getAnswer1());
//                answer.setIsTrue(0);
//                answer.setQuestion(question);
//                List<Answer> answers = new ArrayList<>();
//                answers.add(answer);
//
//                answer = new Answer();
//                answer.setName(questionRequest.getAnswer2());
//                answer.setIsTrue(0);
//                answer.setQuestion(question);
//                answers.add(answer);
//
//                answer = new Answer();
//                answer.setName(questionRequest.getAnswer3());
//                answer.setIsTrue(0);
//                answer.setQuestion(question);
//                answers.add(answer);
//
//                answer = new Answer();
//                answer.setName(questionRequest.getAnswer4());
//                answer.setQuestion(question);
//                answer.setIsTrue(0);
//                answer.setQuestion(question);
//                answers.add(answer);
//
//
//                answers.get(questionRequest.getCorrectAnswer() - 1).setIsTrue(1);
//                answerRepository.saveAll(answers);
//            });
//
//
//        }
//
//        QuizResponse quizResponse = new QuizResponse();
//        quizResponse.setCode(save.getCode());
//        quizResponse.setName(save.getName());
//        return ResponseEntity.ok(ResponseBodyDto.ofSuccess(quizResponse));
//    }
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClazzRepository clazzRepository;

    @Transactional
    @PostMapping("question")
    public ResponseEntity<ResponseBodyDto> createQuestion(@RequestBody ExamRequst examRequst, @RequestHeader(name = "user_id") String userId) {
        Optional<Clazz> byId = clazzRepository.findById(examRequst.getClassId());
        Exam exam = new Exam();
        exam.setClazz(byId.get());
        exam.setCreatedBy(userId);
        exam.setTimeLimit(examRequst.getTimeLimit());
        exam.setQuantity(examRequst.getQuantity());
        exam.setName(examRequst.getName());
        String code = Long.toHexString(Instant.now().getEpochSecond());
        exam.setCode(code);
        exam.setCreatedAt(new Date(System.currentTimeMillis()));
        exam = examRepository.save(exam);
        final Exam exam1=exam;
        var listExam = examRequst.getQuestion().stream().map(s -> {
            return new Question(s, 1,exam1);
        }).collect(Collectors.toList());
        List<Question> questions = questionRepository.saveAll(listExam);
        AddExamDto examDto = new AddExamDto();
        examDto.setId(exam.getId());
        var ques = questions.stream().map(question -> {
            return new AddExam( question.getName(),question.getId());
        }).collect(Collectors.toList());
        examDto.setAddExamList(ques);
        return ResponseEntity.ok(ResponseBodyDto.ofSuccess(examDto));
    }

    @GetMapping
    public ResponseEntity<ResponseBodyDto> getAll(String idClazz) {
        List<Exam> byClazz_idTeacher = examRepository.findByClazz_Id(idClazz);
        List<ExamDto> examDtos = genericMapper.mapToListOfType(byClazz_idTeacher, ExamDto.class);
        return ResponseEntity.ok(ResponseBodyDto.ofSuccess(examDtos));
    }

    @Transactional
    @PostMapping("submit")
    public ResponseEntity<ResponseBodyDto> submit(@RequestBody ResultRequest questionRequest) throws Exception {
        Question question = questionRepository.findById(questionRequest.getIdQuestion()).orElse(null);
        if (question == null) {
            throw new Exception("User không tồn tại");
        }
        List<Answer> answers = new ArrayList<>();

        Answer answer = new Answer();
        answer.setName(questionRequest.getAnswer1());
        answer.setIsTrue(0);
        answer.setQuestion(question);
        answers.add(answer);

        answer = new Answer();
        answer.setName(questionRequest.getAnswer2());
        answer.setIsTrue(0);
        answer.setQuestion(question);
        answers.add(answer);

        answer = new Answer();
        answer.setName(questionRequest.getAnswer3());
        answer.setIsTrue(0);
        answer.setQuestion(question);
        answers.add(answer);

        answer = new Answer();
        answer.setName(questionRequest.getAnswer4());
        answer.setQuestion(question);
        answer.setIsTrue(0);
        answer.setQuestion(question);
        answers.add(answer);


        answers.get(questionRequest.getCorrectAnswer() - 1).setIsTrue(1);
        answerRepository.saveAll(answers);
        return ResponseEntity.ok(ResponseBodyDto.ofSuccess("ok"));
    }


}
