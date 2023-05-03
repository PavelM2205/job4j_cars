CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    engine_id INT REFERENCES engine(id),
    driver_id INT REFERENCES driver(id)
);