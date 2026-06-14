--
-- PostgreSQL database dump
--

\restrict 8s9hkMaiXsm9N1Yspt52gHGDj5nFK38XbhQeFNBxDBbKTZ4uB6sekz9ooT2t5pb

-- Dumped from database version 18.0
-- Dumped by pg_dump version 18.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    idcliente integer NOT NULL,
    tipo_cliente character varying(50) NOT NULL,
    email character varying(255),
    telefone character varying(50),
    data_registo date DEFAULT CURRENT_DATE NOT NULL
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- Name: cliente_idcliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cliente_idcliente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cliente_idcliente_seq OWNER TO postgres;

--
-- Name: cliente_idcliente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cliente_idcliente_seq OWNED BY public.cliente.idcliente;


--
-- Name: clienteendereco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clienteendereco (
    idendereco integer NOT NULL,
    idcliente integer NOT NULL,
    endereco character varying(255) NOT NULL,
    cidade character varying(100),
    codigo_postal character varying(20),
    pais character varying(100),
    tipo_endereco character varying(50) NOT NULL
);


ALTER TABLE public.clienteendereco OWNER TO postgres;

--
-- Name: clienteendereco_idendereco_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clienteendereco_idendereco_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.clienteendereco_idendereco_seq OWNER TO postgres;

--
-- Name: clienteendereco_idendereco_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clienteendereco_idendereco_seq OWNED BY public.clienteendereco.idendereco;


--
-- Name: clienteparticular; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clienteparticular (
    idcliente integer NOT NULL,
    nome_completo character varying(255) NOT NULL,
    nif character varying(20)
);


ALTER TABLE public.clienteparticular OWNER TO postgres;

--
-- Name: clienterevendedor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clienterevendedor (
    idcliente integer NOT NULL,
    nome_empresa character varying(255) NOT NULL,
    vat_empresa character varying(50),
    contacto_principal character varying(255),
    departamento character varying(100),
    telefone_empresa character varying(50),
    nota_interna text
);


ALTER TABLE public.clienterevendedor OWNER TO postgres;

--
-- Name: fatura; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fatura (
    idfatura integer NOT NULL,
    idpedido integer NOT NULL,
    data_emissao date DEFAULT CURRENT_DATE NOT NULL,
    valor_total numeric(10,2) NOT NULL,
    estado character varying(50) NOT NULL
);


ALTER TABLE public.fatura OWNER TO postgres;

--
-- Name: fatura_idfatura_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.fatura_idfatura_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.fatura_idfatura_seq OWNER TO postgres;

--
-- Name: fatura_idfatura_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fatura_idfatura_seq OWNED BY public.fatura.idfatura;


--
-- Name: faturaitem; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.faturaitem (
    iditem integer NOT NULL,
    idfatura integer NOT NULL,
    idreceita integer NOT NULL,
    quantidade_litros numeric(10,2),
    preco_unitario numeric(10,2),
    subtotal numeric(10,2)
);


ALTER TABLE public.faturaitem OWNER TO postgres;

--
-- Name: faturaitem_iditem_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.faturaitem_iditem_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.faturaitem_iditem_seq OWNER TO postgres;

--
-- Name: faturaitem_iditem_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.faturaitem_iditem_seq OWNED BY public.faturaitem.iditem;


--
-- Name: ingrediente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ingrediente (
    idingrediente integer NOT NULL,
    nome character varying(255) NOT NULL,
    unidade character varying(50),
    stockatual numeric(10,2),
    stockminimo numeric(10,2)
);


ALTER TABLE public.ingrediente OWNER TO postgres;

--
-- Name: ingrediente_idingrediente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ingrediente_idingrediente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ingrediente_idingrediente_seq OWNER TO postgres;

--
-- Name: ingrediente_idingrediente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ingrediente_idingrediente_seq OWNED BY public.ingrediente.idingrediente;


--
-- Name: lote; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lote (
    idlote integer NOT NULL,
    idpedido integer,
    idreceita integer NOT NULL,
    litros numeric(10,2) NOT NULL,
    data_producao date,
    idveiculo integer,
    idrequest_producao integer
);


ALTER TABLE public.lote OWNER TO postgres;

--
-- Name: lote_idlote_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lote_idlote_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.lote_idlote_seq OWNER TO postgres;

