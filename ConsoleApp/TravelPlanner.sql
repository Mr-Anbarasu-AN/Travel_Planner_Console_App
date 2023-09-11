show databases;
use miniproject;
create table user( userid int auto_increment primary key , fullname varchar(100) , 
email varchar(100) unique , phone bigint unique , password varchar(20));
create table trip( tripid int auto_increment primary key , destination varchar(100) , 
 accommodation varchar(100) , transport varchar(100)  , schedule varchar(100) );
ALTER TABLE trip 
ADD COLUMN userid INT,
ADD FOREIGN KEY (userid) REFERENCES user(userid);
show tables;
select * from user;
select * from trip;
desc trip;
desc user;
drop table trip;
drop table user;

