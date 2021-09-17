package com.mioto.pms.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author admin
 * @date 2021-08-27 17:06
 */
public class PhoneValidException extends AuthenticationException {
    public PhoneValidException(String msg) {
        super(msg);
    }

    public PhoneValidException(String msg, Throwable t) {
        super(msg, t);
    }
}
