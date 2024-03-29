insert into users(username, email, password, profile_picture)
values ('admin', 'admin@test.nl', '$2a$10$ZP50cAmIg35v5Ozhe5OuhuGSFs5p6gA5OuuqJyUnvrgMLbfjNKC3i',
        'http://localhost:8080/api/v1/files/1675255037594profielfoto.jpg'),
       ('user', 'user@test.nl', '$2a$10$ZP50cAmIg35v5Ozhe5OuhuGSFs5p6gA5OuuqJyUnvrgMLbfjNKC3i',
        'http://localhost:8080/api/v1/files/cat1.jpg'),
       ('cook', 'cook@test.nl', '$2a$10$ZP50cAmIg35v5Ozhe5OuhuGSFs5p6gA5OuuqJyUnvrgMLbfjNKC3i',
        'http://localhost:8080/api/v1/files/cat2.jpg'),
       ('Garth', 'cat3@test.nl', '$2a$10$ZP50cAmIg35v5Ozhe5OuhuGSFs5p6gA5OuuqJyUnvrgMLbfjNKC3i',
        'http://localhost:8080/api/v1/files/cat3.jpg'),
       ('cat4', 'cat4@test.nl', '$2a$10$ZP50cAmIg35v5Ozhe5OuhuGSFs5p6gA5OuuqJyUnvrgMLbfjNKC3i',
        'http://localhost:8080/api/v1/files/cat4.jpg'),
       ('cat5', 'cat5@test.nl', '$2a$10$ZP50cAmIg35v5Ozhe5OuhuGSFs5p6gA5OuuqJyUnvrgMLbfjNKC3i',
        'http://localhost:8080/api/v1/files/cat5.jpg'),
       ('cat6', 'cat6@test.nl', '$2a$10$ZP50cAmIg35v5Ozhe5OuhuGSFs5p6gA5OuuqJyUnvrgMLbfjNKC3i',
        'http://localhost:8080/api/v1/files/cat6.jpg'),
       ('cat7', 'cat7@test.nl', '$2a$10$ZP50cAmIg35v5Ozhe5OuhuGSFs5p6gA5OuuqJyUnvrgMLbfjNKC3i',
        'http://localhost:8080/api/v1/files/cat7.jpg'),
       ('cat8', 'cat8@test.nl', '$2a$10$ZP50cAmIg35v5Ozhe5OuhuGSFs5p6gA5OuuqJyUnvrgMLbfjNKC3i',
        'http://localhost:8080/api/v1/files/cat8.jpg');
insert into user_roles(user_id, roles)
values (1, 'ADMIN'),
       (1, 'USER'),
       (1, 'COOK'),
       (2, 'USER'),
       (3, 'USER'),
       (3, 'COOK'),
       (4, 'USER'),
       (5, 'USER'),
       (6, 'USER'),
       (7, 'USER'),
       (8, 'USER'),
       (9, 'USER');
insert into cooks_customers(cook_id, customer_id)
values (3, 1),
       (3, 2),
       (3, 4),
       (3, 5),
       (3, 6),
       (3, 7),
       (3, 8),
       (3, 9);
insert into menus (cook_id, dessert, end_delivery_window, main, menu_description, menu_pictureurl, menu_type,
                   number_of_menus, order_deadline, price_menu, send_to_customers, side, start_delivery_window, starter,
                   tikkie_link, title, warm_up_instruction)
values (3, 'Millionaires shortbread', '2023-02-11 19:00:00', 'Eggplant with a tomato sauce',
        'It tastes heavenly and that without animal products',
        'http://localhost:8080/v1/files/eggplant-menu-picture.jpg', 'VEGAN', 25, '2024-02-10 17:00:00', 12.5, false,
        'Salad with grapefruit and sumac', '2023-02-11 17:00:00', 'Fruits de mer', 'https://www.novi.nl/',
        'Best menu ever', '1. Starter can be served cold.2. Eggplant can you warm up in the oven.');
insert into customer_menu(menu_id, customer_id)
values (1, 1),
       (1, 2),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9);
insert into deliveries(eta, paid)
values ('2023-02-11T19:00:00', false),
       ('2023-02-11T19:30:00', true),
       ('2023-02-11T19:45:00', false);
insert into orders(allergies, allergies_explanation, city, comments, declined, end_delivery_window, number_of_menus,
                   order_date_and_time, start_delivery_window, zipcode, delivery_id, menu_id, order_customer_id,
                   street_and_number)
values ('pinda', 'I will swell up after eating one trace', 'Catcity',
        'You can drop it behind the tree at the front door', false, '2023-02-11 19:00:00', 3, '2023-04-11 17:00:00',
        '2023-02-11 17:00:00', '3511PB', 1, 1, 4, 'catpark 1')
     , (null, null, 'Catcity', '', true, '2023-02-11 19:00:00', 2, '2023-04-11 17:30:00', '2023-02-11 18:00:00',
        '3511PB', null, 1, 5, 'catstreet 53a')
     , (null, null, 'Catcity', '', false, '2023-02-11 19:00:00', 5, '2023-04-11 17:45:00', '2023-02-11 19:00:00',
        '3511PB', null, 1, 6, 'Uppercatstreet 89')
     , (null, null, 'Catcity', '', false, '2023-02-11 19:00:00', 1, '2023-04-11 18:30:00', '2023-02-11 20:00:00',
        '3511PB', 2, 1, 7, 'catcitystreet 130')
     , (null, null, 'Catcity', '', false, '2023-02-11 19:00:00', 1, '2023-04-11 19:21:00', '2023-02-11 21:00:00',
        '3511PB', 3, 1, 8, 'tigerlane 23')
     , (null, null, 'Catcity', '', false, '2023-02-11 19:00:00', 3, '2023-04-11 20:00:00', '2023-02-11 22:00:00',
        '3511PB', null, 1, 9, 'Tigerdecatstreet 66')
     , (null, null, 'Catcity', '', false, '2023-02-11 19:00:00', 1, '2023-04-11 21:00:00', '2023-02-11 23:00:00',
        '3511PB', null, 1, 1, 'lionsquare 32')
     , (null, null, 'Catcity', '', false, '2023-02-11 19:00:00', 7, '2023-04-11 22:00:00', '2023-02-11 22:30:00',
        '3511PB', null, 1, 2, 'mousestreet 24');