INSERT INTO restaurant (ID, NAME) VALUES
(200, 'rest1'),
(201, 'rest2');

INSERT INTO users (ID, username, PASSWORD) VALUES
  (100, 'u', 'u'),
  (101, 'a', 'a'),
  (102, 'a2', 'a2'),
  (103, 'm', 'm');

INSERT INTO ADMINISTRATORS (RESTAURANT_ID, USER_ID) VALUES
  (200, 101),
  (200, 102);

INSERT INTO user_role (roles, user_id) VALUES
  ('ROLE_MANAGER', 103), ('ROLE_ADMIN', 103),  ('ROLE_USER', 103),
  ('ROLE_ADMIN', 101),  ('ROLE_USER', 101),
  ('ROLE_ADMIN', 102),  ('ROLE_USER', 102);



INSERT INTO lunch_menu (ID, NAME, RESTAURANT_ID, DATE) VALUES
(300, 'вторник', 200, '2018-09-30'),
(302, 'бизнес_понедельник', 201, '2018-09-30'),
(301, 'wednesday', 200, '2018-09-29');


INSERT INTO MEAL (ID, NAME, PRICE, RESTAURANT_ID, LUNCH_MENU_ID) VALUES
(400, 'meat', 15, 200, 300),
(401, 'water', 23, 200, 300),
(402, 'салат', 47, 200, 300),
(403, 'суп', 14, 200, 301),
(404, 'empty plate', 0, 201, 301);


INSERT INTO VOTING_HISTORY (ID, DATE, VOTES, RESTAURANT_ID) VALUES
(500, '2018-09-30',  53, 200),
(501, '2018-09-30',  67, 201);

INSERT INTO VOTE (ID, USER_ID, DATE, RESTAURANT_ID) VALUES
(600, 100, '2018-09-30',  200),
(601, 101, '2018-09-30',  200),
(602, 102, '2018-09-30',  201);