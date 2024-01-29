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
    private String title;
    private String description;
    private String link;
    private String imageProject;
    private String tags;
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
