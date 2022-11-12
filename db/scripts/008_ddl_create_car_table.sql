CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    engine_id INT REFERENCES engine(id),
    driver_id INT REFERENCES driver(id)
);