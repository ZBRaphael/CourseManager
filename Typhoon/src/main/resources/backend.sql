CREATE TABLE STUDENT(
	stuId int(32) primary key AUTO_INCREMENT,
	stuUsername varchar(128),
	stuPassword varchar(32),
	interest varchar(64),
	stuRemainingClassHour int default 0,
	stuTotalClassHour int default 0,
	stuTell varchar(64)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;
CREATE TABLE TEACHER(
	teaId int(32) primary key AUTO_INCREMENT,
	teaUsername varchar(128),
	teaPassword varchar(128)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;
CREATE TABLE COURSE(
	courseId int(32) primary key AUTO_INCREMENT,
	courseStartDate varchar(128),
	courseEndDate varchar(128),
	courseCostHour int,
	courseLocation varchar(256),
	teaId int(32),
	interest varchar(64),
	courseDescription varchar(512) default null,
	isCanceled int DEFAULT 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;
CREATE TABLE ATTENDCLASS(
	attendClassId int(32) primary key AUTO_INCREMENT,
	courseId int(32),
	stuId int(32),
	stuUsername varchar(128),
	stuTell varchar(64),
	courseDate varchar(128),
	courseLocation varchar(256),
	isAttend int DEFAULT 0,
	isCanceledByStu int DEFAULT 0,
	isCanceledByTea int DEFAULT 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;