package com.ptit.author.controller;

import com.ptit.author.config.Const;
import com.ptit.author.config.HandleSendPhoneNumber;
import com.ptit.author.config.JwtTokenProvider;
import com.ptit.author.config.ResponseBodyDto;
import com.ptit.author.controller.request.ConfirmRequest;
import com.ptit.author.controller.request.LoginRequest;
import com.ptit.author.controller.request.RegisterRequest;
import com.ptit.author.controller.response.ConfirmResponse;
import com.ptit.author.controller.response.LoginResponse;
import com.ptit.author.controller.response.RegisterResponse;
import com.ptit.author.entity.CustomUserDetails;
import com.ptit.author.entity.User;
import com.ptit.author.repo.UserRepo;
import com.ptit.author.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    MinioAdapter minioAdapter;
    @GetMapping("test")
    public String test(@RequestHeader("Authorization") String au){
        return au;
    }
    @GetMapping("test1")
    public String test1(){
        return "ok";
    }

    @PostMapping("login")
    public ResponseEntity<ResponseBodyDto> login(@Valid @RequestBody LoginRequest loginRequest){

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            String jwt ="Bearer "+ jwtTokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
            CustomUserDetails customUserDetails=(CustomUserDetails) authentication.getPrincipal();
            LoginResponse loginResponse=new LoginResponse(jwt,customUserDetails.getUser().getName()
                                        ,customUserDetails.getUser().getUrlImage(),
                    customUserDetails.getUser().getRole().equals(Const.ROLE_HOCSINH) ?"0":"1",customUserDetails.getUser().getId());
            return ResponseEntity.ok(ResponseBodyDto.ofSuccess(loginResponse));
        } catch (Exception e){
            return ResponseEntity.ok(ResponseBodyDto.ofFail("Sai tài khoản hoặc mật khẩu"));
        }

    }

    @Autowired
    UserRepo userRepository;
    @Autowired
    MailService mailService;
    @PostMapping("register")
    public ResponseEntity<ResponseBodyDto> register(@Valid RegisterRequest register) throws Exception {
        String emailOrPhone=register.getEmailOrPhoneNumber().trim();
        User user1 = userRepository.findByUsername(register.getUserName()).orElse(null);
        if(user1 != null){
            throw new Exception("Username đã tồn tại");
        }
        User user=new User();
        String code=genCode(6);
        String contentSend="Chào mừng bạn đến với hệ thống Lms Ptit.\n" +
                "Mã xác thực của bạn là "+code;
        if( Pattern.matches("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$",emailOrPhone)){
            user.setEmail(emailOrPhone);
            mailService.sendEmail(emailOrPhone,contentSend);
        }

        if(Pattern.matches("^\\s*(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})\\s*$",emailOrPhone)){
            user.setPhoneNumber(emailOrPhone);
            HandleSendPhoneNumber.sendMessage(contentSend,emailOrPhone);
        }
        if(Objects.isNull(user.getPhoneNumber()) && Objects.isNull(user.getEmail()) ){
            throw new Exception("Vui lòng nhập số điện thoại hoặc email hợp lệ");
        }
        String urlImage="";
        user.setTokenReset(code);
        user.setUrlImage(urlImage);
        user.setName(register.getName());
        user.setUsername(register.getUserName());
        user.setPassword(new BCryptPasswordEncoder().encode(register.getPassword()));
        if("1".equals(register.getRole())){
            user.setRole(Const.ROLE_GIAOVIEN);
        }else {
            user.setRole(Const.ROLE_HOCSINH);
        }
        user.setActiveFlg(1);
        user.setIsDeleted(0);
        User save = userRepository.save(user);
        RegisterResponse registerResponse=new RegisterResponse(save.getId());
        return ResponseEntity.ok(ResponseBodyDto.ofSuccess(registerResponse));

    }
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/confirm-code")
    public ResponseEntity<ResponseBodyDto> confirm(@Valid @RequestBody ConfirmRequest confirmRequest) throws Exception {
        User user1 = userRepository.findById(confirmRequest.getIdUser()).orElse(null);
        if(user1 == null){
            throw new Exception("User chưa được đăng kí");
        }
        if(!Const.NOT_ACTIVE.equals(user1.getActiveFlg())){
            throw new Exception("Tài khoản đã được kích hoạt trước đó");
        }
        if(!confirmRequest.getCode().equals(user1.getTokenReset())){
            throw new Exception("Mã xác thực không hợp lệ");
        }
        user1.setActiveFlg(1);
        userRepository.save(user1);
        String jwt=jwtTokenProvider.generateToken(new CustomUserDetails(user1));
        LoginResponse loginResponse=new LoginResponse(jwt,user1.getName()
                ,user1.getUrlImage(),user1.getRole().equals(Const.ROLE_HOCSINH) ?"0":"1" ,
                user1.getId());
        return ResponseEntity.ok(ResponseBodyDto.ofSuccess(loginResponse));
    }
    private String genCode(int size){
        String s="";
        Random random=new Random();
        for (int i = 0; i <size ; i++) {
            s+=random.nextInt(9);
        }
        return s;
    }

    @GetMapping
    public String test(){
        return "ok";
    }


}
