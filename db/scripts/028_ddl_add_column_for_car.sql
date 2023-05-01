ALTER TABLE car ADD COLUMN car_brand_id INT REFERENCES car_brand(id);
ALTER TABLE car ADD COLUMN car_body_id INT REFERENCES car_body(id);
ALTER TABLE car ADD COLUMN wheel_drive_id INT REFERENCES wheel_drive(id);
ALTER TABLE car ADD COLUMN car_colour_id INT REFERENCES car_colour(id);
ALTER TABLE car ADD COLUMN transmission_id INT REFERENCES transmission(id);
ALTER TABLE car ADD COLUMN year_id INT REFERENCES year(id);
ALTER TABLE car ADD COLUMN mileage INT;