--
-- Name: lote_idlote_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lote_idlote_seq OWNED BY public.lote.idlote;


--
-- Name: loteetapa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.loteetapa (
    idlote integer NOT NULL,
    idetapa integer NOT NULL,
    idusuario integer,
    data_inicio timestamp without time zone,
    data_fim timestamp without time zone
);


ALTER TABLE public.loteetapa OWNER TO postgres;

--
-- Name: pedido; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pedido (
    idpedido integer NOT NULL,
    idcliente integer NOT NULL,
    data_pedido date NOT NULL,
    estado character varying(50) DEFAULT 'Pendente'::character varying NOT NULL,
    data_estimada_conclusao date,
    total_litros numeric(10,2),
    total_grade integer
);


ALTER TABLE public.pedido OWNER TO postgres;

--
-- Name: pedido_idpedido_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pedido_idpedido_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pedido_idpedido_seq OWNER TO postgres;

--
-- Name: pedido_idpedido_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pedido_idpedido_seq OWNED BY public.pedido.idpedido;


--
-- Name: pedidoitem; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pedidoitem (
    iditem integer NOT NULL,
    idpedido integer NOT NULL,
    idreceita integer NOT NULL,
    quantidade_litros numeric(10,2),
    quantidade_grades integer
);


ALTER TABLE public.pedidoitem OWNER TO postgres;

--
-- Name: pedidoitem_iditem_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pedidoitem_iditem_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pedidoitem_iditem_seq OWNER TO postgres;

--
-- Name: pedidoitem_iditem_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pedidoitem_iditem_seq OWNED BY public.pedidoitem.iditem;


--
-- Name: producaoetapa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.producaoetapa (
    idetapa integer NOT NULL,
    nome character varying(100) NOT NULL
);


ALTER TABLE public.producaoetapa OWNER TO postgres;

--
-- Name: producaoetapa_idetapa_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.producaoetapa_idetapa_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.producaoetapa_idetapa_seq OWNER TO postgres;

--
-- Name: producaoetapa_idetapa_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.producaoetapa_idetapa_seq OWNED BY public.producaoetapa.idetapa;


--
-- Name: receita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.receita (
    idreceita integer NOT NULL,
    nome character varying(255) NOT NULL,
    descricao text
);


ALTER TABLE public.receita OWNER TO postgres;

--
-- Name: receita_idreceita_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.receita_idreceita_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.receita_idreceita_seq OWNER TO postgres;

--
-- Name: receita_idreceita_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.receita_idreceita_seq OWNED BY public.receita.idreceita;


--
-- Name: receitaingrediente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.receitaingrediente (
    id integer NOT NULL,
    idreceita integer NOT NULL,
    idingrediente integer NOT NULL,
    quantidade numeric(10,2)
);


ALTER TABLE public.receitaingrediente OWNER TO postgres;

--
-- Name: receitaingrediente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.receitaingrediente_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.receitaingrediente_id_seq OWNER TO postgres;

--
-- Name: receitaingrediente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.receitaingrediente_id_seq OWNED BY public.receitaingrediente.id;


--
-- Name: receitapreco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.receitapreco (
    idpreco integer NOT NULL,
    idreceita integer NOT NULL,
    precopor_litro numeric(10,2) CONSTRAINT receitapreco_preco_por_litro_not_null NOT NULL,
    datavigencia date DEFAULT CURRENT_DATE CONSTRAINT receitapreco_data_vigencia_not_null NOT NULL
);


ALTER TABLE public.receitapreco OWNER TO postgres;

--
-- Name: receitapreco_idpreco_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.receitapreco_idpreco_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.receitapreco_idpreco_seq OWNER TO postgres;

--
-- Name: receitapreco_idpreco_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.receitapreco_idpreco_seq OWNED BY public.receitapreco.idpreco;


--
-- Name: requestproducao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.requestproducao (
    idrequest_producao integer NOT NULL,
    idusuario integer NOT NULL,
    estado character varying(50) DEFAULT 'Pendente'::character varying NOT NULL,
    data_criacao timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    data_conclusao timestamp without time zone
);


ALTER TABLE public.requestproducao OWNER TO postgres;

