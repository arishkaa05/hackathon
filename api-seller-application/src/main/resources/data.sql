-- Пример 1 password->password1
INSERT INTO users (city, contact_number, image_url, username, lastname, email, password, create_date, update_date)
VALUES ('Moscow', '+7 999 123 8888', 'orca', 'Толик', 'Генов', 'tolik@example.com',
        '$2a$12$kowz4HP.lkTFGs.0uKoDHOs.9u9dVYmydX2iRSIFCu/TXSpSP8C7i', '2023-01-01 12:00:00', '2023-01-01 12:00:00');

-- Пример admin password->password1
INSERT INTO users (city, contact_number, image_url, username, lastname, email, password, create_date, update_date)
VALUES ('Moscow', '+7 999 123 4500', 'orca', 'admin', 'admin', 'admin@example.com',
        '$2a$12$ZzjQlNYnKsy/epnVyUPUveueouvI.T/UQfCvCJB3e5K8Vssq3Sacy', '2023-01-01 12:00:00', '2023-01-01 12:00:00');
  
-- Пример 2 password->password2
INSERT INTO users (city, contact_number, image_url, username, lastname, email, password, create_date, update_date)
VALUES ('Saint Petersburg', '+7 999 234 5678', 'elephant', 'Миша', 'Потапов',
        'misha2@example.com', '$2a$12$Lu3r.RqH9kkoOlBeLPv.9e7pHbTJSlAvTw7UM6qLISfbDUz4wv4fy', '2023-01-02 12:00:00',
        '2023-01-02 12:00:00');

-- Пример 3
INSERT INTO users (city, contact_number, image_url, username, lastname, email, password, create_date, update_date)
VALUES ('Novosibirsk', '+7 999 345 6789', 'beaver', 'Максим', 'Плаксов', 'maks@example.com',
        '$2a$12$0jai4vCadbfLu75pxnRq9O/xbPxAizpSXF.LNJx12AkuqZbR60ifG', '2023-01-03 12:00:00', '2023-01-03 12:00:00');

-- Пример 4 password->password4
INSERT INTO users (city, contact_number, image_url, username, lastname, email, password, create_date, update_date)
VALUES ('Yekaterinburg', '+7 999 456 7890', 'duck', 'Виталик', 'Трапезов',
        'trapezov@example.com', '$2a$12$vqLnu7fNE1qwekAP2qWxGORSPHBpMAEq9mG8zEbje/GmVe9bUXnvK', '2023-01-04 12:00:00',
        '2023-01-04 12:00:00');

-- Пример 5
INSERT INTO users (city, contact_number, image_url, username, lastname, email, password, create_date, update_date)
VALUES ('Kazan', '+7 999 567 8901', 'duck', 'Дмитрий', 'Семененков', 'dmitry@example.com',
        '$2a$12$Zk/6WPgTcQ.TUFKtM9tOSubXa7mD24CSd3agceuiDL5xvgImk0r9e', '2023-01-05 12:00:00', '2023-01-05 12:00:00');

-- Пример 6
INSERT INTO users (city, contact_number, image_url, username, lastname, email, password, create_date, update_date)
VALUES ('Chelyabinsk', '+7 999 678 9012', 'beaver', 'Михаил', 'Петров', 'misha1@example.com',
        '$2a$12$S9lUGHw5bfPM75p.3qXdF.tZOQqh0khLMsH5xy0ulj8NXv9eW7Nwe', '2023-01-06 12:00:00', '2023-01-06 12:00:00');

-- Пример 7
INSERT INTO users (city, contact_number, image_url, username, lastname, email, password, create_date, update_date)
VALUES ('Omsk', '+7 999 789 0123', 'turtle', 'Кирилл', 'Соколов', 'arina@example.com',
        '$2a$12$V0y0x2GVKuqfSs5sLUdsa.ZMLkUeOqCOU/p4Ev/OYgbZnmiFQ7TNm', '2023-01-07 12:00:00', '2023-01-07 12:00:00');

-- Пример 8
INSERT INTO users (city, contact_number, image_url, username, lastname, email, password, create_date, update_date)
VALUES ('Rostov-on-Don', '+7 999 890 1234', 'bee', 'Геннадий', 'Букин', 'gena@example.com',
        '$2a$12$WigK.ibOzqlX9CWM4L9F6emOJVYyoB.Ecwa3QfAvk0gPXlKo5UEbm', '2023-01-08 12:00:00', '2023-01-08 12:00:00');

