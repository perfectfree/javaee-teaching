package edu.jiangbaiyu.demo.transactionservice.exception;

import edu.jiangbaiyu.demo.transactionservice.dto.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        e.printStackTrace(); // 便于调试
        return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误: " + e.getMessage());
    }
}