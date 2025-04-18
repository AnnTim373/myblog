package ru.practicum.myblog.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.page.Page;
import ru.practicum.myblog.repository.PostRepository;

import java.util.List;
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

    @Override
    public Page<Post> findAll(Page<Post> page) {
        Session session = sessionFactory.getCurrentSession();
        Long total = session.createQuery("select count(p.id) from Post p", Long.class).uniqueResult();

        List<Post> posts = session
                .createQuery("from Post p order by p.id desc", Post.class)
                .setFirstResult(page.getOffset())
                .setMaxResults(page.getPageSize())
                .getResultList();

        return new Page<>(posts, total, page.getPageSize(), page.getPageNumber());
    }

    @Override
    public Page<Post> findAllByTag(Page<Post> page, String tag) {
        Session session = sessionFactory.getCurrentSession();
        Long total = session
                .createQuery(
                        "select count(distinct p.id) from Post p join p.tags t where t.value = :tag", Long.class
                )
                .setParameter("tag", tag)
                .uniqueResult();

        List<Post> posts = session
                .createQuery(
                        "select distinct p from Post p join p.tags t where t.value = :tag order by p.id desc",
                        Post.class
                )
                .setParameter("tag", tag)
                .setFirstResult(page.getOffset())
                .setMaxResults(page.getPageSize())
                .getResultList();

        return new Page<>(posts, total, page.getPageSize(), page.getPageNumber());
    }

    public Optional<Post> findByTitle(String title) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().createQuery(
                        "select p from Post p where p.title = :title",
                        Post.class
                )
                .setParameter("title", title).uniqueResult());
    }

}
