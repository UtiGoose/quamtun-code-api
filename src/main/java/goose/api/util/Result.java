package goose.api.util;


import lombok.Data;

import java.io.Serializable;



@Data
public class Result<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;

    /**
     * 状态码和消息
     *
     * @param resultCode
     */
    public Result(ResultCode resultCode) {
        this(resultCode.code(), resultCode.message(), null);
    }

    /**
     * 状态码和自定义消息
     *
     * @param resultCode
     * @param message
     */
    public Result(ResultCode resultCode, String message) {
        this(resultCode.code(), message, null);
    }

    /**
     * 状态码和数据
     *
     * @param resultCode
     * @param data
     */
    public Result(ResultCode resultCode, T data) {
        this(resultCode.code(), resultCode.message(), data);
    }

    /**
     * 状态码和数据
     *
     * @param resultCode
     * @param data
     */
    public Result(ResultCode resultCode, String message, T data) {
        this(resultCode.code(), message, data);
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
