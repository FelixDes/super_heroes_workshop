create database heroes_db;

create user felix;
ALTER USER felix WITH PASSWORD 'felix_pw';

GRANT ALL ON SCHEMA public to felix;