package ru.practicum.myblog.validation;

import ru.practicum.myblog.dto.PostDTO;

public interface PostValidator {

    void validate(PostDTO postDTO);

}
