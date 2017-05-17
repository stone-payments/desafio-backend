CREATE TABLE public.creditcard
(
    id integer NOT NULL DEFAULT nextval('credcard_id_seq'::regclass),
    cardnumber character varying(100) COLLATE "default".pg_catalog,
    cardholdername character varying(100) COLLATE "default".pg_catalog,
    value bigint,
    cvv bigint,
    expdate character varying(5) COLLATE "default".pg_catalog,
    CONSTRAINT credcard_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.creditcard
    OWNER to postgres;