--
-- Name: requestproducao_idrequest_producao_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.requestproducao_idrequest_producao_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.requestproducao_idrequest_producao_seq OWNER TO postgres;

--
-- Name: requestproducao_idrequest_producao_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.requestproducao_idrequest_producao_seq OWNED BY public.requestproducao.idrequest_producao;


--
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    idrole integer NOT NULL,
    nome character varying(100) NOT NULL,
    descricao text
);


ALTER TABLE public.role OWNER TO postgres;

--
-- Name: role_idrole_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.role_idrole_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.role_idrole_seq OWNER TO postgres;

--
-- Name: role_idrole_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.role_idrole_seq OWNED BY public.role.idrole;


--
-- Name: stock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stock (
    idreceita integer NOT NULL,
    litros_disponiveis numeric(10,2) NOT NULL,
    stock_minimo numeric(10,2)
);


ALTER TABLE public.stock OWNER TO postgres;

--
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    idusuario integer NOT NULL,
    nome character varying(100) NOT NULL,
    senha character varying(100) NOT NULL,
    idrole integer NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- Name: usuario_idusuario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuario_idusuario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.usuario_idusuario_seq OWNER TO postgres;

--
-- Name: usuario_idusuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuario_idusuario_seq OWNED BY public.usuario.idusuario;


--
-- Name: veiculo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.veiculo (
    idveiculo integer NOT NULL,
    matricula character varying(20) NOT NULL,
    marca character varying(50) NOT NULL,
    cor character varying(30),
    nome character varying(100) NOT NULL,
    capacidade numeric(10,2) NOT NULL,
    ocupacao_atual numeric(10,2) DEFAULT 0,
    tipo character varying(50) NOT NULL
);


ALTER TABLE public.veiculo OWNER TO postgres;

--
-- Name: veiculo_idveiculo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.veiculo_idveiculo_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.veiculo_idveiculo_seq OWNER TO postgres;

--
-- Name: veiculo_idveiculo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.veiculo_idveiculo_seq OWNED BY public.veiculo.idveiculo;


--
-- Name: cliente idcliente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente ALTER COLUMN idcliente SET DEFAULT nextval('public.cliente_idcliente_seq'::regclass);


--
-- Name: clienteendereco idendereco; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clienteendereco ALTER COLUMN idendereco SET DEFAULT nextval('public.clienteendereco_idendereco_seq'::regclass);


--
-- Name: fatura idfatura; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fatura ALTER COLUMN idfatura SET DEFAULT nextval('public.fatura_idfatura_seq'::regclass);


--
-- Name: faturaitem iditem; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.faturaitem ALTER COLUMN iditem SET DEFAULT nextval('public.faturaitem_iditem_seq'::regclass);


--
-- Name: ingrediente idingrediente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingrediente ALTER COLUMN idingrediente SET DEFAULT nextval('public.ingrediente_idingrediente_seq'::regclass);


--
-- Name: lote idlote; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lote ALTER COLUMN idlote SET DEFAULT nextval('public.lote_idlote_seq'::regclass);


--
-- Name: pedido idpedido; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido ALTER COLUMN idpedido SET DEFAULT nextval('public.pedido_idpedido_seq'::regclass);


--
-- Name: pedidoitem iditem; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidoitem ALTER COLUMN iditem SET DEFAULT nextval('public.pedidoitem_iditem_seq'::regclass);


--
-- Name: producaoetapa idetapa; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producaoetapa ALTER COLUMN idetapa SET DEFAULT nextval('public.producaoetapa_idetapa_seq'::regclass);


--
-- Name: receita idreceita; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receita ALTER COLUMN idreceita SET DEFAULT nextval('public.receita_idreceita_seq'::regclass);


--
-- Name: receitaingrediente id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receitaingrediente ALTER COLUMN id SET DEFAULT nextval('public.receitaingrediente_id_seq'::regclass);


--
-- Name: receitapreco idpreco; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receitapreco ALTER COLUMN idpreco SET DEFAULT nextval('public.receitapreco_idpreco_seq'::regclass);


--
-- Name: requestproducao idrequest_producao; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.requestproducao ALTER COLUMN idrequest_producao SET DEFAULT nextval('public.requestproducao_idrequest_producao_seq'::regclass);


