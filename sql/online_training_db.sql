create database online_training_db;

use online_training_db;

create table course (
id_course int auto_increment,
c_title varchar (255) unique not null,
c_description varchar (2000) unique not null,
course_type int not null,
teacher_id int not null,
course_status ENUM('open', 'in_process', 'closed') not null,
is_available TINYINT(1) DEFAULT 1 not null,
primary key (id_course),
foreign key (course_type) references online_training_db.course_type(id_type),
foreign key (teacher_id) references online_training_db.user_account(id_account)
);

create table course_enrolment (
c_e_student_id int not null,
c_e_course_id int not null,
primary key (student_id, course_id),
foreign key (c_e_student_id) references online_training_db.user_account(id_account),
foreign key (c_e_course_id) references online_training_db.course(id_course)
);

create table course_type (
id_type int auto_increment not null
category varchar (50) unique not null,
primary key (id_type)
);

create table user_account (
id_account int auto_increment,
e_mail varchar (100) unique not null,
u_password varchar (100) not null,
first_name varchar (50) not null,
last_name varchar (50) not null,
account_role ENUM('admin', 'teacher', 'student'),
status_is_deleted tinyint(1) not null default 0,
primary key (id_account)
);

use online_training_db;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteTeacher`(in id INT)
BEGIN
UPDATE user_account
SET status_is_deleted = 1
WHERE id_account = id;

UPDATE course
SET teacher_id=NULL
WHERE teacher_id = id;
END;;
  DELIMITER ;

create table task (
task_id int auto_increment,
t_title varchar(50) not null,
description varchar (2000) unique not null,
course_id int not null,
primary key (id_task),
foreign key (course_id) references online_training_db.course(id_course)
);

create table task_review (
student_id int not null,
t_task_id int not null,
task_answer  varchar (255),
teacher_comment varchar (255),
mark int,
foreign key (t_task_id) references online_training_db.task(task_id),
foreign key (student_id) references online_training_db.user_account(id_account)
);

DROP TRIGGER IF EXISTS online_training_db.task_AFTER_INSERT;
DELIMITER $$
USE `online_training_db`$$
CREATE DEFINER = CURRENT_USER TRIGGER online_training_db.task_AFTER_INSERT AFTER INSERT ON task FOR EACH ROW
BEGIN
    SET @task_id = (SELECT MAX(task_id) FROM task);
    SET @id_course = (SELECT course_id FROM task WHERE task_id = @task_id);
INSERT INTO task_review (student_id, t_task_id)
SELECT c_e_student_id, @task_id FROM course_enrolment WHERE c_e_course_id = @id_course;
END
$$
DELIMITER ;