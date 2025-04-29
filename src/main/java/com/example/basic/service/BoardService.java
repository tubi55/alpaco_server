package com.example.basic.service;

import com.example.basic.dto.BoardDTO;
import com.example.basic.entity.BoardEntity;
import com.example.basic.entity.JoinEntity;
import com.example.basic.repository.BoardRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepo boardRepo;

    // 회원 정보까지 포함한 게시글 저장 메서드
    public BoardEntity createBoard(BoardDTO boardDTO, JoinEntity loginUser) {
        BoardEntity board = new BoardEntity();
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setWriter(loginUser); // 작성자 설정
        return boardRepo.save(board);
    }

    // 모든 게시물 가져오는 메서드
    public List<BoardDTO> getAllBoards() {
        List<BoardEntity> boardEntities = boardRepo.findAll();

        return boardEntities.stream()
                .map(board -> new BoardDTO(
                        board.getId(),
                        board.getTitle(),
                        board.getContent(),
                        board.getWriter().getUname() // 작성자 이름 가져오기
                ))
                .collect(Collectors.toList());
    }

    // 특정 게시글 아이디의 데이터만 삭제하는 메서드
    public boolean deleteBoard(Long boardId, JoinEntity loginUser) {
        // 1. 해당 게시글 조회
        BoardEntity board = boardRepo.findById(boardId)
                .orElse(null);

        if (board == null) {
            return false; // 게시글 없음
        }

        // 2. 로그인 유저와 게시글 작성자 비교
        if (!board.getWriter().getId().equals(loginUser.getId())) {
            return false; // 작성자가 아님
        }

        // 3. 삭제 진행
        boardRepo.delete(board);
        return true;
    }

    // 특정 아이디의 게시글 데이터만 가져오는 메서드
    public BoardDTO getBoardById(Long id) {
        BoardEntity board = boardRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        return new BoardDTO(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getWriter().getUname()
        );
    }

    // 특정 아이디의 게시글 데이터만 수정하는 메서드
    public boolean updateBoard(Long id, BoardDTO boardDTO, JoinEntity loginUser) {
        BoardEntity board = boardRepo.findById(id)
                .orElse(null);

        if (board == null) {
            return false;
        }

        // 작성자만 수정 가능
        if (!board.getWriter().getId().equals(loginUser.getId())) {
            return false;
        }

        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        boardRepo.save(board);
        return true;
    }

}