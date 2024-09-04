package com.sparta.spartastudykeep.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Embeddings", url = "http://localhost:8000", configuration = EmbeddingConfiguration.class)
public interface EmbeddingApi {
    @PostMapping("/embeddings")
    Double[] saveData(@RequestBody EmbeddinRequestDto dto);

    @PutMapping("/embeddings")
    Double[] updateData(@RequestBody EmbeddinRequestDto dto);

    @DeleteMapping("/embeddings")
    void deleteData(@RequestBody EmbeddingDeleteRequestDto dto);

    @PostMapping("/similarity")
    SimilarityResponseDto[] getSimilarText(@RequestBody SimilarRequestDto dto);
}
