ALTER SEQUENCE global_seq RESTART WITH 100000;
INSERT INTO USERS (ID, FIRSTNAME,LASTNAME,PASSWORD, EMAIL, REGISTERED, ENABLED) VALUES
                                                                (0,'Oleg','Plotnikov','user_password', 'user@email.com', '2019-04-23 10:00:00', TRUE),
                                                                (1, 'Ekaterina','Konova','admin_password', 'admin@email.com', '2019-04-23 12:00:00', TRUE);
INSERT INTO user_roles (user_id, role) VALUES
                                           (0, 'ROLE_USER'),
                                           (1, 'ROLE_ADMIN');
INSERT INTO RESTAURANTS (ID, NAME) VALUES
                                      (2, 'KFC'),
                                      (3, 'McDonalds'),
                                      (4, 'BurgerKing');
INSERT INTO MENUS (ID,  MENU_DATE, RESTAURANT_ID) VALUES
                                                     (10, '2019-04-19', 2),
                                                     (11, '2019-04-20', 3),
                                                     (12, '2019-06-11', 3),
                                                     (13, '2019-06-11', 4),
                                                     (14, '2019-06-12', 2);
INSERT INTO DISHES (ID, description, cost, MENU_ID) VALUES
                                                (100, 'Steak', 100000,10),
                                                (101, 'Hamburger', 10000, 11),
                                                (102, 'Bugs', 20000, 12),
                                                (103, 'McSteak', 11100, 12),
                                                (104, 'McVine', 22200, 12),
                                                (105, 'Takoburger', 33300, 13);
INSERT INTO votes (ID, DATE, USER_ID, MENU_ID) VALUES
                                                       (200, '2019-04-20', 0, 11),
                                                       (201, '2019-04-20', 1, 11),
                                                       (203, '3019-06-11', 0, 12);
