package com.sparta.spartastudykeep.controller;

import com.sparta.spartastudykeep.dto.*;

import com.sparta.spartastudykeep.security.UserDetailsImpl;
import com.sparta.spartastudykeep.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/boards")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<BoardResponseDto> saveBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BoardRequestDto boardRequestDto){
        return ResponseEntity.ok(boardService.saveBoard(userDetails.getUser(), userDetails.getUsername(), boardRequestDto));
    }

    // 게시글 제목 전체 조회
    @GetMapping
    public ResponseEntity<List<BoardGetTitleResponseDto>> getAllBoard(){
        return ResponseEntity.ok(boardService.getAllBoard());
    }

    // 게시글 단건 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> getDetailBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId){
        return ResponseEntity.ok(boardService.getDetailBoard(boardId, userDetails.getUser(), userDetails.getUsername()));
    }
    
    // 게시글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoardTitle(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto){
        return ResponseEntity.ok(boardService.updateBoard(userDetails.getUser(), userDetails.getUsername(), boardId, boardRequestDto));
    }

    // 게시글 삭제
    @DeleteMapping("/{boardId}")
    public void deleteBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId) {
        boardService.deleteBoard( userDetails.getUser() , boardId);
    }

    // 뉴스피드 구현
    // 현재 사용자의 친구 목록에 있는 친구들의 작성글을 다 가져오면 되는 부분
    // 이걸 가져와서 페이징한다.
    @GetMapping("/newsfeed")
    public ResponseEntity<Page<NewsfeedDto>> getNewsfeed(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                          @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NewsfeedDto> newsfeed = boardService.getNewsfeed(userDetails.getUser(), pageable);
        return ResponseEntity.ok(newsfeed);
    }
}

