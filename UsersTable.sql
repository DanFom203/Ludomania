CREATE EXTENSION IF NOT EXISTS 'uuid-ossp';

create table users (
                    id uuid primary key default uuid_generate_v4(),
                    first_name varchar(50) NOT NULL,
                    last_name varchar(50) NOT NULL,
                    email varchar(50) ,
                    password varchar(50) NOT NULL,
                    birthdate date NOT NULL,
                    foreign key (id) references users_skins_connection(user_id)
);