create schema if not exists my_blog;

create sequence if not exists my_blog.seq_post;

create table if not exists my_blog.post
(
    id          bigint default next value for my_blog.seq_post primary key,
    title       varchar(256) not null,
    content     varchar      not null,
    likes_count integer      not null
);