--
-- Name: role idrole; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role ALTER COLUMN idrole SET DEFAULT nextval('public.role_idrole_seq'::regclass);


--
-- Name: usuario idusuario; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario ALTER COLUMN idusuario SET DEFAULT nextval('public.usuario_idusuario_seq'::regclass);


--
-- Name: veiculo idveiculo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.veiculo ALTER COLUMN idveiculo SET DEFAULT nextval('public.veiculo_idveiculo_seq'::regclass);


--
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cliente (idcliente, tipo_cliente, email, telefone, data_registo) FROM stdin;
1	Particular	miguel.silva@email.pt	910000001	2023-03-15
2	Particular	rita.fernandes@email.pt	920000002	2023-07-22
3	Particular	pedro.costa@email.pt	930000003	2024-01-10
4	Revendedor	geral@beerworld.pt	210000004	2022-11-05
5	Revendedor	comercial@tapadolobo.pt	220000005	2023-05-18
6	Revendedor	encomendas@beershop360.pt	230000006	2024-02-28
\.


--
-- Data for Name: clienteendereco; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clienteendereco (idendereco, idcliente, endereco, cidade, codigo_postal, pais, tipo_endereco) FROM stdin;
\.


--
-- Data for Name: clienteparticular; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clienteparticular (idcliente, nome_completo, nif) FROM stdin;
1	Miguel António Silva	123456789
2	Rita Fernandes Lopes	234567890
3	Pedro Alexandre Costa	345678901
\.


--
-- Data for Name: clienterevendedor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clienterevendedor (idcliente, nome_empresa, vat_empresa, contacto_principal, departamento, telefone_empresa, nota_interna) FROM stdin;
4	Beer World Lda	PT501234567	Hugo Mendes	Compras	210000004	Cliente premium, desconto 5%
5	Tapado do Lobo Lda	PT502345678	Catarina Brito	Encomendas	220000005	Pagamento a 30 dias
6	BeerShop 360 Lda	PT503456789	Rui Assunção	Logística	230000006	Entrega apenas às 3as feiras
\.


--
-- Data for Name: fatura; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fatura (idfatura, idpedido, data_emissao, valor_total, estado) FROM stdin;
1	1	2025-01-22	542.00	PAGA
2	2	2025-02-15	97.50	PAGA
3	3	2025-04-08	383.00	EMITIDA
\.


--
-- Data for Name: faturaitem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.faturaitem (iditem, idfatura, idreceita, quantidade_litros, preco_unitario, subtotal) FROM stdin;
1	1	1	120.00	1.95	234.00
2	1	2	80.00	2.70	216.00
3	2	1	50.00	1.95	97.50
4	3	3	100.00	2.20	220.00
5	3	5	50.00	2.30	115.00
\.


--
-- Data for Name: ingrediente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ingrediente (idingrediente, nome, unidade, stockatual, stockminimo) FROM stdin;
1	Malte Pilsner	kg	500.00	100.00
2	Malte Munich	kg	200.00	50.00
3	Malte Caramelo	kg	150.00	30.00
4	Lúpulo Hallertau	kg	40.00	10.00
5	Lúpulo Saaz	kg	35.00	10.00
6	Levedura Lager S23	un	20.00	5.00
7	Levedura Ale US-05	un	15.00	5.00
8	Água Filtrada	L	5000.00	1000.00
9	Açúcar de Cana	kg	80.00	20.00
10	Corante Caramelo	ml	300.00	50.00
\.


--
-- Data for Name: lote; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lote (idlote, idpedido, idreceita, litros, data_producao, idveiculo, idrequest_producao) FROM stdin;
1	1	1	120.00	2025-01-18	3	1
2	1	2	80.00	2025-01-19	3	1
3	2	1	50.00	2025-02-20	1	2
4	3	3	100.00	2025-04-05	2	3
5	3	5	50.00	2025-04-06	2	3
\.


--
-- Data for Name: loteetapa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.loteetapa (idlote, idetapa, idusuario, data_inicio, data_fim) FROM stdin;
\.


