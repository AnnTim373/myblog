package ru.practicum.myblog.repository;

import ru.practicum.myblog.domain.Tag;

import java.util.List;

public interface TagRepository {

    void saveAll(List<Tag> tags);

}
