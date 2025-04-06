package ru.practicum.myblog.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.myblog.domain.Tag;
import ru.practicum.myblog.repository.TagRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    private final SessionFactory sessionFactory;

    @Override
    @Transactional
    public void saveAll(List<Tag> tags) {
        tags.forEach(tag -> sessionFactory.getCurrentSession().merge(tag));
    }

}
