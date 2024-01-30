package br.com.fcamara.apiorangejuice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private String link;
    private String imageProject;
    private String tags;
    private UserResponse user;
    private LocalDate createdAt;
}
