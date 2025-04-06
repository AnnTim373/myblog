package ru.practicum.myblog.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
        sessionFactory.getCurrentSession().merge(post);
        return post;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Post where id = :id", Post.class)
                .setParameter("id", id)
                .uniqueResultOptional();
    }

    @Override
    public void editLikeCount(Long postId, int likeCountModifier) {
        Session session = sessionFactory.getCurrentSession();
        Post post = session.createQuery("from Post where id = :postId", Post.class)
                .setParameter("postId", postId).uniqueResult();
        post.setLikesCount(post.getLikesCount() + likeCountModifier);
        session.merge(post);
    }

}
