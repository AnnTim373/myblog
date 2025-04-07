package ru.practicum.myblog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(schema = "my_blog", name = "tag")
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @Column(name = "tag_value")
    private String value;

}
