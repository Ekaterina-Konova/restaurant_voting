DELETE
FROM VOTEs;
DELETE
FROM dishes;
DELETE
FROM RESTAURANTs;
DELETE
FROM MENUs;
DELETE
FROM USERS;
ALTER SEQUENCE global_seq RESTART WITH 100000;
INSERT INTO USERS (FIRSTNAME,LASTNAME,PASSWORD, EMAIL, REGISTERED, ENABLED) VALUES
                                                                ('Oleg','Plotnikov','user_password', 'user@email.com', '2019-04-23 10:00:00', TRUE),
                                                                ( 'Ekaterina','Konova','admin_password', 'admin@email.com', '2019-04-23 12:00:00', TRUE);
INSERT INTO user_roles (user_id, role) VALUES
                                           (100000, 'ROLE_USER'),
                                           (100001, 'ROLE_USER'),
                                           (100001, 'ROLE_ADMIN');
INSERT INTO RESTAURANTS (NAME) VALUES
                                      ( 'KFC'),
                                      ( 'McDonalds'),
                                      ( 'BurgerKing');
INSERT INTO MENUS ( MENU_DATE, RESTAURANT_ID) VALUES
                                                     ('2019-04-19', 100002),
                                                     ('2019-04-20', 100003),
                                                     ('2019-06-11', 100003),
                                                     ('2019-06-11', 100004),
                                                     ('2019-06-12', 100002);
INSERT INTO DISHES (description, cost, MENU_ID) VALUES
                                                ('Steak', 100000,10),
                                                ('Hamburger', 10000, 11),
                                                ('Bugs', 20000, 12),
                                                ('McSteak', 11100, 12),
                                                ('McVine', 22200, 12),
                                                ('Takoburger', 33300, 13);
INSERT INTO votes (DATE, USER_ID, MENU_ID) VALUES
                                                       ('2019-04-20', 0, 11),
                                                       ('2019-04-20', 1, 11),
                                                       ('2019-06-11', 0, 12);