-- Пример 9
INSERT INTO users (city, contact_number, image_url, username, lastname, email, password, create_date, update_date)
VALUES ('Ufa', '+7 999 901 2345', 'elephant', 'Ирина', 'Чикипики', 'irina@example.com',
        '$2a$12$.g.lnIjiIG8zTABySiDjQ.ThzHA3ii8IeC8pwndftPWpYVIafw6WO', '2023-01-09 12:00:00', '2023-01-09 12:00:00');

-- Пример 10
INSERT INTO users (city, contact_number, image_url, username, lastname, email, password, create_date, update_date)
VALUES ('Samara', '+7 999 012 3456', 'bee', 'Поль', 'Ринс', 'pole@example.com',
        '$2a$12$387OHZEG3SUyFGGawhxKo.b31Yn9yjXZBLvAB.O3h.vH2nmByY7bi', '2023-01-10 12:00:00', '2023-01-10 12:00:00');

-- Example 1: User with the "ROLE_USER" role
INSERT INTO users_roles (user_id, role)
VALUES (1, 'ROLE_USER');

-- Example 2: User with the "ROLE_USER" role
INSERT INTO users_roles (user_id, role)
VALUES (2, 'ROLE_ADMIN');

-- Example 3: User with the "ROLE_USER" role
INSERT INTO users_roles (user_id, role)
VALUES (3, 'ROLE_USER');

-- Example 4: User with the "ROLE_ADMIN" role
INSERT INTO users_roles (user_id, role)
VALUES (4, 'ROLE_ADMIN');

-- Example 5: User with the "ROLE_ADMIN" role
INSERT INTO users_roles (user_id, role)
VALUES (5, 'ROLE_ADMIN');

-- Example 6: User with both "ROLE_USER" and "ROLE_ADMIN" roles
INSERT INTO users_roles (user_id, role)
VALUES (6, 'ROLE_USER');
INSERT INTO users_roles (user_id, role)
VALUES (7, 'ROLE_ADMIN');

-- Example 7: User with the "ROLE_USER" role
INSERT INTO users_roles (user_id, role)
VALUES (8, 'ROLE_USER');

-- Example 8: User with the "ROLE_ADMIN" role
INSERT INTO users_roles (user_id, role)
VALUES (9, 'ROLE_ADMIN');

-- Example 9: User with the "ROLE_USER" role
INSERT INTO users_roles (user_id, role)
VALUES (10, 'ROLE_USER');

-- Пример 1
INSERT INTO subcategories (title, title_url, quantity, description, image_url)
VALUES ('Футблоки', 't-shirts', 199, 'Футболки из хлопка с икслюзивным логотипом LETI', 'TShirt.jpg');

-- Пример 2
INSERT INTO subcategories (title, title_url, quantity, description, image_url)
VALUES ('Свитшоты', 'sweatshirts', 299, 'Свитшоты с вашим любимым вузом', 'Sweatshirts.jpg');

-- Пример 3
INSERT INTO subcategories (title, title_url, quantity, description, image_url)
VALUES ('Кепки', 'cap', 199, 'Кепки с логотипом ЛЭТИ', 'cap.jpg');

-- Пример 4
INSERT INTO subcategories (title, title_url, quantity, description, image_url)
VALUES ('Рюкзаки', 'backpacks', 299, 'Непромокаемые сундучки для сохранения ваших лабораторных работ', 'backpacks.jpg');

-- Пример 5
INSERT INTO subcategories (title, title_url, quantity, description, image_url)
VALUES ('Носки', 'socks', 499, 'Носочки с котиками', 'socks.jpg');

-- Пример 6
INSERT INTO subcategories (title, title_url, quantity, description, image_url)
VALUES ('Обложки', 'covers', 499, 'Обложки для ваших классных тетрадок', 'covers.jpg');

-- Пример 7
INSERT INTO subcategories (title, title_url, quantity, description, image_url)
VALUES ('Шопперы', 'shopper', 599, 'Хлопковые шопперы для ваших вещичек', 'shopper.jpg');

-- Пример 8
INSERT INTO subcategories (title, title_url, quantity, description, image_url)
VALUES ('Браслеты', 'bracelets', 199, 'Резиновые браслеты с логотипом вуза', 'bracelets.jpg');

