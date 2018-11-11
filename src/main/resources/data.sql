INSERT INTO restaurant (ID, NAME, ADMINISTRATION_PASSWORD) VALUES
(200, 'rest1', '111'),
(201, 'rest2', '222');

INSERT INTO users (ID, username, PASSWORD, restaurant_id) VALUES
  (100, 'u', 'u', null),
  (101, 'a', 'a', 200),
  (102, 'a2', 'a2', 201);

INSERT INTO user_role (roles, user_id) VALUES
  ('USER', 100),
  ('ADMIN', 101),
  ('USER', 101),
  ('ADMIN', 102),
  ('USER', 102);



INSERT INTO lunch_menu (ID, NAME, RESTAURANT_ID, DATE) VALUES
(300, 'вторник', 200, '2018-09-30'),
(302, 'бизнес_понедельник', 201, '2018-09-30'),
(301, 'sreda', 200, '2018-09-29');


INSERT INTO MEAL (ID, NAME, PRICE, RESTAURANT_ID, LUNCH_MENU_ID) VALUES
(400, 'myaso', 15, 200, 300),
(401, 'pivo', 23, 200, 300),
(402, 'водка', 47, 200, 300),
(403, 'суп', 14, 200, 301),
(404, 'nothing', 0, 201, 301);


INSERT INTO VOTING_HISTORY (ID, DATE, VOTES, RESTAURANT_ID) VALUES
(500, '2018-09-30',  53, 200),
(501, '2018-09-30',  67, 201);

INSERT INTO VOTE (ID, USER_ID, DATE, RESTAURANT_ID) VALUES
(600, 100, '2018-09-30',  200),
(601, 101, '2018-09-30',  200),
(602, 102, '2018-09-30',  201);