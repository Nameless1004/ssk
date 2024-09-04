package com.sparta.spartastudykeep.common.exception;

import org.springframework.dao.DataAccessException;

public class UnAuthorizedAccessException extends DataAccessException {

    public UnAuthorizedAccessException() {
        super("접근이 거부되었습니다. 이 작업을 수행할 권한이 없습니다.");
    }
    public UnAuthorizedAccessException(String msg) {
        super(msg);
    }
}
