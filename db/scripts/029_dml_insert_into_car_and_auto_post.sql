-- INSERT INTO auto_user VALUES (1, 'login_1', 'password_1'), (2, 'login_2', 'password_2');

-- INSERT INTO driver VALUES (1, 'driver_1', 1), (2, 'driver_2', 2);

INSERT INTO car (car_brand_id, engine_id, car_body_id, wheel_drive_id, car_colour_id,
                 transmission_id, year_id, mileage)
    VALUES ((SELECT id FROM car_brand WHERE name = 'BMW'),
           (SELECT id FROM engine WHERE name = '1.8 л/Бензин'),
           (SELECT id FROM car_body WHERE name = 'Хэтчбэк'),
           (SELECT id FROM wheel_drive WHERE name = 'Передний'),
           (SELECT id FROM car_colour WHERE name = 'Белый'),
           (SELECT id FROM transmission WHERE name = 'Механика'),
           (SELECT id FROM year WHERE year = 2007), 150000),
           ((SELECT id FROM car_brand WHERE name = 'Toyota'),
           (SELECT id FROM engine WHERE name = '3.0 л/Дизель'),
           (SELECT id FROM car_body WHERE name = 'Седан'),
           (SELECT id FROM wheel_drive WHERE name = 'Задний'),
           (SELECT id FROM car_colour WHERE name = 'Черный'),
           (SELECT id FROM transmission WHERE name = 'Автомат'),
           (SELECT id FROM year WHERE year = 2015), 75000),
           ((SELECT id FROM car_brand WHERE name = 'KIA'),
           (SELECT id FROM engine WHERE name = '3.0 л/Дизель'),
           (SELECT id FROM car_body WHERE name = 'Седан'),
           (SELECT id FROM wheel_drive WHERE name = 'Задний'),
           (SELECT id FROM car_colour WHERE name = 'Черный'),
           (SELECT id FROM transmission WHERE name = 'Автомат'),
           (SELECT id FROM year WHERE year = 2015), 75000);

INSERT INTO auto_post (text, auto_user_id, car_id, city_id, photo, price, is_sold)
    VALUES ('text_1', (SELECT id FROM auto_user WHERE login = 'Ivanov'),
           (SELECT id FROM car WHERE car_brand_id =
                                     (SELECT id FROM car_brand WHERE name = 'BMW')),
           (SELECT id FROM city WHERE name = 'Москва'), '{1, 2}', 1200000, FALSE),
           ('text_2', (SELECT id FROM auto_user WHERE login = 'Petrov'),
           (SELECT id FROM car WHERE car_brand_id =
                                      (SELECT id FROM car_brand WHERE name = 'Toyota')),
           (SELECT id FROM city WHERE name = 'Красноярск'), '{1, 2}', 1500000, TRUE),
           ('text_3', (SELECT id FROM auto_user WHERE login = 'Sidorov'),
           (SELECT id FROM car WHERE car_brand_id =
                                      (SELECT id FROM car_brand WHERE name = 'KIA')),
           (SELECT id FROM city WHERE name = 'Новосибирск'), '{1, 2}', 850000, FALSE);