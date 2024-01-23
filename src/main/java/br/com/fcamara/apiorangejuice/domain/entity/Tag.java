package br.com.fcamara.apiorangejuice.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String tagName;

}
