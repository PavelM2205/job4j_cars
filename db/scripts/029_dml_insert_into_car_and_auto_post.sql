INSERT INTO car (car_brand_id, engine_id, car_body_id, wheel_drive_id, car_colour_id,
                 transmission_id, year_id, mileage)
    VALUES ((SELECT id FROM car_brand WHERE name = 'BMW'),
           (SELECT id FROM engine WHERE name = '1.8 л/Бензин'),
           (SELECT id FROM car_body WHERE name = 'Седан'),
           (SELECT id FROM wheel_drive WHERE name = 'Передний'),
           (SELECT id FROM car_colour WHERE name = 'Черный'),
           (SELECT id FROM transmission WHERE name = 'Механика'),
           (SELECT id FROM auto_year WHERE year_value = 2007), 150000),
           ((SELECT id FROM car_brand WHERE name = 'Toyota'),
           (SELECT id FROM engine WHERE name = '3.0 л/Дизель'),
           (SELECT id FROM car_body WHERE name = 'Седан'),
           (SELECT id FROM wheel_drive WHERE name = 'Задний'),
           (SELECT id FROM car_colour WHERE name = 'Белый'),
           (SELECT id FROM transmission WHERE name = 'Автомат'),
           (SELECT id FROM auto_year WHERE year_value = 2015), 75000),
           ((SELECT id FROM car_brand WHERE name = 'KIA'),
           (SELECT id FROM engine WHERE name = '3.0 л/Дизель'),
           (SELECT id FROM car_body WHERE name = 'Внедорожник'),
           (SELECT id FROM wheel_drive WHERE name = 'Полный'),
           (SELECT id FROM car_colour WHERE name = 'Синий'),
           (SELECT id FROM transmission WHERE name = 'Автомат'),
           (SELECT id FROM auto_year WHERE year_value = 2015), 75000);

INSERT INTO auto_post (text, auto_user_id, car_id, city_id, photo, price, is_sold)
    VALUES ('Не бита, не крашена', (SELECT id FROM auto_user WHERE login = 'Ivanov'),
           (SELECT id FROM car WHERE car_brand_id =
                                     (SELECT id FROM car_brand WHERE name = 'BMW')),
           (SELECT id FROM city WHERE name = 'Москва'), '{1,2}', 1200000, FALSE),
           ('В отличном состоянии', (SELECT id FROM auto_user WHERE login = 'Petrov'),
           (SELECT id FROM car WHERE car_brand_id =
                                      (SELECT id FROM car_brand WHERE name = 'Toyota')),
           (SELECT id FROM city WHERE name = 'Красноярск'), '{1, 2}', 1500000, TRUE),
           ('Состояние хорошее, на ходу', (SELECT id FROM auto_user WHERE login = 'Sidorov'),
           (SELECT id FROM car WHERE car_brand_id =
                                      (SELECT id FROM car_brand WHERE name = 'KIA')),
           (SELECT id FROM city WHERE name = 'Новосибирск'), '{1, 2}', 850000, FALSE);