--
-- Data for Name: pedido; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pedido (idpedido, idcliente, data_pedido, estado, data_estimada_conclusao, total_litros, total_grade) FROM stdin;
1	4	2025-01-06	ENTREGUE	2025-01-22	200.00	17
2	1	2025-02-01	ENTREGUE	2025-02-15	50.00	5
3	5	2025-03-10	EM_PRODUCAO	2025-04-15	150.00	13
4	6	2025-04-01	PENDENTE	2025-05-01	300.00	25
5	2	2025-04-09	PENDENTE	2025-04-30	24.00	2
6	4	2026-04-25	Pendente	2026-04-24	0.00	0
\.


--
-- Data for Name: pedidoitem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pedidoitem (iditem, idpedido, idreceita, quantidade_litros, quantidade_grades) FROM stdin;
1	1	1	120.00	10
2	1	2	80.00	7
3	2	1	50.00	5
4	3	3	100.00	9
5	3	5	50.00	4
6	4	1	150.00	13
7	4	4	150.00	12
8	5	2	24.00	2
\.


--
-- Data for Name: producaoetapa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.producaoetapa (idetapa, nome) FROM stdin;
\.


--
-- Data for Name: receita; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.receita (idreceita, nome, descricao) FROM stdin;
1	Lager Clássica	Cerveja lager dourada, leve e refrescante, fermentação baixa
2	IPA Artesanal	India Pale Ale com forte aroma e amargor de lúpulo
3	Stout Escura	Cerveja escura com notas de café e chocolate
4	Weizen de Trigo	Cerveja de trigo alemã, turva, com notas de banana e cravo
5	Amber Ale	Ale âmbar equilibrada, maltada com leve toque lupulado
\.


--
-- Data for Name: receitaingrediente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.receitaingrediente (id, idreceita, idingrediente, quantidade) FROM stdin;
\.


--
-- Data for Name: receitapreco; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.receitapreco (idpreco, idreceita, precopor_litro, datavigencia) FROM stdin;
1	1	1.80	2024-01-01
2	1	1.95	2025-01-01
3	2	2.50	2024-01-01
4	2	2.70	2025-03-01
5	3	2.20	2024-06-01
6	4	2.10	2024-01-01
7	5	2.30	2025-01-01
\.


--
-- Data for Name: requestproducao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.requestproducao (idrequest_producao, idusuario, estado, data_criacao, data_conclusao) FROM stdin;
1	2	CONCLUIDO	2025-01-05 08:00:00	2025-01-20 17:00:00
2	2	CONCLUIDO	2025-02-10 09:00:00	2025-02-25 16:30:00
3	5	EM_CURSO	2025-04-01 08:30:00	\N
4	5	PENDENTE	2025-04-10 10:00:00	\N
\.


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.role (idrole, nome, descricao) FROM stdin;
1	ADMIN	Administrador do sistema com acesso total
2	PRODUCAO	Responsável pela produção e lotes
3	VENDAS	Gestão de pedidos e clientes
4	ENTREGA	Motorista / gestão de veículos
\.


--
-- Data for Name: stock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.stock (idreceita, litros_disponiveis, stock_minimo) FROM stdin;
\.


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (idusuario, nome, senha, idrole) FROM stdin;
1	admin	admin123	1
2	joao.mestre	brew@2024	2
3	ana.vendas	vendas#01	3
4	carlos.driver	estrada99	4
5	sofia.prod	lager2025	2
\.


--
-- Data for Name: veiculo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.veiculo (idveiculo, matricula, marca, cor, nome, capacidade, ocupacao_atual, tipo) FROM stdin;
1	AA-00-BB	Mercedes	Branco	Sprinter 1	1000.00	0.00	Furgão
2	CC-11-DD	Ford	Azul	Transit Porto	800.00	0.00	Furgão
3	EE-22-FF	Iveco	Cinzento	Iveco Norte	2000.00	500.00	Camião
4	GG-33-HH	Renault	Branco	Master Lisboa	900.00	0.00	Furgão
\.


--
-- Name: cliente_idcliente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cliente_idcliente_seq', 1, false);


--
-- Name: clienteendereco_idendereco_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.clienteendereco_idendereco_seq', 1, false);


--
-- Name: fatura_idfatura_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fatura_idfatura_seq', 1, false);


--
-- Name: faturaitem_iditem_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.faturaitem_iditem_seq', 1, false);


--
-- Name: ingrediente_idingrediente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ingrediente_idingrediente_seq', 1, false);


