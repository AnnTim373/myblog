package ru.practicum.myblog.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.myblog.domain.Comment;
import ru.practicum.myblog.repository.CommentRepository;

@Repository
@RequiredArgsConstructor
@Transactional
public class CommentRepositoryImpl implements CommentRepository {

    private final SessionFactory sessionFactory;

    @Override
    public void save(Comment comment) {
        sessionFactory.getCurrentSession().merge(comment);
    }

    @Override
    public void deleteById(Long commentId) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(
                session.createQuery("from Comment where id = :id", Comment.class)
                        .setParameter("id", commentId).uniqueResult()
        );
    }

}
