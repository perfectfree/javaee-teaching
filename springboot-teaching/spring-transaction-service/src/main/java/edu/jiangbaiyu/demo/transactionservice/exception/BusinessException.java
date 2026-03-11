package edu.jiangbaiyu.demo.transactionservice.exception;

/**
 * 自定义业务异常
 */
public class BusinessException extends RuntimeException {
    private int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}