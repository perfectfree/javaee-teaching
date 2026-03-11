package edu.jiangbaiyu.demo.springbootrestful.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "统一响应结果")
public class Result<T> {
    @Schema(description = "状态码", example = "200")
    private int code;
    @Schema(description = "消息", example = "成功")
    private String message;
    @Schema(description = "数据")
    private T data;

    // 静态工厂方法
    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.code = 200;
        r.message = "成功";
        r.data = data;
        return r;
    }

    public static <T> Result<T> error(int code, String message) {
        Result<T> r = new Result<>();
        r.code = code;
        r.message = message;
        return r;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}