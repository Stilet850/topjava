DELETE FROM topjava.topjava.user_roles;
DELETE FROM topjava.topjava.users;
ALTER SEQUENCE topjava.topjava.global_seq RESTART WITH 100000;

INSERT INTO topjava.topjava.users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO topjava.topjava.user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);
