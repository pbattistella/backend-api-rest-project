CREATE TABLE activity
(
    id bigserial,
    name character varying(100) NOT NULL,
    description text,
    start_date date,
    end_date date,
    customer bigint,
    project bigint,
    CONSTRAINT activity_pkey PRIMARY KEY (id),
    CONSTRAINT customer_fk_in_activity FOREIGN KEY (customer) REFERENCES customer(id),
    CONSTRAINT project_fk_in_activity FOREIGN KEY (project) REFERENCES project(id)
 );