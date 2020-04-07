package com.quinn.app.common.constans;

public enum ResponseEnum {

    code_000000("success"),
    code_100000("系统异常"),
    code_100001("数据不存在"),
    code_100002("设备ID不能为空"),
    code_100003("TOPIC类型异常"),
    code_100004("功能ID不能为空"),
    code_100005("功能ID对应的功能不存在"),






    code_200001("调用阿里IOT获取设备信息接口失败"),


    code_999999("系统异常，请稍后再试");

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResponseEnum(String message) {
        this.message = message;
    }

    public ResponseUtils result() {
        return result(null);
    }

//    public ResponseUtils result(Object data) {
//        ResponseUtils restResult = new ResponseUtils();
//        restResult.setCode(this.name().replace("code_", ""));
//        restResult.setMessage(this.message);
//        restResult.setData(data);
//        return restResult;
//    }

    public <T> ResponseUtils result(T data) {
        ResponseUtils<T> restResult = new ResponseUtils<>();
        restResult.setCode(this.name().replace("code_", ""));
        restResult.setMessage(this.message);
        restResult.setData(data);
        return restResult;

    }




}
