package com.sparta.spartastudykeep.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenType {
    ACCESS(10*60*1000), // 10min
    REFRESH(24*60*60*1000); // 24hour

    private final long lifeTime;
}
