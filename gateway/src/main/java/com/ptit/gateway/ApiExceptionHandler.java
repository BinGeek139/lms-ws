package com.ptit.gateway;


import com.netflix.zuul.exception.ZuulException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class ApiExceptionHandler {
    /**
     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây
     */
    @ExceptionHandler(Exception.class)
    public @ResponseBody
    ResponseEntity<ResponseBodyDto> handleServerError(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseBodyDto dtoResult = new ResponseBodyDto();
        dtoResult.setCode("1");
        dtoResult.setMessage(ex.getMessage());
        LoggerFactory.getLogger(this.getClass()).error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(dtoResult);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ResponseEntity<ResponseBodyDto> handleException1(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseBodyDto dtoResult = new ResponseBodyDto();
        dtoResult.setCode("2");
        dtoResult.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(dtoResult);
    }

    @ExceptionHandler(ZuulException.class)
    public @ResponseBody
    ResponseEntity<ResponseBodyDto> handleException2(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseBodyDto dtoResult = new ResponseBodyDto();
        dtoResult.setCode("2");
        dtoResult.setMessage("Dịch vụ đang bảo trì");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(dtoResult);
    }
}
