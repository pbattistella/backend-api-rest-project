CREATE TABLE customer
(
    id bigserial,
    full_name character varying(100) NOT NULL,
    email character varying(120) NOT NULL,
    phone character varying(120) NOT NULL,
    gender character varying(15) NOT NULL,
    birth_date date NOT NULL,
    CONSTRAINT customer_pkey PRIMARY KEY (id)
);