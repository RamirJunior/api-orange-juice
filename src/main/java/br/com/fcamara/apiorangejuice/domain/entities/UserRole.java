package br.com.fcamara.apiorangejuice.domain.entities;

import lombok.Getter;

@Getter
public enum UserRole {

    USER("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }
}
