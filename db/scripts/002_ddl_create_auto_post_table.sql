CREATE TABLE auto_post (
    id SERIAL PRIMARY KEY,
    text TEXT,
    created TIMESTAMP DEFAULT now(),
    auto_user_id INT REFERENCES auto_user(id)
);