package ru.practicum.myblog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(schema = "my_blog", name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {

    @Id
    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @Lob
    @Column(name = "data", columnDefinition = "BLOB")
    private byte[] data;

}
