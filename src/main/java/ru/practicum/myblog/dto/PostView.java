package ru.practicum.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostView {

    private Long id;

    private String title;

    private String image;

    private List<String> tags;

    private String content;

    private Long likesCount;

    private List<CommentDTO> comments;

    @SuppressWarnings("unused")
    public List<String> getContentParts() {
        return Arrays.stream(this.content.split("\n")).toList();
    }

    @SuppressWarnings("unused")
    public String getTextPreview() {
        if (this.content != null) {
            if (this.content.length() <= 200) return this.content;
            return this.content.substring(0, 200) + "...";
        } else return "";
    }

}
