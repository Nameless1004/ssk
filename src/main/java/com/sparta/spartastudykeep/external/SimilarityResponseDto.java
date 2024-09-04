package com.sparta.spartastudykeep.external;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimilarityResponseDto {
    Long boardId;
    String title;
    double similarity;
}
