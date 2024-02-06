package br.com.fcamara.apiorangejuice.api.utils;

import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static final String API_ISSUER = "orange-api-squad14";

    // User routes mapping
    public static final String USER_CONTROLLER_MAPPING_ROUTE = "/user";
    public static final String POST_USER_REGISTER_ROUTE = "/register";
    public static final String POST_USER_LOGIN_ROUTE = "/login";

    // Projects routes mapping
    public static final String PROJECT_CONTROLLER_MAPPING_ROUTE = "/project";
    public static final String GET_ALL_PROJECTS_ROUTE = "/all";
    public static final String DELETE_PROJECT_ROUTE = "/{projectId}";

    // Exception or validation messages
    public static final String EMAIL_REGISTERED = "Email already registered.";
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String UNAUTHORIZED_PROJECT = "Project resource not allowed.";
    public static final String MIN_SIZE_PASSWORD_MESSAGE = "Password must be {min} characters at least.";
    public static final String REQUIRED_PASSWORD_MESSAGE = "Password is required";
    public static final String REQUIRED_EMAIL_MESSAGE = "Email is required";
    public static final String REQUIRED_FIRSTNAME_MESSAGE = "Firstname is required";
    public static final String REQUIRED_LASTNAME_MESSAGE = "Lastname is required";
    public static final String ERROR_JWT_CREATION_MESSAGE = "Something wrong during token creation.";
}
