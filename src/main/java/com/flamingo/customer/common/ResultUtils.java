package com.flamingo.customer.common;

/**
 * @author 咏鹅
 * @version 1.0
 * @description TODO
 * @date 2023/5/30 18:56
 */
public class ResultUtils {

    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"OK");
    }

    public static BaseResponse error(ErrorCode errorCode,String message,String description){
        return new BaseResponse<>(errorCode.getCode(),message,description);
    }

    public static BaseResponse error(int code,String message,String description){
        return new BaseResponse<>(code,null,message,description);
    }

    public static BaseResponse error(int code,String message){
        return new BaseResponse<>(code,message);
    }

    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }

}
