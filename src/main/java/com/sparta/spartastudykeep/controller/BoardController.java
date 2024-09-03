package com.sparta.spartastudykeep.controller;

import com.sparta.spartastudykeep.dto.*;
import com.sparta.spartastudykeep.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 작성
    @PostMapping("/api/boards")
    public ResponseEntity<BoardSaveResponseDto> saveBoard(@RequestBody BoardSaveRequestDto boardSaveRequestDto){
        return ResponseEntity.ok(boardService.saveBoard(boardSaveRequestDto));
    }

    // 게시글 제목 전체 조회
    @GetMapping("/api/boards")
    public ResponseEntity<List<BoardGetTitleResponseDto>> getAllBoard(){
        return ResponseEntity.ok(boardService.getAllBoard());
    }

    // 게시글 단건 조회
    @GetMapping("/api/boards/{boardId}")
    public ResponseEntity<BoardGetDetailResponseDto> getDetailBoard(@PathVariable Long boardId){
        return ResponseEntity.ok(boardService.getDetailBoard(boardId));
    }

    // 게시글 제목 수정
    @PutMapping("/api/boards/{boardId}/title")
    public ResponseEntity<BoardUpdateTitleResponseDto> updateBoardTitle(@PathVariable Long boardId, @RequestBody BoardUpdateTitleRequestDto boardUpdateTitleRequestDto){
        return ResponseEntity.ok(boardService.updateBoardTitle(boardId, boardUpdateTitleRequestDto));
    }

    // 게시글 내용 수정
    @PutMapping("/api/boards/{boardId}/content")
    public ResponseEntity<BoardUpdateContentResponseDto> updateContentTitle(@PathVariable Long boardId, @RequestBody BoardUpdateContentRequestDto boardUpdateContentRequestDto){
        return ResponseEntity.ok(boardService.updateBoardContent(boardId, boardUpdateContentRequestDto));
    }

    // 게시글 삭제
    @DeleteMapping("/api/boards/{boardId}")
    public void deleteBoard(@PathVariable Long boardId) { boardService.deleteBoard(boardId); }
}

