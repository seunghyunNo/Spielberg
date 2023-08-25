## test code

SELECT TABLE_NAME FROM information_schema.TABLES
WHERE TABLE_SCHEMA = 'moviedb';

SELECT * FROM customer;
SELECT * FROM customer_authorities;
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

select * from user;

INSERT INTO moviedb.`user`
(birthday, created_at, updated_at, gender, phone_num, name, password, username, email, status)
VALUES('2000-05-03', NOW(), NOW(), 'MALE', '00011112222', '나', 'ㅇㅇㅇㅇㅇ', '나', 'PPP@VVV.COM', 'ACTIVE');

 
