create database IF not exists Location;
use Location;

drop TABLE ads;
create table if not exists ads (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, lon float, lat float, radius INT, company VARCHAR(30), summary VARCHAR(100), expirationDate date, nrViews INT, url VARCHAR(100), oneTimeView bool, curNrViews INT DEFAULT 0);

drop TABLE haveWatched;
create table if not exists haveWatched (id INT, userId INT);

drop TABLE apps;
create table if not exists apps (app VARCHAR(30), approvedCompany VARCHAR(30));

drop TABLE companies;
create table if not exists companies ( company VARCHAR(30), approvedApp VARCHAR(30));

