CREATE TABLE STUDENT(
	stuId int(32) primary key AUTO_INCREMENT,
	stuUsername varchar(128),
	stuPassword varchar(32),
	interest varchar(64),
	stuRemainingClassHour int,
	stuTell varchar(64)
);
CREATE TABLE TEACHER(
	teaId int(32) primary key AUTO_INCREMENT,
	teaUsername varchar(128),
	teaPassword varchar(128)
);
CREATE TABLE COURSE(
	courseId int(32) primary key AUTO_INCREMENT,
	courseDate datetime,
	courseLocation varchar(256),
	teaId int(32),
	interest varchar(64),
	isCanceled int DEFAULT 0
);
CREATE TABLE ATTENDCLASS(
	courseId int(32),
	stuId int(32),
	courseDate datetime,
	courseLocation varchar(256),
	isAttend int DEFAULT 0
);