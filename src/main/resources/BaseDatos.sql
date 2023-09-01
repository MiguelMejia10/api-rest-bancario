CREATE TABLE personas
(
    cliente_id     SERIAL PRIMARY KEY,
    nombre         VARCHAR(255) NOT NULL,
    genero         VARCHAR(20)  NOT NULL,
    edad           INTEGER      NOT NULL,
    identificacion VARCHAR(255) NOT NULL UNIQUE,
    direccion      VARCHAR(255) NOT NULL,
    telefono       VARCHAR(255) NOT NULL
);

CREATE TABLE clientes
(
    password   VARCHAR(255) NOT NULL,
    estado     BOOLEAN,
    cliente_id INTEGER      NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES personas (cliente_id),
    PRIMARY KEY (cliente_id)
);

CREATE TABLE cuentas
(
    id               SERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(255)     NOT NULL UNIQUE,
    tipo_cuenta   VARCHAR(255),
    saldo_inicial    DOUBLE PRECISION NOT NULL,
    estado           BOOLEAN,
    cliente_id       INTEGER          NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes (cliente_id)
);

CREATE TABLE movimientos
(
    id                 SERIAL PRIMARY KEY,
    fecha              DATE,
    tipo_movimiento VARCHAR(255),
    valor              DOUBLE PRECISION NOT NULL,
    saldo              DOUBLE PRECISION NOT NULL,
    numero_cuenta   VARCHAR(255)     NOT NULL,
    FOREIGN KEY (numero_de_cuenta) REFERENCES cuentas (numero_de_cuenta)
);