package ru.practicum.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

    private Long id;

    private String title;

    private MultipartFile image;

    private String tags;

    private String content;

    private Long likesCount;

    private String oldImage;

}
