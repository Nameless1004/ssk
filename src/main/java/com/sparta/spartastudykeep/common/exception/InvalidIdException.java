package com.sparta.spartastudykeep.common.exception;

public class InvalidIdException extends IllegalArgumentException {

    public InvalidIdException() {
        super("id가 존재하지 않습니다.");
    }

    public InvalidIdException(String message) {
        super(message);
    }
}
