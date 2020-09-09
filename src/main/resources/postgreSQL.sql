-- SEQUENCE: public.produtos_consumiveis_seq

-- DROP SEQUENCE public.produtos_consumiveis_seq;

CREATE SEQUENCE public.produtos_consumiveis_seq
    INCREMENT 1
    START 0
    MINVALUE 0
    MAXVALUE 99999999
    CACHE 1;

ALTER SEQUENCE public.produtos_consumiveis_seq
    OWNER TO postgres;
	
-- SEQUENCE: public.produtos_seq

-- DROP SEQUENCE public.produtos_seq;

CREATE SEQUENCE public.produtos_seq
    INCREMENT 1
    START 0
    MINVALUE 0
    MAXVALUE 9999999
    CACHE 1;

ALTER SEQUENCE public.produtos_seq
    OWNER TO postgres;
	
-- Table: public.produtos

-- DROP TABLE public.produtos;

CREATE TABLE public.produtos
(
    id integer NOT NULL,
    nome text COLLATE pg_catalog."default" NOT NULL,
    i_unidades_medidas smallint NOT NULL,
    dt_criacao date,
    dth_modificacao timestamp without time zone,
    CONSTRAINT pk_produtos PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.produtos
    OWNER to postgres;	
-- Table: public.produtos_consumiveis

-- DROP TABLE public.produtos_consumiveis;

CREATE TABLE public.produtos_consumiveis
(
    id integer NOT NULL,
    i_produtos integer NOT NULL,
    qtd_produto integer NOT NULL,
    vl_produto numeric NOT NULL,
    dt_criacao date,
    dth_modificacao timestamp without time zone,
    CONSTRAINT pk_produtos_consumiveis PRIMARY KEY (id),
    CONSTRAINT fk_produtos_consumiveis_produtos FOREIGN KEY (i_produtos)
        REFERENCES public.produtos (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.produtos_consumiveis
    OWNER to postgres;
-- Index: fki_fk_produtos_consumiveis_produtos

-- DROP INDEX public.fki_fk_produtos_consumiveis_produtos;

CREATE INDEX fki_fk_produtos_consumiveis_produtos
    ON public.produtos_consumiveis USING btree
    (i_produtos ASC NULLS LAST)
    TABLESPACE pg_default;