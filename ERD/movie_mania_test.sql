## test code

SELECT TABLE_NAME FROM information_schema.TABLES
WHERE TABLE_SCHEMA = 'moviedb';

SELECT * FROM user;
SELECT * FROM user_authorities;
select * from movie;
select * from cinema;
select * from theater;
select * from show_info;
select * from price_info;
select * from seat;
select * from ticket_info;
select * from review;
select * from recommend;
select * from report;
select * from question;
select * from review r;

UPDATE ticket_info 
SET ticket_code='ASDFGTR'
WHERE user_id != 1;

select m.*, si.status from movie m LEFT JOIN show_info si on m.id = si.id;


select * from user;



SELECT * FROM authority;
SELECT * FROM user_authorities ;

INSERT INTO moviedb.user_authorities
(authorities_id, user_id)
VALUES(2, 5);


SELECT a.name, ua.user_id FROM authority a, user_authorities ua WHERE a.id = ua.authorities_id;

select * from show_info where status = "now";

INSERT INTO moviedb.`user`
(birthday, created_at, updated_at, gender, phone_num, name, password, username, email, status)
VALUES('2000-05-03', NOW(), NOW(), 'MALE', '00011112222', '나', 'ㅇㅇㅇㅇㅇ', '나', 'PPP@VVV.COM', 'ACTIVE');

 

INSERT INTO movie
(open_date, show_time, audi_cnt, movie_code, directors, genre, rate_grade, title, actors, img)
VALUES('20240822', 0, 0, '0', '1', '1', '1', '1', '1', '1.img');

INSERT INTO movie
(open_date, show_time, audi_cnt, movie_code, directors, genre, rate_grade, title, actors, img)
VALUES('20240922', 0, 0, '2', '2', '2', '2', '2', '2', '2');


INSERT INTO movie
(open_date, show_time, audi_cnt, movie_code, directors, genre, rate_grade, title, actors, img)
VALUES('20240822', 0, 0, '3', '3', '3', '3', '3', '3', '3');

INSERT INTO movie
(open_date, show_time, audi_cnt, movie_code, directors, genre, rate_grade, title, actors, img)
VALUES('20240822', 0, 0, '4', '4', '4', '4', '3', '23', '3');

INSERT INTO movie
(open_date, show_time, audi_cnt, movie_code, directors, genre, rate_grade, title, actors, img)
VALUES('20240822', 0, 0, '5', '4', '4', '4', '3', '23', '3');
INSERT INTO movie
(open_date, show_time, audi_cnt, movie_code, directors, genre, rate_grade, title, actors, img)
VALUES('20240822', 0, 0, '6', '4', '4', '4', '3', '23', '3');

INSERT INTO movie
(open_date, show_time, audi_cnt, movie_code, directors, genre, rate_grade, title, actors, img)
VALUES('20240822', 0, 0, '7', '4', '4', '4', '3', '23', '3');

INSERT INTO movie
(open_date, show_time, audi_cnt, movie_code, directors, genre, rate_grade, title, actors, img)
VALUES('20240822', 0, 0, '8', '4', '4', '4', '3', '23', '3');




INSERT INTO show_info
(movie_id, show_date_time, theater_id, status)
VALUES(2, '20240823110000', 1, 'NOW');

INSERT INTO ticket_info
(price_info_id, show_info_id, user_id, ticket_code)
VALUES(1, 2, 1, '티켓코드'),
(1, 3, 1, '티켓코드'),
(1, 4, 1, '티켓코드'),
(1, 5, 1, '티켓코드'),
(1, 2, 3, '티켓코드'),
(1, 5, 3, '티켓코드');

INSERT INTO seat
(seat_column, seat_row, ticket_info_id)
VALUES(10, 1, 2),
(10, 2, 2),
(10, 3, 2),
(10, 4, 2),
(10, 1, 3),
(10, 2, 3),
(10, 3, 3),
(9, 1, 3),
(10, 1, 4),
(10, 2, 4),
(10, 3, 4),
(10, 1, 5),
(10, 2, 5),
(10, 1, 6),
(10, 2, 6),
(10, 1, 7);


UPDATE price_info 
SET name = '학생'
WHERE id='2';

INSERT INTO review (score, created_at, movie_id, updated_at, user_id, content)
VALUES
  (3, NOW(), 1, NOW(), 1, '좋은 영화'),
  (4, NOW(), 1, NOW(), 2, '재미있는 영화'),
  (2, NOW(), 1, NOW(), 3, '별로에요...'),
  (1, NOW(), 1, NOW(), 4, '웩'),
  (5, NOW(), 1, NOW(), 5, '훌륭해요'),
  (3, NOW(), 1, NOW(), 6, '그냥그럼');

INSERT INTO `user`
(birthday, created_at, updated_at, gender, phone_num, name, password, username, email, status)
VALUES
  ('1990-01-01', NOW(), NOW(), 'MALE', '123-456-7890', 'John Doe', 'password123', 'john_doe', 'john@example.com', 'ACTIVE'),
  ('1985-05-15', NOW(), NOW(), 'FEMALE', '987-654-3210', 'Jane Smith', 'password456', 'jane_smith', 'jane@example.com', 'ACTIVE'),
  ('1992-09-22', NOW(), NOW(), 'MALE', '555-555-5555', 'Michael Johnson', 'password789', 'michael_johnson', 'michael@example.com', 'ACTIVE'),
  ('1988-03-12', NOW(), NOW(), 'FEMALE', '222-333-4444', 'Emily Brown', 'password111', 'emily_brown', 'emily@example.com', 'ACTIVE'),
  ('1995-11-25', NOW(), NOW(), 'MALE', '777-888-9999', 'Daniel Lee', 'password222', 'daniel_lee', 'daniel@example.com', 'ACTIVE'),
  ('1999-07-07', NOW(), NOW(), 'FEMALE', '444-555-6666', 'Olivia Wilson', 'password333', 'olivia_wilson', 'olivia@example.com', 'ACTIVE');
