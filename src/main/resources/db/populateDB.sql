DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, description, date_time, calories) VALUES
(100000, 'Breakfast', '2019-10-24 09:00:00', 500),
(100000, 'Lunch', '2019-10-24 14:00:00', 1000),
(100000, 'Supper', '2019-10-24 19:00:00', 510),
(100001, 'Breakfast', '2019-10-24 09:00:00', 500),
(100001, 'Lunch', '2019-10-24 14:00:00', 1000),
(100001, 'Supper', '2019-10-24 19:00:00', 500);