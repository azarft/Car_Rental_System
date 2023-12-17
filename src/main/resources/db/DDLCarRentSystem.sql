CREATE TABLE administrator (
    id SERIAL,
    email VARCHAR, 
    "name" VARCHAR,
    surname VARCHAR,
    "position" VARCHAR,
    phone VARCHAR, 
    password VARCHAR, 
    CONSTRAINT administrator_pk PRIMARY KEY (id)
);
CREATE table "user" (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE),
	"name" varchar,
	surname varchar,
	email varchar,
	phone varchar,
	address varchar,
	"password" varchar,
	birthday date NULL,
	CONSTRAINT user_pk PRIMARY KEY (id)
);


CREATE table car (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE),
	"type" varchar,
	price_per_day numeric,
	color varchar,
	brand varchar,
	volume int4,
	capacity int4,
	CONSTRAINT car_pk PRIMARY KEY (id)
);

CREATE table passport (
    user_id SERIAL PRIMARY KEY,
    passportnumber CHAR(14) NOT NULL,
    CONSTRAINT passport_fk
        FOREIGN KEY (user_id)
        REFERENCES "user" (id)
        ON DELETE cascade
        ON UPDATE CASCADE
);

CREATE TABLE license (
    user_id SERIAL PRIMARY KEY,
    "number" CHAR(10) NOT NULL,
    category VARCHAR NULL,
    CONSTRAINT license_fk
        FOREIGN KEY (user_id)
        REFERENCES "user" (id)
        ON DELETE cascade
        ON UPDATE CASCADE
);

CREATE TABLE images (
    car_id SERIAL PRIMARY KEY,
    image_path VARCHAR NULL,
    CONSTRAINT images_fk
        FOREIGN KEY (car_id)
        REFERENCES car (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE reservation (
	reservation_id SERIAL PRIMARY KEY,
    user_id INT4,
    car_id INT4,
    starting_date DATE,
    ending_date DATE,
    pickup_location VARCHAR,
    return_location VARCHAR,
    total_cost NUMERIC(10, 2),
    arrival_time date,
   	arrival_fine numeric,
    CONSTRAINT reservation_user_fk FOREIGN KEY (user_id) REFERENCES "user" (id),
    CONSTRAINT reservation_car_fk FOREIGN KEY (car_id) REFERENCES car (id)
);

CREATE TABLE payment (
    payment_id SERIAL PRIMARY KEY,
    user_id INT4,
    card_number CHAR(19),
    card_holder VARCHAR,
    expiration_date DATE,
    cvc CHAR(4) NOT NULL,
    CONSTRAINT payment_fk FOREIGN KEY (user_id) REFERENCES public."user" (id)
);



