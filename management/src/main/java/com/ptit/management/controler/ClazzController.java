package com.ptit.management.controler;

import com.ptit.management.config.GenericMapper;
import com.ptit.management.config.ResponseBodyDto;
import com.ptit.management.dto.ClazzRequest;
import com.ptit.management.dto.ClazzResponse;
import com.ptit.management.dto.ResultRequest;
import com.ptit.management.entity.Clazz;
import com.ptit.management.entity.User;
import com.ptit.management.entity.UserClazz;
import com.ptit.management.repository.ClazzRepository;
import com.ptit.management.repository.UserClazzRepository;
import com.ptit.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@CrossOrigin("*")
@RequestMapping("/clazz")
@RestController
public class ClazzController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClazzRepository clazzRepository;
    @Autowired
    UserClazzRepository userClazzRepository;
    @Autowired
    GenericMapper genericMapper;

    @Transactional
    @PostMapping
    public ResponseEntity<ResponseBodyDto> create(@Valid @RequestBody ClazzRequest clazzRequest,
                                                  @RequestHeader(name = "user_id") String userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            throw new Exception("Vui lòng đăng nhập");
        }
        String code = Long.toHexString(Instant.now().getEpochSecond());
        Clazz clazz=new Clazz();
        clazz.setName(clazzRequest.getName());
        clazz.setDescription(clazzRequest.getDescription());
        clazz.setStatus(1);
        clazz.setCode(code);
        clazz.setIdTeacher(userId);
        clazzRepository.save(clazz);

        return ResponseEntity.ok(ResponseBodyDto.ofSuccess(code));
    }


    @GetMapping
    public ResponseEntity<ResponseBodyDto> getAll(@RequestHeader(name = "user_id") String userId){
        List<Clazz> byUser_id = clazzRepository.findByIdTeacher(userId);
        List<ClazzResponse> clazzResponses = genericMapper.mapToListOfType(byUser_id, ClazzResponse.class);
        return ResponseEntity.ok(ResponseBodyDto.ofSuccess(clazzResponses));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<ResponseBodyDto> deleteById(@PathVariable("id")String id) throws Exception {
        Optional<Clazz> byId = clazzRepository.findById(id);
        if(!byId.isPresent()){
            throw new Exception("Lớp không tồn tại");
        }
        Clazz clazz = byId.get();
        clazz.setStatus(0);
        clazzRepository.save(clazz);
        return ResponseEntity.ok(ResponseBodyDto.ofSuccess("ok"));
    }

}
