package com.sparta.spartastudykeep.common.exception;

public class InvalidAdminTokenException extends IllegalArgumentException{
    public InvalidAdminTokenException() {
        super("Invalid Admin Token");
    }
}
