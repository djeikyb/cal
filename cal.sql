drop database if exists cal;

create database cal;

use cal;

drop table if exists events;
drop table if exists guests;

create table events
(
  id            double not null auto_increment,
  day           varchar (10),
  timeStart     varchar (5),
  timeEnd       varchar (5),
  kind          varchar (30),
  description   varchar (255),
  guests        varchar (255),

  primary key (id)
);

create table guests
(
  id      double not null auto_increment,
  name    varchar (255),
  email   varchar (255),

  primary key (id)
);
