package com.example.basic.service;

import com.example.basic.dto.JoinDTO;
import com.example.basic.entity.JoinEntity;
import com.example.basic.repository.JoinRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final JoinRepo joinRepo;

    // DTO로 이름, 이메일, 색상 정보를 전달 받아 DB에 저장후 결과 문자값 반환
    public String processJoin(JoinDTO dto) {
        JoinEntity user = new JoinEntity(null, dto.getUname(), dto.getEmail(), dto.getColors() );
        joinRepo.save(user);
        return "회원정보가 저장되었습니다";
    }

    //DB에서 모든 유저 정보 찾아서 리스트 형태로 결과값 반환
    public List<JoinEntity> getAllUsers(){
        return joinRepo.findAll();
    }

    //특정 id값을 전닯다아 해당 사용자의 정보만 DB에서 제거
    public void delete(Long id){
        joinRepo.deleteById(id);
    }

    //특정 id값을 전달받아 해당 사용자의 정보만 반환
    public JoinEntity getUserById(Long id){
        return joinRepo.findById(id).orElseThrow(()-> new RuntimeException("해당 아이디의 유저 없음"));
    }

    //반환된 사용자 정보를 갱신해서 업데이트 (기존 DB에 데이터 존재 유무에 따라 insert, update문 선별 실행됨 dirty checking)
    public  void updateUser(JoinEntity user){
        joinRepo.save(user);
    }

    //인수로 페이지 번호를 넘겨받아 해당 페이지에 데이터들만 전달
    public Page<JoinEntity> getUsersByPage(int page, int size){
        return joinRepo.findAll(PageRequest.of(page, size));
    }
}
