package com.talkweb.announcement.common.exception;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理请求体参数验证失败异常 (@RequestBody + @Validated)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.warn("请求参数验证失败: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "请求参数验证失败");

        problemDetail.setType(URI.create("https://api.example.com/problems/validation-failed"));
        problemDetail.setTitle("参数验证失败");

        // 添加字段错误详情
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
        problemDetail.setProperty("fieldErrors", fieldErrors);

        return problemDetail;
    }

    /**
     * 处理实体不存在异常
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFound(EntityNotFoundException ex) {
        log.warn("实体不存在: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage());

        problemDetail.setType(URI.create("https://api.example.com/problems/entity-not-found"));
        problemDetail.setTitle("资源不存在");

        return problemDetail;
    }

    /**
     * 处理其他未预期的异常
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex) {
        log.error("系统异常", ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "系统内部错误，请联系管理员");

        problemDetail.setType(URI.create("https://api.example.com/problems/internal-error"));
        problemDetail.setTitle("系统内部错误");

        return problemDetail;
    }
}