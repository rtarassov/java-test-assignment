CREATE TABLE order_ (
id int NOT NULL AUTO_INCREMENT,
client_id int,
address clob,
date_created timestamp,
price DECIMAL(10, 2) DEFAULT 9.99
);

CREATE TABLE pizza (
id int NOT NULL AUTO_INCREMENT,
name varchar,
price decimal(20,2)
);

CREATE TABLE order_pizza (
id int NOT NULL AUTO_INCREMENT,
order_id int,
pizza_id int
);

CREATE TABLE user (
id int NOT NULL AUTO_INCREMENT,
username varchar,
password varchar,
is_admin boolean,
enabled boolean,
date_created timestamp
);
