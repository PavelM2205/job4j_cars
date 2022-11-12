CREATE TABLE post_participants (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES auto_user(id),
    post_id INT REFERENCES auto_post(id)
);