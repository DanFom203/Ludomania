CREATE EXTENSION IF NOT EXISTS 'uuid-ossp';

create table users (
                       id uuid primary key default uuid_generate_v4(),
                       first_name varchar(50) NOT NULL,
                       last_name varchar(50) NOT NULL,
                       password varchar(50) NOT NULL,
                       email varchar(50) ,
                       birthdate date NOT NULL
);