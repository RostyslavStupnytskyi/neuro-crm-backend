package com.company.crm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AccountLockedException extends AuthenticationException {
    public AccountLockedException(String msg) {
        super(msg);
    }
}