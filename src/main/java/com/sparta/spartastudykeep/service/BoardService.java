package com.sparta.spartastudykeep.service;

import com.sparta.spartastudykeep.dto.BoardGetTitleResponseDto;
import com.sparta.spartastudykeep.dto.BoardRequestDto;
import com.sparta.spartastudykeep.dto.BoardResponseDto;
import com.sparta.spartastudykeep.dto.FriendResponseDto;
import com.sparta.spartastudykeep.dto.NewsfeedDto;
import com.sparta.spartastudykeep.entity.Board;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.repository.BoardRepository;
import java.util.ArrayList;
import java.util.List;

import com.sparta.spartastudykeep.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final FriendshipService friendshipService;

    @Transactional
    public BoardResponseDto saveBoard(User user, String user_name, BoardRequestDto boardRequestDto) {

        Board newBoard = new Board();
        newBoard.setBoard_title(boardRequestDto.getBoard_title());
        newBoard.setBoard_contents(boardRequestDto.getBoard_contents());

        Board saveBoard = boardRepository.save(newBoard);

        return new BoardResponseDto(saveBoard, user, user_name);
    }

    public List<BoardGetTitleResponseDto> getAllBoard() {
        List<Board> boardList = boardRepository.findAll();

        List<BoardGetTitleResponseDto> dtoList = new ArrayList<>();

        for (Board board : boardList) {
            BoardGetTitleResponseDto dto = new BoardGetTitleResponseDto(board.getBoard_title());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public BoardResponseDto getDetailBoard(Long boardId, User user, String user_name) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("ERROR!! 해당 게시글을 찾을 수 없습니다."));
            // Notfoundexception 로 바꿔주기, 일단 냅두기
        return new BoardResponseDto(board, user, user_name);
    }

    @Transactional
    public BoardResponseDto updateBoard(User user, String user_name, Long boardId, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("ERROR!! 해당 게시글을 찾을 수 없습니다."));

        // 현재 로그인한 유저와 작성한 유저가 다른 경우
        // 편하신대로 하시면 됩니다.
        if(!user.getId().equals(board.getUser().getId())){
            throw new IllegalArgumentException("작성한 유저가 아닙니다.");
        }

        // 작성한 유저가 맞다.
        board.setBoard_title(boardRequestDto.getBoard_title());
        board.setBoard_contents(boardRequestDto.getBoard_contents());

        Board updatedBoard = boardRepository.save(board);
        return new BoardResponseDto (updatedBoard, user, user_name);
    }

    @Transactional
    public void deleteBoard(User user, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("ERROR!! 해당 게시글을 찾을 수 없습니다."));

        //
        if(!user.getId().equals(board.getUser().getId())){
            throw new IllegalArgumentException("작성한 유저가 아닙니다.");
        }

        boardRepository.deleteById(boardId);
    }

    public Page<NewsfeedDto> getNewsfeed(User user, Pageable pageable) {

        List<FriendResponseDto> friends = friendshipService.getFriendAll(user);
        List<Long> ids = friends.stream().map(FriendResponseDto::getUserId).toList();
        Page<Board> newsfeed = boardRepository.findAllByUserIdIn(ids, pageable);

        // 친구들이 작성한 글 목록 저장함
        return newsfeed.map(NewsfeedDto::new);
    }
}


