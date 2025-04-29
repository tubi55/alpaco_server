package com.example.basic.controller;
import com.example.basic.dto.JoinDTO;
import com.example.basic.entity.JoinEntity;
import com.example.basic.service.JoinService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JoinController {
    private final JoinService joinService;

    // 회원 정보 저장
    @PostMapping("/join/create")
    public Map<String, Object> create(@RequestBody JoinDTO formDTO ){
        String result = joinService.processJoin(formDTO);

       Map<String, Object> response =  new HashMap<>();
       response.put("message", result);
        return response;
    }
    
    // 모든 정보 가져오는 컨틀로러
    @GetMapping("/admin/all")
    public List<JoinEntity> getAllUsers() {
        return joinService.getAllUsers();
    }


    //회원 정보 출력 (+ 인증유무에 따른 접근 권한 설정)
    @GetMapping("/admin")
    public Object showAdminPage(@RequestParam(defaultValue="0") int page, HttpSession session){

        if(session.getAttribute("loginUser") == null){
            Map<String, Object> response = new HashMap<>();
            response.put("isLogin", false);
            response.put("message", "로그인이 필요합니다.");
            return response;
        }

        int pageSize = 3;
        Page<JoinEntity> userPage = joinService.getUsersByPage(page, pageSize);

        Map<String, Object> result = new HashMap<>();
        result.put("users", userPage.getContent());
        result.put("currentPage", page);
        result.put("totalPages", userPage.getTotalPages());
        return result;
    }

    // 회원 정보 삭제
    @DeleteMapping("/admin/del/{id}")
    public Map<String, String> delUser(@PathVariable Long id){
        joinService.delete(id);
        Map<String, String> result = new HashMap<>();
        result.put("message", "삭제 성공");
        return result;
    }
    

    //특정 회원 정보만 가져옴
    @GetMapping("/admin/edit/{id}")
    public JoinEntity editUser(@PathVariable Long id){
       return joinService.getUserById(id);
    }

    //가져온 회원 정보 수정
    @PutMapping("/admin/update")
    public Map<String, String> updateUser(@RequestBody JoinEntity formUser){
        joinService.updateUser(formUser);

        Map<String, String> result = new HashMap<>();
        result.put("message", "수정 성공");
        return result;
    }
}

