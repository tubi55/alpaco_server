package com.example.basic.service;

import com.example.basic.entity.JoinEntity;
import com.example.basic.repository.JoinRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JoinRepo joinRepo;

    // 이름과 이메일을 전달 받아 2개의 정보가 모두 매칭되는 사용자 정보를 DB에서 찾아 반환
    public JoinEntity checkUser(String uname, String email) {
        return joinRepo.findByUnameAndEmail(uname, email);
    }
}
