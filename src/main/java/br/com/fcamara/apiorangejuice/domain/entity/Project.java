package br.com.fcamara.apiorangejuice.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User user;
    private String title;
    private List<Tag> tags;
    private String link;
    private String description;
    private String imagePath;

}
