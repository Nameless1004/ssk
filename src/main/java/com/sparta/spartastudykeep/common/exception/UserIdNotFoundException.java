package com.sparta.spartastudykeep.common.exception;

public class UserIdNotFoundException extends InvalidIdException {

    public UserIdNotFoundException(Long id) {
        super("User with id " + id + " not found");
    }
}
