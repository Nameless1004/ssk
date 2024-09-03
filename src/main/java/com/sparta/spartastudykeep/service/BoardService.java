package com.sparta.spartastudykeep.service;

import com.sparta.spartastudykeep.dto.*;
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
    public BoardSaveResponseDto saveBoard(BoardSaveRequestDto boardSaveRequestDto) {
        Board newBoard = new Board( boardSaveRequestDto.getUsername(),
                boardSaveRequestDto.getTitle(),
                boardSaveRequestDto.getContent()
        );

        Board saveBoard = boardRepository.save(newBoard);

        return new BoardSaveResponseDto( saveBoard.getId(),
                saveBoard.getUsername(),
                saveBoard.getTitle(),
                saveBoard.getContent()
        );
    }

    public List<BoardGetTitleResponseDto> getAllBoard() {
        List<Board> boardList = boardRepository.findAll();

        List<BoardGetTitleResponseDto> dtoList = new ArrayList<>();

        for (Board board : boardList) {
            BoardGetTitleResponseDto dto = new BoardGetTitleResponseDto(board.getTitle());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public BoardGetDetailResponseDto getDetailBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("ERROR!! 해당 게시글을 찾을 수 없습니다."));

        return new BoardGetDetailResponseDto( board.getId(),
                board.getUsername(),
                board.getTitle(),
                board.getContent()
        );
    }

    @Transactional
    public BoardUpdateTitleResponseDto updateBoardTitle(Long boardId, BoardUpdateTitleRequestDto boardUpdateTitleRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("ERROR!! 해당 게시글을 찾을 수 없습니다."));

        board.updateTitle(boardUpdateTitleRequestDto.getTitle());

        return new BoardUpdateTitleResponseDto( board.getId(),
                board.getUsername(),
                board.getUsername(),
                board.getContent()
        );
    }

    @Transactional
    public BoardUpdateContentResponseDto updateBoardContent(Long boardId, BoardUpdateContentRequestDto boardUpdateContentRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("ERROR!! 해당 게시글을 찾을 수 없습니다."));

        board.updateContent(boardUpdateContentRequestDto.getContent());

        return new BoardUpdateContentResponseDto( board.getId(),
                board.getUsername(),
                board.getTitle(),
                board.getContent()
        );
    }

    @Transactional
    public void deleteBoard(Long boardId) { boardRepository.deleteById(boardId);
    }
}


