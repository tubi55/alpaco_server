package com.example.basic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private String writerName;

    //외래키를 통해 활용할 사용자 이름이 포함된 새로운 DTO내부 생성자로 다시 생성
    public BoardDTO(Long id, String title, String content, String writerName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writerName = writerName;
    }
}