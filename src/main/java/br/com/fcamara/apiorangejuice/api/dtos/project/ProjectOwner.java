package br.com.fcamara.apiorangejuice.api.dtos.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectOwner {
    private String firstname;
    private String lastname;
    private String email;
    private String profileImageUser;
}
