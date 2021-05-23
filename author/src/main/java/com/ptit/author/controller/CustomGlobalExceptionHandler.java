package com.ptit.author.controller;


import com.ptit.author.config.ResponseBodyDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        HashMap<String, String> fieldErrors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        ResponseBodyDto responseBodyDto = new ResponseBodyDto();
        responseBodyDto.setMessage("Lỗi Hệ thống");
        responseBodyDto.setData(fieldErrors);
        responseBodyDto.setCode("1");
        return ResponseEntity.ok(responseBodyDto);

    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    ResponseEntity<ResponseBodyDto > handleServerError(Exception ex, WebRequest request) {

        ResponseBodyDto responseBodyDto = new ResponseBodyDto();
        responseBodyDto.setMessage(ex.getMessage());
        responseBodyDto.setCode("1");
        LoggerFactory.getLogger(this.getClass()).error(ex.getMessage(),ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBodyDto);
    }


}