--
-- Name: lote_idlote_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lote_idlote_seq', 1, false);


--
-- Name: pedido_idpedido_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pedido_idpedido_seq', 6, true);


--
-- Name: pedidoitem_iditem_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pedidoitem_iditem_seq', 1, false);


--
-- Name: producaoetapa_idetapa_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.producaoetapa_idetapa_seq', 1, false);


--
-- Name: receita_idreceita_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.receita_idreceita_seq', 1, false);


--
-- Name: receitaingrediente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.receitaingrediente_id_seq', 1, false);


--
-- Name: receitapreco_idpreco_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.receitapreco_idpreco_seq', 1, false);


--
-- Name: requestproducao_idrequest_producao_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.requestproducao_idrequest_producao_seq', 1, false);


--
-- Name: role_idrole_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.role_idrole_seq', 1, false);


--
-- Name: usuario_idusuario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_idusuario_seq', 1, false);


--
-- Name: veiculo_idveiculo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.veiculo_idveiculo_seq', 1, false);


--
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (idcliente);


--
-- Name: clienteendereco clienteendereco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clienteendereco
    ADD CONSTRAINT clienteendereco_pkey PRIMARY KEY (idendereco);


--
-- Name: clienteparticular clienteparticular_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clienteparticular
    ADD CONSTRAINT clienteparticular_pkey PRIMARY KEY (idcliente);


--
-- Name: clienterevendedor clienterevendedor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clienterevendedor
    ADD CONSTRAINT clienterevendedor_pkey PRIMARY KEY (idcliente);


--
-- Name: fatura fatura_idpedido_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fatura
    ADD CONSTRAINT fatura_idpedido_key UNIQUE (idpedido);


--
-- Name: fatura fatura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fatura
    ADD CONSTRAINT fatura_pkey PRIMARY KEY (idfatura);


--
-- Name: faturaitem faturaitem_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.faturaitem
    ADD CONSTRAINT faturaitem_pkey PRIMARY KEY (iditem);


--
-- Name: ingrediente ingrediente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingrediente
    ADD CONSTRAINT ingrediente_pkey PRIMARY KEY (idingrediente);


--
-- Name: lote lote_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lote
    ADD CONSTRAINT lote_pkey PRIMARY KEY (idlote);


--
-- Name: loteetapa loteetapa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loteetapa
    ADD CONSTRAINT loteetapa_pkey PRIMARY KEY (idlote, idetapa);


--
-- Name: pedido pedido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_pkey PRIMARY KEY (idpedido);


--
-- Name: pedidoitem pedidoitem_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidoitem
    ADD CONSTRAINT pedidoitem_pkey PRIMARY KEY (iditem);


--
-- Name: producaoetapa producaoetapa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producaoetapa
    ADD CONSTRAINT producaoetapa_pkey PRIMARY KEY (idetapa);


--
-- Name: receita receita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receita
    ADD CONSTRAINT receita_pkey PRIMARY KEY (idreceita);


--
-- Name: receitaingrediente receitaingrediente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receitaingrediente
    ADD CONSTRAINT receitaingrediente_pkey PRIMARY KEY (id);


--
-- Name: receitapreco receitapreco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receitapreco
    ADD CONSTRAINT receitapreco_pkey PRIMARY KEY (idpreco);


--
-- Name: requestproducao requestproducao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.requestproducao
    ADD CONSTRAINT requestproducao_pkey PRIMARY KEY (idrequest_producao);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (idrole);


--
-- Name: stock stock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_pkey PRIMARY KEY (idreceita);


--
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (idusuario);


--
-- Name: veiculo veiculo_matricula_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.veiculo
    ADD CONSTRAINT veiculo_matricula_key UNIQUE (matricula);


--
-- Name: veiculo veiculo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.veiculo
    ADD CONSTRAINT veiculo_pkey PRIMARY KEY (idveiculo);


--
-- Name: clienteendereco clienteendereco_idcliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clienteendereco
    ADD CONSTRAINT clienteendereco_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES public.cliente(idcliente);


--
-- Name: clienteparticular clienteparticular_idcliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clienteparticular
    ADD CONSTRAINT clienteparticular_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES public.cliente(idcliente);


