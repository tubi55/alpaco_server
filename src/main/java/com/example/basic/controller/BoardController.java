package com.example.basic.controller;

import com.example.basic.dto.BoardDTO;
import com.example.basic.entity.BoardEntity;
import com.example.basic.entity.JoinEntity;
import com.example.basic.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;
    
    //게시글 저장
    @PostMapping("/create")
    public Map<String, String> createBoard(@RequestBody BoardDTO boardDTO, HttpSession session) {
        Map<String, String> response = new HashMap<>();

        // 로그인 유저 꺼내기
        JoinEntity loginUser = (JoinEntity) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.put("message", "로그인 후 작성 가능합니다.");
            return response;
        }

        BoardEntity savedBoard = boardService.createBoard(boardDTO, loginUser);
        response.put("message", "게시글 작성 성공");
        response.put("boardId", String.valueOf(savedBoard.getId()));

        return response;
    }

    //  게시글 목록 조회
    @GetMapping("/list")
    public Object getBoardList(HttpSession session) {
        JoinEntity loginUser = (JoinEntity) session.getAttribute("loginUser");

        if (loginUser == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "로그인 후 목록 조회 가능합니다.");
            return response;
        }

        List<BoardDTO> boardList = boardService.getAllBoards();

        // BoardEntity → 필요한 데이터만 뽑아서 가볍게 변환
        List<Map<String, Object>> result = boardList.stream().map(board -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", board.getId());
            map.put("title", board.getTitle());
            map.put("content", board.getContent());
            map.put("writerName", board.getWriterName());
            return map;
        }).collect(Collectors.toList());

        return result;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteBoard(@PathVariable Long id, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        JoinEntity loginUser = (JoinEntity) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.put("message", "로그인 후 삭제 가능합니다.");
            return response;
        }

        boolean deleted = boardService.deleteBoard(id, loginUser);

        if (deleted) {
            response.put("message", "게시글 삭제 성공");
        } else {
            response.put("message", "삭제 권한이 없거나 게시글이 존재하지 않습니다.");
        }

        return response;
    }

    @GetMapping("/{id}")
    public BoardDTO getBoard(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }



    @GetMapping("/loginUser")
    public Map<String, String> getLoginUser(HttpSession session) {
        JoinEntity loginUser = (JoinEntity) session.getAttribute("loginUser");
        Map<String, String> response = new HashMap<>();
        if (loginUser != null) {
            response.put("username", loginUser.getUname());
        } else {
            response.put("username", "");
        }
        return response;
    }

    // 게시글 수정
    @PutMapping("/update/{id}")
    public Map<String, String> updateBoard(@PathVariable Long id, @RequestBody BoardDTO boardDTO, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        JoinEntity loginUser = (JoinEntity) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.put("message", "로그인 후 수정 가능합니다.");
            return response;
        }

        boolean updated = boardService.updateBoard(id, boardDTO, loginUser);

        if (updated) {
            response.put("message", "게시글 수정 성공");
        } else {
            response.put("message", "수정 권한이 없거나 게시글이 존재하지 않습니다.");
        }

        return response;
    }
}