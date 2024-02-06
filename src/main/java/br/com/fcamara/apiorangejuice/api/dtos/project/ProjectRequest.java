package br.com.fcamara.apiorangejuice.api.dtos.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
    private Long Id;
    private String title;
    private String description;
    private String link;
    private String imageProject;
    private List<String> tags;
}
