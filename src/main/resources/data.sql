INSERT INTO restaurant (ID, NAME, ADMINISTRATION_PASSWORD) VALUES
(200, 'rest1', '111'),
(201, 'rest2', '222');

INSERT INTO users (ID, LOGIN, PASSWORD, restaurant_id) VALUES
  (100, 'u', 'u', null),
  (101, 'a', 'a', 200),
  (102, 'a2', 'a2', 201);

INSERT INTO user_role (roles, user_id) VALUES
  ('USER', 100),
  ('ADMIN', 101),
  ('USER', 101),
  ('ADMIN', 102),
  ('USER', 102);

