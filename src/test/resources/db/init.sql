create database books_system;

\connect books_system;

create table books (
  id bigint primary key generated always as identity,
  name character varying(255) not null,

  CONSTRAINT books_name_uni UNIQUE (name)
);

insert into books (name) values ('FreeBSD'), ('Debian'), ('UNIX'), ('xv6');