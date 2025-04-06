package ru.practicum.myblog.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostDTO {

    private Long id;

    private String title;

    private MultipartFile image;

    private String tags;

    private String content;

    private Long likesCount;

}
