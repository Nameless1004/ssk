package com.sparta.spartastudykeep.external;

import feign.FeignException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "EmbeddingService")
@Service
@RequiredArgsConstructor
public class EmbeddingService {

    private final EmbeddingApi embeddingApi;

    public List<SimilarityResponseDto> getSimilarText(SimilarRequestDto text) {
        SimilarityResponseDto[] similar = embeddingApi.getSimilarText(text);
        return Arrays.stream(similar)
            .toList();
    }

    public void embeddingAndSave(EmbeddinRequestDto text) {
        try {
            Double[] data = embeddingApi.saveData(text);
            String string = Arrays.toString(data);
            log.info(string);
        } catch (FeignException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    public void updateEmbeddingData(EmbeddinRequestDto reuqestDto) {
        Double[] data = embeddingApi.updateData(reuqestDto);
        String string = Arrays.toString(data);
        log.info(string);
    }

    public void deleteEmbeddingData(EmbeddingDeleteRequestDto requestDto) {
        embeddingApi.deleteData(requestDto);
    }

}
