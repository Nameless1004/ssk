package com.sparta.spartastudykeep.service;

import com.sparta.spartastudykeep.dto.BoardResponseDto;
import com.sparta.spartastudykeep.entity.Board;
import com.sparta.spartastudykeep.entity.Bookmark;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.repository.BoardRepository;
import com.sparta.spartastudykeep.repository.BookmarkRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final BoardRepository boardRepository;

    /**
     * 유저의 북마크에 보드를 추가합니다.
     * @param user 현재 로그인 유저
     * @param boardId 추가할 보드 아이디
     */
    public void addBookmark(User user, Long boardId) {
        Board board = getBoard(boardId);

        Bookmark bookmark = new Bookmark(user, board);
        bookmarkRepository.save(bookmark);
    }

    /**
     * 유저의 북마크에서 해당 보드를 제거합니다.
     * @param user 현재 로그인 유저
     * @param boardId 제거할 보드 아이디
     */
    public void removeBookmark(User user, Long boardId) {
        bookmarkRepository.deleteByUserIdAndBoardId(user.getId(), boardId);
    }

    /**
     * 유저가 북마크에 저장한 글들을 페이징 해서 가져옵니다.
     * @param user 현재 로그인 유저
     * @param pageable 페이징
     * @return
     */
    public Page<BoardResponseDto> getAllBookmarkedBoards(User user, Pageable pageable) {
        Page<Bookmark> bookmarks = bookmarkRepository.findAllByUser(user, pageable);
        return bookmarks.map(x -> new BoardResponseDto(x.getBoard()));
    }

    /**
     * 유저가 저장한 모든 북마크를 삭제합니다.
     * @param user 현재 로그인 유저
     */
    public void removeAllBookmark(User user) {
        if(!bookmarkRepository.existsByUser(user)) {
            throw new IllegalArgumentException("삭제할 북마크가 없습니다.");
        }
        bookmarkRepository.deleteAllByUser(user);
    }

    private Board getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보드 아이디입니다."));
        return board;
    }
}
