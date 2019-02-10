# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table domain (
  domain_id                     integer auto_increment not null,
  registered_email              varchar(255),
  domain_name                   varchar(255) not null,
  country                       varchar(255),
  created_date                  datetime(6) not null,
  website_url                   varchar(255) not null,
  updated_date                  datetime(6) not null,
  expiry_date                   datetime(6) not null,
  registrar_name                varchar(255) not null,
  constraint pk_domain primary key (domain_id)
);

create table domain_response (
  id                            integer not null,
  domain_name                   varchar(255),
  expiry_date                   datetime(6),
  year                          integer not null,
  month                         integer not null,
  day                           integer not null
);

create table test_ebean (
  user_id                       integer auto_increment not null,
  name                          varchar(255),
  constraint pk_test_ebean primary key (user_id)
);

create table user (
  user_id                       integer auto_increment not null,
  name                          varchar(255) not null,
  primary_email                 varchar(255),
  password                      varchar(255),
  active                        tinyint(1) not null,
  verified                      tinyint(1) not null,
  constraint uq_user_primary_email unique (primary_email),
  constraint pk_user primary key (user_id)
);

create table watch_list (
  watch_id                      integer auto_increment not null,
  user_id                       integer not null,
  domain_name                   varchar(255) not null,
  country                       varchar(255),
  created_date                  datetime(6) not null,
  website_url                   varchar(255) not null,
  updated_date                  datetime(6) not null,
  expiry_date                   datetime(6) not null,
  registrar_name                varchar(255) not null,
  constraint pk_watch_list primary key (watch_id)
);


# --- !Downs

drop table if exists domain;

drop table if exists domain_response;

drop table if exists test_ebean;

drop table if exists user;

drop table if exists watch_list;