-- Пример 9
INSERT INTO subcategories (title, title_url, quantity, description, image_url)
VALUES ('Зонтики', 'umbrellas', 199,
        'Автоматический классический зонт с логотипом LETI, оснащён надёжным механизмом.Складной механизм автомат удобен при раскрытии простым нажатием на кнопку.Складной зонт имеет прочный каркас с системой антиветер.',
        'umbrellas.webp');

-- Пример 9
INSERT INTO subcategories (title, title_url, quantity, description, image_url)
VALUES ('Кружки', 'mugs', 199,
        'Красивая большая кружка с принтом LETI',
        'mugs.jpg');

-- Пример 10
INSERT INTO subcategories (title, title_url, quantity, description, image_url)
VALUES ('Постеры', 'posters', 299,
        'Вернитесь в золотую эпоху, когда у каждого человека была коллекция постеров. Пополни свою коллекцию с постерами LETI',
        'posters.jpg');

-- Пример 11
INSERT INTO subcategories (title, title_url, quantity, description, image_url)
VALUES ('Коврики', 'rugs', 299,
        'Каждому необходимо приобрести данную прелесть для душа в общежитии',
        'rugs.jpg');

-- Пример 12
INSERT INTO subcategories (title, title_url, quantity, description, image_url)
VALUES ('Наборы и сертификаты', 'kits-and-certificates', 299,
        'Каждому необходимо приобрести подарочные набор для людей не учащихся в данному вузе, чтобы они увидели истинную красоту',
        'kits.jpg');


-- Пример 1
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (100, 'Футболка с синим логотипом LETI белая', 't-shirt-white-with-leti-logo-blue', 1200, true, true, 1100,
        'Официальная футболка с логотипом LETI для всех фанатов.', 100, 0.3, 'tshirt-white-logo.png',
        '2023-01-01 10:00:00', '2023-01-01 10:00:00');
-- Пример 2
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (100, 'Футболка с золотым логотипом LETI  белая', 't-shirt-white-with-leti-logo-gold', 1200, true, true, 1100,
        'Официальная футболка с логотипом LETI для всех фанатов золотого цвета.', 100, 0.3, 'tshirt-white-logo-gold.png',
        '2023-01-01 10:00:00', '2023-01-01 10:00:00');

-- Пример 3
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (100, 'Футболка с логотипом LETI синего цвета', 't-shirt-with-blue-leti-logo', 1200, true, true, 1100,
        'Официальная футболка с логотипом LETI для всех фанатов синего цвета.', 100, 0.3, 'tshirt-blue-logo.png',
        '2023-01-01 10:00:00', '2023-01-01 10:00:00');

-- Пример 3
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (100, 'Футболка с логотипом LETI белого цвета c котиком', 't-shirt-with-white-leti-logo-cat', 1200, true, true, 1100,
        'Официальная футболка с логотипом LETI для всех фанатов синего цвета.', 100, 0.3, 'tshirt-white-cat.png',
        '2023-01-01 10:00:00', '2023-01-01 10:00:00');

-- Пример 3
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (100, 'Футболка с логотипом LETI белого цвета c котиками', 't-shirt-with-white-leti-logo-catы', 1200, true, true, 1100,
        'Официальная футболка с логотипом LETI для всех фанатов синего цвета.', 100, 0.3, 'tshirt-white-cats.png',
        '2023-01-01 10:00:00', '2023-01-01 10:00:00');


-- Пример 4
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (100, 'Свитшот с котиками LETI белый', 'sweatshirt-with-leti-cats-white', 1999, true, true, 1899,
        'Официальный свитшот с логотипом LETI для всех фанатов.', 100, 0.3, 'sweatshirt-with-cats-white.png',
        '2023-01-01 10:00:00', '2023-01-01 10:00:00');

-- Пример 5
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (100, 'Свитшот с логотипом LETI синий', 'sweatshirt-with-leti-logo-blue', 1999, true, true, 1899,
        'Официальный свитшот с логотипом LETI для всех фанатов.', 100, 0.3, 'sweatshirt-with-logo-leti-blue.png',
        '2023-01-01 10:00:00', '2023-01-01 10:00:00');

