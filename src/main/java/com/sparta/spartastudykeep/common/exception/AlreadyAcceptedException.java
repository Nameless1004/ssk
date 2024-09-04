package com.sparta.spartastudykeep.common.exception;

import org.springframework.dao.DataAccessException;

public class AlreadyAcceptedException extends DataAccessException {

    public AlreadyAcceptedException() {
        super("이미 수락한 요청입니다.");
    }

    public AlreadyAcceptedException(String message) {
        super(message);
    }
}
