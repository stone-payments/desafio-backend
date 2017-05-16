CREATE TABLE public.transaction
(
    id integer NOT NULL DEFAULT nextval('transaction_id_seq'::regclass),
    clientid character varying(100) COLLATE "default".pg_catalog,
    clientname character varying(100) COLLATE "default".pg_catalog,
    totaltopay bigint,
    creditcardid bigint,
    date date,
    CONSTRAINT transaction_pkey PRIMARY KEY (id),
    CONSTRAINT creditcard_fk FOREIGN KEY (creditcardid)
        REFERENCES public.creditcard (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.transaction
    OWNER to postgres;