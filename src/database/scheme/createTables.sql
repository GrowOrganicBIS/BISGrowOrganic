# 1. create Java DB with name IS2209, no user/password for db required
# 2. execute every sql query from creatTables.sql, make sure it is
# properly executed and created...., execute them one by one..

create table "APP".REMEMBERUSER
(
	ID INTEGER not null primary key,
	EMAIL VARCHAR(255),
	PASSWORD VARCHAR(255)
)

# Insert a value into REMEMBERUSER, so the remember me function does
# not have to care about the insert opration but only update operation
INSERT into table "APP.REMEMBERUSER" values (1, '', '')

create table "APP".USERS
(
	EMAIL VARCHAR(255) not null primary key,
	FIRSTNAME VARCHAR(255),
	LASTNAME VARCHAR(255),
	PASSWORD VARCHAR(255),
	ADDRESS VARCHAR(255)
)