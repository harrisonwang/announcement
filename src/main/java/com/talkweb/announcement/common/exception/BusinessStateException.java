package com.talkweb.announcement.common.exception;

/**
 * 业务状态异常，用于处理不符合业务规则的状态操作
 */
public class BusinessStateException extends RuntimeException {

    public BusinessStateException(String message) {
        super(message);
    }

    public BusinessStateException(String message, Throwable cause) {
        super(message, cause);
    }
}