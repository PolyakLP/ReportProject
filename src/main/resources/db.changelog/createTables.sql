create table LOCATIONS(
id uuid NOT NULL,
manager_name varchar(255),
phone varchar(255) ,
address_primary varchar(255) ,
address_secondary varchar(255) ,
country varchar(255) ,
town varchar(255) ,
postal_code varchar(255) ,
primary key (id)
);

create table Status(
id serial NOT NULL PRIMARY KEY,
status_name varchar(255) NOT NULL
);

create table Marketplace(
id serial NOT NULL PRIMARY KEY,
marketplace_name varchar(255) NOT NULL
);

create table LISTINGS (
id uuid NOT NULL,
title varchar(255) NOT NULL,
description varchar(255) NOT NULL,
location uuid REFERENCES LOCATIONS(id),
listing_price NUMERIC(9, 2) NOT NULL,
currency varchar(3) NOT NULL,
quantity integer NOT NULL,
listing_status integer NOT NULL,
marketplace integer NOT NULL,
upload_time varchar(255),
owner_email_address varchar(255),
primary key (id,location)
);

--create table LISTINGS (
--id uuid NOT NULL,
--title varchar(255) NOT NULL,
--description varchar(255) NOT NULL,
--location uuid,
--listing_price NUMERIC(9, 2) NOT NULL,
--currency varchar(3) NOT NULL,
--quantity integer NOT NULL,
--listing_status integer NOT NULL,
--marketplace integer NOT NULL,
--upload_time varchar(255),
--owner_email_address varchar(255),
--primary key (id)
--);


