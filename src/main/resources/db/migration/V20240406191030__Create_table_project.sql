CREATE TABLE project
(
    id bigserial,
    name character varying(100) NOT NULL,
    description text,
    status character varying(50) NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    manager bigint NOT NULL,
    CONSTRAINT project_pkey PRIMARY KEY (id),
    CONSTRAINT manager_fk_in_project FOREIGN KEY (manager) REFERENCES customer(id)
 );