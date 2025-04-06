package ru.practicum.myblog.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.myblog.domain.Comment;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.repository.PostRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class PostRepositoryImpl implements PostRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Post save(Post post) {
        return sessionFactory.getCurrentSession().merge(post);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Post where id = :id", Post.class)
                .setParameter("id", id)
                .uniqueResultOptional();
    }

    @Override
    public void editLikeCount(Long id, int likeCountModifier) {
        Session session = sessionFactory.getCurrentSession();
        Post post = session.createQuery("from Post where id = :id", Post.class)
                .setParameter("id", id).uniqueResult();
        post.setLikesCount(post.getLikesCount() + likeCountModifier);
        session.merge(post);
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(
                session.createQuery("from Post where id = :id", Post.class)
                        .setParameter("id", id).uniqueResult()
        );
    }

}
