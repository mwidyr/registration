CREATE TABLE if not exists users(
    id serial PRIMARY KEY,
    first_name VARCHAR(255) not null ,
    last_name VARCHAR(255) not null ,
    mobile_number VARCHAR(255) not null,
    email VARCHAR(255) not null ,
    gender VARCHAR(50),
    date_of_birth date);