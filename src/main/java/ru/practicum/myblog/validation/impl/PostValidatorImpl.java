package ru.practicum.myblog.validation.impl;

import org.springframework.stereotype.Component;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.error.PostException;
import ru.practicum.myblog.validation.PostValidator;

import java.util.regex.Pattern;

@Component
public class PostValidatorImpl implements PostValidator {

    private static final String TAG_PATTERN = "^#([A-Za-zА-Яа-я0-9]+)(\\s#([A-Za-zА-Яа-я0-9]+))*$";

    @Override
    public void validate(PostDTO postDTO) {
        validateTags(postDTO.getTags());
    }

    private void validateTags(String tags) {
        if (tags == null || tags.isEmpty()) return;
        if (!Pattern.matches(TAG_PATTERN, tags))
            throw new PostException("Invalid tag format: " + tags + "; should be like '#tag #tag'");
    }

}
