CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(255),
    link VARCHAR(255),
    image_project VARCHAR(255),
    tags VARCHAR(80),
    user_id BIGINT,
    deleted BOOLEAN DEFAULT false,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
