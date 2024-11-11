DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS restaurants;
DROP SEQUENCE IF EXISTS global_seq CASCADE;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    firstname        VARCHAR                 NOT NULL,
    lastname         VARCHAR                 NOT NULL,
    email            VARCHAR                 NOT NULL,
    password         VARCHAR                 NOT NULL,
    registered       TIMESTAMP DEFAULT now() NOT NULL,
    enabled          BOOL DEFAULT TRUE       NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants (
                             id              INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
                             restaurant_name VARCHAR                NOT NULL,
                             address         VARCHAR                NOT NULL
);
CREATE UNIQUE INDEX restaurants_unique_restaurant_name_address_idx ON restaurants (restaurant_name, address);

CREATE TABLE dishes (
                        id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
                        description   VARCHAR             NOT NULL,
                        cost          DECIMAL             NOT NULL,
                        restaurant_id INTEGER             NOT NULL,
                        menu_date     DATE DEFAULT now()  NOT NULL,
                        FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX dishes_unique_description_restaurant_menu_date_idx ON dishes (description, restaurant_id, menu_date);

CREATE TABLE votes
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    restaurant_id INTEGER             NOT NULL,
    user_id       INTEGER             NOT NULL,
    date          DATE DEFAULT now()  NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX votes_unique_user_date_idx ON votes (user_id, date);