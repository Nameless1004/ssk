package com.sparta.spartastudykeep.controller;

import com.sparta.spartastudykeep.dto.*;

import com.sparta.spartastudykeep.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/boards")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<BoardResponseDto> saveBoard(@RequestBody BoardRequestDto boardRequestDto){
        return ResponseEntity.ok(boardService.saveBoard(boardRequestDto));
    }

    // 게시글 제목 전체 조회
    @GetMapping
    public ResponseEntity<List<BoardGetTitleResponseDto>> getAllBoard(){
        return ResponseEntity.ok(boardService.getAllBoard());
    }

    // 게시글 단건 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> getDetailBoard(@PathVariable Long boardId){
        return ResponseEntity.ok(boardService.getDetailBoard(boardId));
    }
    
    // 게시글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoardTitle(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto){
        return ResponseEntity.ok(boardService.updateBoard(boardId, boardRequestDto));
    }

    // 게시글 삭제
    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable Long boardId) { boardService.deleteBoard(boardId); }
}

