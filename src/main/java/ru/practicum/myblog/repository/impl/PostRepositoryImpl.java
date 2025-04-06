package ru.practicum.myblog.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.repository.PostRepository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final SessionFactory sessionFactory;

    @Override
    @Transactional
    public Post save(Post post) {
        sessionFactory.getCurrentSession().persist(post);
        return post;
    }

}
