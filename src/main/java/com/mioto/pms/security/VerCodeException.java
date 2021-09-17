package com.mioto.pms.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author admin
 * @date 2021-08-27 16:34
 */
public class VerCodeException extends AuthenticationException {


    public VerCodeException(String msg) {
        super(msg);
    }

    public VerCodeException(String msg, Throwable t) {
        super(msg, t);
    }
}