-- Пример 6
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (100, 'Свитшот с логотипом LETI белый', 'sweatshirt-with-leti-logo-white', 1999, true, true, 1899,
        'Официальный свитшот с логотипом LETI для всех фанатов.', 100, 0.3, 'sweatshirt-with-logo-leti-white.png',
        '2023-01-01 10:00:00', '2023-01-01 10:00:00');

-- Пример 6
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (100, 'Свитшот с логотипом LETI белый c котиком', 'sweatshirt-with-leti-logo-white-cat', 1999, true, true, 1899,
        'Официальный свитшот с логотипом LETI для всех фанатов.', 100, 0.3, 'sweatshirt-with-cat-white.png',
        '2023-01-01 10:00:00', '2023-01-01 10:00:00');

-- Пример 6
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (100, 'Свитшот с круглым логотипом LETI ', 'sweatshirt-with-circle-logo-leti-white', 1999, true, true, 1899,
        'Официальный свитшот с логотипом LETI для всех фанатов.', 100, 0.3, 'sweatshirt-with-circle-logo-leti-white.png',
        '2023-01-01 10:00:00', '2023-01-01 10:00:00');

-- Пример 7
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (75, 'Кофейная кружка LETI белая', 'coffee-mug-leti-white', 1200, false, false, 1200, 'Начните день с чашки для кофе с логотипом LETI.',
        200,
        0.4, 'coffee-mug-leti-white.png', '2023-01-03 12:45:00', '2023-01-03 12:45:00');

-- Пример 7
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (75, 'Кофейная кружка LETI синяя', 'coffee-mug-leti-blue', 1200, false, false, 1200, 'Начните день с чашки для кофе с логотипом LETI.',
        200,
        0.4, 'coffee-mug-leti-blue.jpeg', '2023-01-03 12:45:00', '2023-01-03 12:45:00');

-- Пример 7
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (75, 'Термокружка LETI с котиком', 'thermal-mug-cat', 1200, false, false, 1100, 'Начните день с чашки для кофе с логотипом LETI.',
        200,
        0.4, 'thermal-mug-cat.png', '2023-01-03 12:45:00', '2023-01-03 12:45:00');

-- Пример 8
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Кепка LETI белая', 'cap-leti-white-logo', 1000, true, false, 900,
        'Покажите свою привязанность к LETI с этой стильной бейсбольной кепкой.', 150, 0.2, 'cap-leti-white-logo.png',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

-- Пример 9
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Кепка LETI синия', 'cap-leti-blue', 1000, true, false, 900,
        'Покажите свою привязанность к LETI с этой стильной бейсбольной кепкой.', 150, 0.2, 'cap-leti-blue.png',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

-- Пример 10
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Кепка LETI белая с котиком', 'cap-leti-white-cat', 1000, true, false, 900,
        'Покажите свою привязанность к LETI с этой стильной бейсбольной кепкой.', 150, 0.2, 'cap-leti-white.png',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

-- Пример 11
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Рюкзак LETI белый', 'backpack-leti-white', 1999, true, false, 1299,
        'Минималистичный рюкзак женский и мужской из водостойкого материала и с прочными молниями. Вместительный портфель подойдет девушкам и парням для работы, а также будет полезен как рюкзак школьный.', 150, 0.7, 'backpack-leti-white.png',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

-- Пример 12
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Рюкзак LETI синий', 'backpack-leti-blue', 1999, true, false, 1299,
        'Минималистичный рюкзак женский и мужской из водостойкого материала и с прочными молниями. Вместительный портфель подойдет девушкам и парням для работы, а также будет полезен как рюкзак школьный.', 150, 0.7, 'backpack-leti-blue.png',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

-- Пример 13
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Рюкзак LETI черный', 'backpack-leti-black', 1999, true, false, 1299,
        'Минималистичный рюкзак женский и мужской из водостойкого материала и с прочными молниями. Вместительный портфель подойдет девушкам и парням для работы, а также будет полезен как рюкзак школьный.', 150, 0.7, 'backpack-leti-black.png',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Носки LETI белые', 'leti-socks-white', 999, true, false, 699,
        'Набор базовых носков хб с комфортной резинкой. Страна производства Россия. Классическая всесезонная модель высокая - благодаря правильной длине ноги не будут оголяться в положении сидя. Удлиненные однотонные носочки', 150, 0.7, 'leti-socks-white.jpeg',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');


INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Носки LETI синие', 'leti-socks-blue', 999, true, false, 699,
        'Набор базовых носков хб с комфортной резинкой. Страна производства Россия. Классическая всесезонная модель высокая - благодаря правильной длине ноги не будут оголяться в положении сидя. Удлиненные однотонные носочки', 150, 0.7, 'leti-socks-blue.jpeg',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Носки LETI черные', 'leti-socks-black', 999, true, false, 699,
        'Набор базовых носков хб с комфортной резинкой. Страна производства Россия. Классическая всесезонная модель высокая - благодаря правильной длине ноги не будут оголяться в положении сидя. Удлиненные однотонные носочки', 150, 0.7, 'leti-socks-black.jpeg',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Обложка LETI белая', 'leti-cover-white', 299, true, false, 199,
        'Обложки защитят тетради от механических повреждений, грязи и пыли и надолго сохранят внешний вид. Обложки для тетрадей изготовлены из полипропилена (ПП) толщиной 60 мкм, что обеспечит надежную защиту. ', 150, 0.2, 'leti-cover-white.jpeg',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Чехол с лого LETI белый', 'leti-case-white', 299, true, false, 199,
        'Обложки защитят тетради от механических повреждений, грязи и пыли и надолго сохранят внешний вид. Обложки для тетрадей изготовлены из полипропилена (ПП) толщиной 60 мкм, что обеспечит надежную защиту. ', 150, 0.2, 'leti-case-white.png',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');


INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Обложка LETI черная', 'leti-cover-black', 299, true, false, 199,
        'Обложки защитят тетради от механических повреждений, грязи и пыли и надолго сохранят внешний вид. Обложки для тетрадей изготовлены из полипропилена (ПП) толщиной 60 мкм, что обеспечит надежную защиту. ', 150, 0.2, 'leti-cover-black.jpeg',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Шоппер LETI черный', 'shopper-leti-black', 1599, true, false, 1299,
        'Сумка шоппер повседневная на плечо - отличный аксессуар на каждый день для дополнения Вашего современного делового или спортивного образа. Хлопковый новогодний шопер женский дает усадку при стирке, а наш, в составе с синтетикой держит форму годами.', 150, 0.2, 'shopper-leti-black.jpeg',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Шоппер LETI ,белый', 'shopper-leti-white', 1599, true, false, 1299,
        'Сумка шоппер повседневная на плечо - отличный аксессуар на каждый день для дополнения Вашего современного делового или спортивного образа. Хлопковый новогодний шопер женский дает усадку при стирке, а наш, в составе с синтетикой держит форму годами.', 150, 0.2, 'shopper-leti-white.jpeg',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Шоппер LETI синий', 'shopper-leti-blue', 1599, true, false, 1299,
        'Сумка шоппер повседневная на плечо - отличный аксессуар на каждый день для дополнения Вашего современного делового или спортивного образа. Хлопковый новогодний шопер женский дает усадку при стирке, а наш, в составе с синтетикой держит форму годами.', 150, 0.2, 'shopper-leti-blue.jpeg',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (120, 'Браслеты LETI белые', 'leti-bracelets-white', 299, true, false, 199,
        'Оригинальные браслеты с символикой LETI.', 150, 0.2, 'leti-bracelets-white.jpeg',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (10, 'Браслеты LETI синие', 'leti-bracelets-blue', 299, true, false, 199,
        'Оригинальные браслеты с символикой LETI.', 150, 0.2, 'leti-bracelets-blue.jpeg',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (10, 'Браслеты LETI черные', 'leti-bracelets-black', 299, true, false, 199,
        'Оригинальные браслеты с символикой LETI.', 150, 0.2, 'leti-bracelets-black.jpeg',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (10, 'Зонтик LETI черный', 'umbrellas-leti-black', 699, true, false, 599,
        'Складной зонт женский полный автомат прекрасно подойдет для защиты от дождя и идеально дополнит повседневный образ женщины или мужчины.', 150, 0.2, 'umbrellas-leti-black.jpeg',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (10, 'Зонтик LETI белый', 'umbrellas-leti-white', 699, true, false, 599,
        'Складной зонт женский полный автомат прекрасно подойдет для защиты от дождя и идеально дополнит повседневный образ женщины или мужчины.', 150, 0.2, 'umbrellas-leti-white.jpeg',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (10, 'Зонтик LETI синий', 'umbrellas-leti-blue', 699, true, false, 599,
        'Складной зонт женский полный автомат прекрасно подойдет для защиты от дождя и идеально дополнит повседневный образ женщины или мужчины.', 150, 0.2, 'umbrellas-leti-blue.jpeg',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');

INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (10, 'Постер LETI', 'poster-leti', 699, true, false, 599,
        'Постер с изображением лэти', 150, 0.2, 'poster-leti.png',
        '2023-01-04 14:20:00', '2023-01-04 14:20:00');


-- Пример 5
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (90, 'Наклейка для ноутбука LETI', 'Sticker for laptop LETI', 300, true, true, 199,
        'Украсьте свой ноутбук наклейкой с мотивами LETI.', 300,
        0.01, 'leti_laptop_sticker.jpg', '2023-01-05 15:10:00', '2023-01-05 15:10:00');

-- Пример 5
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (90, 'Коврик для ванной LETI белый', 'bath-mat-leti-white', 599, true, true, 499,
        'Коврики для ванной - идеальное решение для создания комфорта и безопасности в твоем доме! Наш набор поможет сделать интерьер ванных и туалетных комнат стильным и уютным, а также обеспечит надежную защиту пола от скользких поверхностей', 300,
        0.01, 'bath-mat-leti-white.jpeg', '2023-01-05 15:10:00', '2023-01-05 15:10:00');

-- Пример 5
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (90, 'Коврик для ванной LETI синий', 'bath-mat-leti-blue', 599, true, true, 499,
        'Коврики для ванной - идеальное решение для создания комфорта и безопасности в твоем доме! Наш набор поможет сделать интерьер ванных и туалетных комнат стильным и уютным, а также обеспечит надежную защиту пола от скользких поверхностей', 300,
        0.01, 'bath-mat-leti-blue.jpeg', '2023-01-05 15:10:00', '2023-01-05 15:10:00');

-- Пример 5
INSERT INTO products (likes, title, title_for_url, old_price, new_product, hot_product, price, description, quantity,
                      weight,
                      image_url, create_date, update_date)
VALUES (90, 'Коврик для ванной LETI черный', 'bath-mat-leti-black', 599, true, true, 499,
        'Коврики для ванной - идеальное решение для создания комфорта и безопасности в твоем доме! Наш набор поможет сделать интерьер ванных и туалетных комнат стильным и уютным, а также обеспечит надежную защиту пола от скользких поверхностей', 300,
        0.01, 'bath-mat-leti-black.jpeg', '2023-01-05 15:10:00', '2023-01-05 15:10:00');

INSERT INTO product_category (subcategories_id, products_id)
VALUES (1, 1);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (1, 2);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (1, 3);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (1, 4);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (1, 5);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (2, 6);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (2, 7);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (2, 8);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (2, 9);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (2, 10);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (3, 14);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (3, 15);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (3, 16);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (4, 17);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (4, 18);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (4, 19);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (5, 20);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (5, 21);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (5, 22);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (6, 23);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (6, 24);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (6, 25);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (7, 26);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (7, 27);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (7, 28);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (8, 29);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (8, 30);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (8, 31);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (9, 32);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (9, 33);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (9, 34);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (10, 11);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (10, 12);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (10, 13);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (11, 35);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (12, 37);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (12, 38);
INSERT INTO product_category (subcategories_id, products_id)
VALUES (12, 39);

-- Пример 1
INSERT INTO orders (create_date, update_date, user_id, address, status, comment, delivery_price)
VALUES ('2023-01-01 10:00:00', '2023-01-01 10:00:00', 1, 'ул. Ломоносова, д.1', 'PROCESSING', 'Быстрая доставка', 500);

-- Пример 2
INSERT INTO orders (create_date, update_date, user_id, address, status, comment, delivery_price)
VALUES ('2023-01-02 11:30:00', '2023-01-02 11:30:00', 2, 'ул. Пушкина, д.5', 'COMPLETED', 'Доставить после 18:00', 700);

-- Пример 3
INSERT INTO orders (create_date, update_date, user_id, address, status, comment, delivery_price)
VALUES ('2023-01-03 12:45:00', '2023-01-03 12:45:00', 3, 'ул. Гагарина, д.10', 'PROCESSING', 'Оставить у соседа', 800);

-- Пример 4
INSERT INTO orders (create_date, update_date, user_id, address, status, comment, delivery_price)
VALUES ('2023-01-04 14:20:00', '2023-01-04 14:20:00', 4, 'ул. Толстого, д.3', 'COMPLETED', 'Не доставлять в выходные',
        900);

-- Пример 5
INSERT INTO orders (create_date, update_date, user_id, address, status, comment, delivery_price)
VALUES ('2023-01-05 15:10:00', '2023-01-05 15:10:00', 5, 'ул. Достоевского, д.7', 'PROCESSING', 'Оплатить картой', 400);

-- Пример 6
INSERT INTO orders (create_date, update_date, user_id, address, status, comment, delivery_price)
VALUES ('2023-01-06 16:30:00', '2023-01-06 16:30:00', 6, 'ул. Чехова, д.2', 'COMPLETED', 'Оставить у портье', 300);

-- Пример 7
INSERT INTO orders (create_date, update_date, user_id, address, status, comment, delivery_price)
VALUES ('2023-01-07 17:45:00', '2023-01-07 17:45:00', 7, 'ул. Карамзина, д.9', 'PROCESSING', 'Доставить до подъезда',
        500);

-- Пример 8
INSERT INTO orders (create_date, update_date, user_id, address, status, comment, delivery_price)
VALUES ('2023-01-08 18:50:00', '2023-01-08 18:50:00', 8, 'ул. Фонвизина, д.4', 'CANCELED', 'Заказ отменен клиентом',
        300);

-- Пример 9
INSERT INTO orders (create_date, update_date, user_id, address, status, comment, delivery_price)
VALUES ('2023-01-09 20:15:00', '2023-01-09 20:15:00', 9, 'ул. Булгакова, д.6', 'PROCESSING', 'Оплата наличными', 400);

-- Пример 10
INSERT INTO orders (create_date, update_date, user_id, address, status, comment, delivery_price)
VALUES ('2023-01-10 21:25:00', '2023-01-10 21:25:00', 10, 'ул. Шолохова, д.8', 'CANCELED', 'Оставить у соседей', 300);


INSERT INTO comments (product_id, user_id, message, create_date, update_date,rating)
VALUES (1, 1, 'Отличная футболка!', NOW(), NOW(),5);
INSERT INTO comments (product_id, user_id, message, create_date, update_date,rating)
VALUES (1, 2, 'Качество на высоте!', NOW(), NOW(),4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (1, 3, 'Положительные впечатления от продукта.', NOW(), NOW(), 4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (1, 4, 'Хорошая футболка, отлично подходит для стройной, довольно длинная. За свои деньги - великолепна!', NOW(), NOW(), 4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (1, 5, 'Плотные футболки, хлопок, на поддевку в холодный период отлично. Придерживайтесь размерной сетки, при стирке дают немного усадку. Рекомендую!', NOW(), NOW(), 4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (1, 6,'Отличные футболки, мягкие и приятные к телу, заказывала в другом цвете их, муж носит уже год и как новые. Цена просто смешная для такого качества и удачного минималистичного дизайна.', NOW(), NOW(), 4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (1, 7,'Ткань очень хорошая, плотная. Размер соответствует, заказывала XL, идеально смотрится, не обтягивает', NOW(), NOW(), 4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (1, 8,'Размер соответствует, удобная базовая футболка', NOW(), NOW(), 4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (1, 9,'Футболка 🔥🔥🔥. Сшита качественно, одел, прям как по мне шили. Материал приятный. Узбекистан молодцы! Закажу ещё! 5 звёзд по-любому.', NOW(), NOW(), 4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (1, 10,'Футболка ашаленная, покупала на физру по понятным причинам: пот на черном видно хуже чем на любом другом цвете. Много надежд за 350 рублей не возлагала, но пришло нечто крутое! ', NOW(), NOW(), 4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (2, 1, 'Продукт нейтральный.', NOW(), NOW(), 3);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (2, 2, 'Не понравился продукт', NOW(), NOW(), 2);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (2, 3, 'Отличные футболки. Заказала несколько штук в разных цветах. Размер соответствует. После стирки не изменилась и не полиняла.', NOW(), NOW(), 2);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (2, 4, 'Футболка пришла хорошо упакованная. Плотный хлопок. На вид хорошего качества. Посмотрим как будет в носке. В целом, сыну подошло.', NOW(), NOW(), 3);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (2, 5, 'Футболка из плотного приятного материала, очень порадовало качество. Не ожидала получить за такую цену качественную вещь. Благодарю продавца 🍀', NOW(), NOW(), 3);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (3, 1, 'Прекрасный продукт!', NOW(), NOW(), 5);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (3, 2, 'Безупречное качество', NOW(), NOW(), 4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (3, 3, 'Безупречное качество', NOW(), NOW(), 4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (3, 4, 'Отличная футболка. Плотная, не просвечивает, белоснежная . цена отличная. Очень довольна покупкой, еще закажу', NOW(), NOW(), 4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (4, 1, 'Футболка плотная, после стирки сохранила форму, не линяет,швы прошиты хорошо, конечно больше мужская.Цена супер!Рекомендую к покупке.', NOW(), NOW(), 3);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (4, 2, 'Хороший товар.', NOW(), NOW(), 3);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (4, 3, 'Размер соответствует., цвет белый,плотность ткани высокая как заказывала, рекомендую, берите по размеру, рекомендую', NOW(), NOW(), 5);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (4, 4, 'Лучший продукт из всех, что я пробовала!', NOW(), NOW(), 5);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (5, 1, 'Материал отличный, сидит отлично. В Фамилии даже несколько хуже по качеству, чем эта. Рекомендую.', NOW(), NOW(), 3);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (6, 1, 'Отлично, взяла как ночнушку, единственный минус, то что в ней очень жарко, а так, если брать на прогулку осенью, то самое то))', NOW(), NOW(), 3);
INSERT INTO comments (product_id, user_id, message, create_date, update_date,rating)
VALUES (6, 2, 'Кофта приятная к телу, качество хорошее, но с размером не угадали, оказался большой', NOW(), NOW(),3);
INSERT INTO comments (product_id, user_id, message, create_date, update_date,rating)
VALUES (6, 3, 'ткань неприятная к телу , выглядит не так как на фото', NOW(), NOW(),3);
INSERT INTO comments (product_id, user_id, message, create_date, update_date,rating)
VALUES (6, 4, 'Кофта отличная в размер', NOW(), NOW(),4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date,rating)
VALUES (7, 1, 'Ставлю 5 звёздочек качество супер.', NOW(), NOW(),4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date,rating)
VALUES (7, 2, 'Апупенная кофта но - сто рукав был чуть чуть грязный закинула стирать.', NOW(), NOW(),4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date,rating)
VALUES (7, 3, 'имбуля, но на рост 157 см. длинноватые рукова и слишком широко:)', NOW(), NOW(),4);
INSERT INTO comments (product_id, user_id, message, create_date, update_date,rating)
VALUES (17, 2, 'Рюкзак действительно удобный!', NOW(), NOW(),2);
INSERT INTO comments (product_id, user_id, message, create_date, update_date,rating)
VALUES (18, 1, 'Нравится дизайн рюкзака.', NOW(), NOW(),1);
INSERT INTO comments (product_id, user_id, message, create_date, update_date, rating)
VALUES (39, 10, 'Можно сказать, что продукт среднего качества.', NOW(), NOW(), 3);

INSERT INTO categories (title, title_url, description, image_url)
VALUES ('Одежда','cloth', 'Одежда для вашего хорошего настроения', 'cloth.jpeg'),
       ('Aксессуары','accessories', 'Аксессуары для вас на каждый день от LETI', 'accessories.jpeg'),
       ('Для дома','for-home', 'Все ваши любимые вещи для комфорта и уюта', 'for-home.jpeg'),
       ('Подарочные наборы','gift-baskets', 'Подарки любят все, поэтому стоимт заглянуть', 'gift-baskets.jpg');

INSERT INTO categories_subcategories (categories_id, subcategories_id)
VALUES (1,1),
       (1,2),
       (2,3),
       (2,4),
       (2,5),
       (2,6),
       (2,7),
       (2,8),
       (2,9),
       (3,10),
       (3,11),
       (3,12),
       (4,13);

INSERT INTO buckets (id, users_id, total_cost, amount_products)
VALUES (2, 1, 5999, 1);
INSERT INTO buckets_products (buckets_id, products_id)
VALUES (2, 1);

INSERT INTO buckets_products (buckets_id, products_id)
VALUES (2, 2);
INSERT INTO buckets_products (buckets_id, products_id)
VALUES (2, 4);