--
-- Name: clienterevendedor clienterevendedor_idcliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clienterevendedor
    ADD CONSTRAINT clienterevendedor_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES public.cliente(idcliente);


--
-- Name: fatura fatura_idpedido_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fatura
    ADD CONSTRAINT fatura_idpedido_fkey FOREIGN KEY (idpedido) REFERENCES public.pedido(idpedido);


--
-- Name: faturaitem faturaitem_idfatura_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.faturaitem
    ADD CONSTRAINT faturaitem_idfatura_fkey FOREIGN KEY (idfatura) REFERENCES public.fatura(idfatura);


--
-- Name: faturaitem faturaitem_idreceita_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.faturaitem
    ADD CONSTRAINT faturaitem_idreceita_fkey FOREIGN KEY (idreceita) REFERENCES public.receita(idreceita);


--
-- Name: lote lote_idpedido_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lote
    ADD CONSTRAINT lote_idpedido_fkey FOREIGN KEY (idpedido) REFERENCES public.pedido(idpedido);


--
-- Name: lote lote_idreceita_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lote
    ADD CONSTRAINT lote_idreceita_fkey FOREIGN KEY (idreceita) REFERENCES public.receita(idreceita);


--
-- Name: lote lote_idrequest_producao_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lote
    ADD CONSTRAINT lote_idrequest_producao_fkey FOREIGN KEY (idrequest_producao) REFERENCES public.requestproducao(idrequest_producao);


--
-- Name: lote lote_idveiculo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lote
    ADD CONSTRAINT lote_idveiculo_fkey FOREIGN KEY (idveiculo) REFERENCES public.veiculo(idveiculo);


--
-- Name: loteetapa loteetapa_idetapa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loteetapa
    ADD CONSTRAINT loteetapa_idetapa_fkey FOREIGN KEY (idetapa) REFERENCES public.producaoetapa(idetapa);


--
-- Name: loteetapa loteetapa_idlote_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loteetapa
    ADD CONSTRAINT loteetapa_idlote_fkey FOREIGN KEY (idlote) REFERENCES public.lote(idlote);


--
-- Name: loteetapa loteetapa_idusuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loteetapa
    ADD CONSTRAINT loteetapa_idusuario_fkey FOREIGN KEY (idusuario) REFERENCES public.usuario(idusuario);


--
-- Name: pedido pedido_idcliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES public.cliente(idcliente);


--
-- Name: pedidoitem pedidoitem_idpedido_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidoitem
    ADD CONSTRAINT pedidoitem_idpedido_fkey FOREIGN KEY (idpedido) REFERENCES public.pedido(idpedido);


--
-- Name: pedidoitem pedidoitem_idreceita_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidoitem
    ADD CONSTRAINT pedidoitem_idreceita_fkey FOREIGN KEY (idreceita) REFERENCES public.receita(idreceita);


--
-- Name: receitaingrediente receitaingrediente_idingrediente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receitaingrediente
    ADD CONSTRAINT receitaingrediente_idingrediente_fkey FOREIGN KEY (idingrediente) REFERENCES public.ingrediente(idingrediente);


--
-- Name: receitaingrediente receitaingrediente_idreceita_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receitaingrediente
    ADD CONSTRAINT receitaingrediente_idreceita_fkey FOREIGN KEY (idreceita) REFERENCES public.receita(idreceita);


--
-- Name: receitapreco receitapreco_idreceita_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receitapreco
    ADD CONSTRAINT receitapreco_idreceita_fkey FOREIGN KEY (idreceita) REFERENCES public.receita(idreceita);


--
-- Name: requestproducao requestproducao_idusuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.requestproducao
    ADD CONSTRAINT requestproducao_idusuario_fkey FOREIGN KEY (idusuario) REFERENCES public.usuario(idusuario);


--
-- Name: stock stock_idreceita_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_idreceita_fkey FOREIGN KEY (idreceita) REFERENCES public.receita(idreceita);


--
-- Name: usuario usuario_idrole_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_idrole_fkey FOREIGN KEY (idrole) REFERENCES public.role(idrole);


--
-- PostgreSQL database dump complete
--

\unrestrict 8s9hkMaiXsm9N1Yspt52gHGDj5nFK38XbhQeFNBxDBbKTZ4uB6sekz9ooT2t5pb

