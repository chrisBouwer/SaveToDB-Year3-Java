DROP DATABASE IF EXISTS PIHE2019;

CREATE DATABASE PIHE2019;

USE PIHE2019;

CREATE TABLE details(
	StudentID int(3) NOT NULL PRIMARY KEY,
	Name varchar(50),
	Surname varchar(50),
	Contact varchar(10),
	Address varchar(100)
);