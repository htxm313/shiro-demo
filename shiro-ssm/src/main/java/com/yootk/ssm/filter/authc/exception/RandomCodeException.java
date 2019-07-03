package com.yootk.ssm.filter.authc.exception;

import javax.naming.AuthenticationException;

public class RandomCodeException extends AuthenticationException {
    public RandomCodeException() {}
    public RandomCodeException(String msg) {
        super(msg);
    }
}

