package com.sparta.spartastudykeep.service;

import com.sparta.spartastudykeep.common.exception.InvalidIdException;
import com.sparta.spartastudykeep.common.exception.UnAuthorizedAccessException;
import com.sparta.spartastudykeep.dto.BoardRequestDto;
import com.sparta.spartastudykeep.dto.BoardResponseDto;
import com.sparta.spartastudykeep.dto.FriendResponseDto;
import com.sparta.spartastudykeep.dto.NewsfeedDto;
import com.sparta.spartastudykeep.entity.Board;
import com.sparta.spartastudykeep.entity.User;
import com.sparta.spartastudykeep.external.EmbeddinRequestDto;
import com.sparta.spartastudykeep.external.EmbeddingDeleteRequestDto;
import com.sparta.spartastudykeep.external.EmbeddingService;
import com.sparta.spartastudykeep.external.SimilarRequestDto;
import com.sparta.spartastudykeep.external.SimilarityResponseDto;
import com.sparta.spartastudykeep.repository.BoardRepository;
import com.sparta.spartastudykeep.repository.BookmarkRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final BookmarkRepository bookmarkRepository;
    private final FriendshipService friendshipService;
    private final EmbeddingService embeddingService;

    @Transactional
    public BoardResponseDto saveBoard(User user, BoardRequestDto boardRequestDto) {

        Board newBoard = new Board();
        newBoard.setBoard_title(boardRequestDto.getBoard_title());
        newBoard.setBoard_contents(boardRequestDto.getBoard_contents());
        newBoard.setUser(user);
        newBoard.setUser_name(user.getUsername());

        Board saveBoard = boardRepository.save(newBoard);
        EmbeddinRequestDto dto = new EmbeddinRequestDto(saveBoard.getId(), saveBoard.getBoard_title());
        embeddingService.embeddingAndSave(dto);

        return new BoardResponseDto(saveBoard, user);
    }

//    // 게시글 조회
//    public List<BoardGetTitleResponseDto> getAllBoard() {
//        List<Board> boardList = boardRepository.findAll();
//
//        List<BoardGetTitleResponseDto> dtoList = new ArrayList<>();
//
//        for (Board board : boardList) {
//            BoardGetTitleResponseDto dto = new BoardGetTitleResponseDto(board.getBoard_title());
//            dtoList.add(dto);
//        }
//        return dtoList;
//    }

    public BoardResponseDto getDetailBoard(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new InvalidIdException("ERROR!! 해당 게시글을 찾을 수 없습니다."));
        // Notfoundexception 로 바꿔주기, 일단 냅두기
        return new BoardResponseDto(board, user);
    }

    @Transactional
    public BoardResponseDto updateBoard(User user, String user_name, Long boardId,
        BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new InvalidIdException("ERROR!! 해당 게시글을 찾을 수 없습니다."));

        // 현재 로그인한 유저와 작성한 유저가 다른 경우
        // 편하신대로 하시면 됩니다.
        if (!user.getId()
            .equals(board.getUser()
                .getId())) {
            throw new UnAuthorizedAccessException();
        }

        // 작성한 유저가 맞다.
        board.setBoard_title(boardRequestDto.getBoard_title());
        board.setBoard_contents(boardRequestDto.getBoard_contents());

        Board updatedBoard = boardRepository.save(board);

        EmbeddinRequestDto dto = new EmbeddinRequestDto(updatedBoard.getId(), updatedBoard.getBoard_title());
        embeddingService.updateEmbeddingData(dto);

        return new BoardResponseDto(updatedBoard, user);
    }

    @Transactional
    public void deleteBoard(User user, Long boardId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new InvalidIdException("ERROR!! 해당 게시글을 찾을 수 없습니다."));

        //
        if (!user.getId()
            .equals(board.getUser()
                .getId())) {
            throw new UnAuthorizedAccessException();
        }
        EmbeddingDeleteRequestDto dto = new EmbeddingDeleteRequestDto(board.getId());
        embeddingService.deleteEmbeddingData(dto);
        bookmarkRepository.deleteAllByUserAndBoard(user, board);

        boardRepository.deleteById(boardId);
    }

    public Page<NewsfeedDto> getNewsfeed(User user, Pageable pageable) {

        List<FriendResponseDto> friends = friendshipService.getFriendAll(user);
        List<Long> ids = friends.stream()
            .map(FriendResponseDto::getUserId)
            .collect(Collectors.toList());
        ids.add(user.getId());

        Page<Board> newsfeed = boardRepository.findAllByUserIdIn(ids, pageable);

        // 친구들이 작성한 글 목록 저장함
        return newsfeed.map(NewsfeedDto::new);
    }

    public List<BoardResponseDto> FindSimilarBoard(String search, Long limit) {
        List<SimilarityResponseDto> similarText = embeddingService.getSimilarText(new SimilarRequestDto(search, limit));
        List<Long> id = similarText.stream()
            .map(x -> x.getBoardId()
            )
            .toList();
        List<Board> allById = boardRepository.findAllById(id);

        List<BoardResponseDto> result = new ArrayList<>();
        for(SimilarityResponseDto dto : similarText) {
            long boardId = dto.getBoardId();
            Board board = allById.stream()
                .filter(x -> x.getId() == boardId)
                .findFirst()
                .get();
            result.add(new BoardResponseDto(board));
        }

        return result;
    }
}


