DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS menus;
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
    role    VARCHAR NOT NULL,
    CONSTRAINT user_roles_fk UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX user_role_unique_idx ON user_roles (user_id, role);

CREATE TABLE restaurants (
                             id    INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
                             name VARCHAR NOT NULL
);
CREATE UNIQUE INDEX unique_restaurant ON restaurants (name);
CREATE TABLE menus
(
    id             INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    menu_date     DATE    NOT NULL,
    restaurant_id INTEGER NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX unique_menu ON menus (menu_date, restaurant_id);
CREATE TABLE dishes (
                        id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
                        description   VARCHAR             NOT NULL,
                        cost          INTEGER            NOT NULL,
                        menu_id      INTEGER             NOT NULL,
                        FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX unique_dish ON dishes ( description,menu_id);

CREATE TABLE votes
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    menu_id INTEGER             NOT NULL,
    user_id       INTEGER             NOT NULL,
    date          DATE DEFAULT now()  NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX unique_vote ON votes (user_id, date);