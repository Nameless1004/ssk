package com.sparta.spartastudykeep.controller;

import com.sparta.spartastudykeep.dto.BoardResponseDto;
import com.sparta.spartastudykeep.security.UserDetailsImpl;
import com.sparta.spartastudykeep.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/api/boards/{id}/bookmarks")
    private ResponseEntity<Void> bookmarking(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable("id") Long id) {
        bookmarkService.addBookmark(userDetails.getUser(), id);
        return ResponseEntity.status(HttpStatus.CREATED)
            .build();
    }

    @DeleteMapping("/api/boards/{id}/bookmarks")
    private ResponseEntity<Void> deleteBookmark(
        @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("id") Long id) {
        bookmarkService.removeBookmark(userDetails.getUser(), id);
        return ResponseEntity.ok()
            .build();
    }

    @DeleteMapping("/api/bookmarks")
    private ResponseEntity<Void> deleteAllBookmarks(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        bookmarkService.removeAllBookmark(userDetails.getUser());
        return ResponseEntity.ok()
            .build();
    }

    @GetMapping("/api/bookmarks")
    private ResponseEntity<Page<BoardResponseDto>> getBookmarkBoards(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardResponseDto> page = bookmarkService.getAllBookmarkedBoards(userDetails.getUser(),
            pageable);
        return ResponseEntity.ok(page);
    }
}
