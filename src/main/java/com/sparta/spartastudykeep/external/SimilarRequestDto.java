package com.sparta.spartastudykeep.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimilarRequestDto {
    private String text;
    private long limit;
}
