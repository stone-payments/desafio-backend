CREATE TABLE public.product
(
    id integer NOT NULL DEFAULT nextval('product_id_seq'::regclass),
    title character varying(100) COLLATE "default".pg_catalog,
    price bigint,
    zipcode character varying(20) COLLATE "default".pg_catalog,
    seller character varying(100) COLLATE "default".pg_catalog,
    thumbnailhd character varying(100) COLLATE "default".pg_catalog,
    date character varying(10) COLLATE "default".pg_catalog,
    CONSTRAINT product_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.product
    OWNER to postgres;