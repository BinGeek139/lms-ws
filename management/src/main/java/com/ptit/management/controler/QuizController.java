package com.ptit.management.controler;

import com.ptit.management.config.ResponseBodyDto;
import com.ptit.management.dto.QuizRequest;
import com.ptit.management.dto.QuizResponse;
import com.ptit.management.entity.Answer;
import com.ptit.management.entity.Clazz;
import com.ptit.management.entity.Exam;
import com.ptit.management.entity.Question;
import com.ptit.management.repository.AnswerRepository;
import com.ptit.management.repository.ExamRepository;
import com.ptit.management.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    @GetMapping
    public String demo() {
        return "ok";
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ResponseBodyDto> create(@RequestBody QuizRequest quizRequest, @RequestHeader(name = "user_id") String userId) throws Exception {
        String code = Long.toHexString(Instant.now().getEpochSecond());
        Exam exam = new Exam();
        exam.setCreatedBy(userId);
        exam.setCreatedAt(new Date(System.currentTimeMillis()));
        exam.setCode(code);
        exam.setName(quizRequest.getName());
        exam.setQuantity(quizRequest.getQuantity());
        exam.setTimeLimit(quizRequest.getTimeLimit());
        Exam save = examRepository.save(exam);
        if (!Objects.isNull(quizRequest.getQuestions())) {
            quizRequest.getQuestions().forEach(questionRequest -> {
                Question question = new Question();
                question.setName(questionRequest.getNameQuestion());
                question.setStatus(1);
                question.setExam(save);
                question = questionRepository.save(question);

                Answer answer = new Answer();
                answer.setName(questionRequest.getAnswer1());
                answer.setIsTrue(0);
                answer.setQuestion(question);
                List<Answer> answers = new ArrayList<>();
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
            });


        }

        QuizResponse quizResponse = new QuizResponse();
        quizResponse.setCode(save.getCode());
        quizResponse.setName(save.getName());
        return ResponseEntity.ok(ResponseBodyDto.ofSuccess(quizResponse));
    }

}
