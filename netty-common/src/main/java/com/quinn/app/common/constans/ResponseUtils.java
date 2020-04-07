package com.quinn.app.common.constans;


import lombok.Data;

@Data
public class ResponseUtils<T> {



    /**
     * 消息码
     */
    private String code;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public ResponseUtils() {

    }


}
