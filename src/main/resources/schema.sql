create schema if not exists my_blog;

create sequence if not exists my_blog.seq_post;

create table if not exists my_blog.post
(
    id          bigint default next value for my_blog.seq_post primary key,
    title       varchar(256) not null,
    content     varchar      not null,
    likes_count integer      not null
);

create table if not exists my_blog.image
(
    post_id bigint references my_blog.post(id) primary key,
    data blob not null
);

create table if not exists my_blog.tag
(
    tag_value varchar primary key
);

create table if not exists my_blog.post_tag
(
    post_id bigint not null references my_blog.post(id),
    tag_value varchar not null references my_blog.tag(tag_value),
    primary key (post_id, tag_value)
);

create sequence if not exists my_blog.seq_comment;

create table if not exists my_blog.comment
(
    id bigint default next value for my_blog.seq_comment primary key,
    content varchar,
    post_id bigint references my_blog.post(id)
);

create index if not exists idx_comment_post_id on my_blog.comment(post_id);



