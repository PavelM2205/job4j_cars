CREATE TABLE owner_history (
    id SERIAL PRIMARY KEY,
    car_id INT REFERENCES car(id),
    driver_id INT REFERENCES driver(id),
    startAt DATE,
    endAt DATE
);