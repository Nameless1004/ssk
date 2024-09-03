package com.sparta.spartastudykeep.service;

import com.sparta.spartastudykeep.dto.BoardGetTitleResponseDto;
import com.sparta.spartastudykeep.dto.BoardRequestDto;
import com.sparta.spartastudykeep.dto.BoardResponseDto;
import com.sparta.spartastudykeep.entity.Board;
import com.sparta.spartastudykeep.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponseDto saveBoard(BoardRequestDto boardRequestDto) {
        Board newBoard = new Board();
        newBoard.setUser_name(boardRequestDto.getUser_name());
        newBoard.setBoard_title(boardRequestDto.getBoard_title());
        newBoard.setBoard_contents(boardRequestDto.getBoard_contents());

        Board saveBoard = boardRepository.save(newBoard);

        return new BoardResponseDto(saveBoard);
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

    public BoardResponseDto getDetailBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("ERROR!! 해당 게시글을 찾을 수 없습니다."));
            // Notfoundexception 로 바꿔주기, 일단 냅두기
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("ERROR!! 해당 게시글을 찾을 수 없습니다."));

        board.setBoard_title(boardRequestDto.getBoard_title());
        board.setBoard_contents(boardRequestDto.getBoard_contents());

        Board updatedBoard = boardRepository.save(board);

        return new BoardResponseDto (updatedBoard);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }

}


