package edu.jiangbaiyu.demo.cloud.common.exception;

import edu.jiangbaiyu.demo.cloud.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误：" + e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public R<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常", e);
        return R.error(e.getCode(), e.getMessage());
    }
}