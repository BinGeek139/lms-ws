package com.ptit.gateway;

public class ResponseBodyDto {
    private String code;
    private String message;
    private Object data;

    public ResponseBodyDto() {
    }

    public ResponseBodyDto(String errorCode) {
        this.code = errorCode;
    }

    public ResponseBodyDto(String errorCode, String message) {
        this.code = errorCode;
        this.message = message;
    }

    public ResponseBodyDto(String errorCode, String message, Object data) {
        this.code = errorCode;
        this.message = message;
        this.data = data;
    }

    public static ResponseBodyDto ofCreated( String message,Object data){
        ResponseBodyDto responseData = new ResponseBodyDto();
        responseData.setCode("0");
        responseData.setData(data);
        responseData.setMessage(message);
        return responseData;

    }

    public static ResponseBodyDto ofSuccess( Object data){
        ResponseBodyDto responseData = new ResponseBodyDto();
        responseData.setCode("0");
        responseData.setData(data);
        responseData.setMessage("Thành công");
        return responseData;

    }
//
//    public static ResponseBody ofSuccess() {
//        ResponseBody responseData = new ResponseBody();
//        responseData.setErrorCode(Constants.ERROR_CODE.SUCCESS);
//        return responseData;
//    }
//
//    public static ResponseBody ofSuccess(String message) {
//        ResponseBody responseData = new ResponseBody();
//        responseData.setMessage(message);
//        responseData.setErrorCode(Constants.ERROR_CODE.SUCCESS);
//        return responseData;
//    }
//
//    public static ResponseBody ofSuccess(String message, Object data) {
//        ResponseBody responseData = new ResponseBody();
//        responseData.setMessage(message);
//        responseData.setData(data);
//        responseData.setErrorCode(Constants.ERROR_CODE.SUCCESS);
//        return responseData;
//    }
//
    public static ResponseBodyDto ofFail(String message) {
        ResponseBodyDto responseData = new ResponseBodyDto();
        responseData.setCode("");
//        responseData.setData(data);
        responseData.setMessage(message);
        return responseData;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}