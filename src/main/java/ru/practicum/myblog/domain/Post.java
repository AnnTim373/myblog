package ru.practicum.myblog.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(schema = "my_blog", name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_sequence")
    @SequenceGenerator(name = "post_sequence", sequenceName = "my_blog.seq_post", allocationSize = 1)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "likes_count")
    private Long likesCount;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private Image image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "my_blog", name = "post_tag",
            joinColumns = @JoinColumn(
                    name = "post_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "tag_value",
                    referencedColumnName = "tag_value"
            ))
    private List<Tag> tags;

}
