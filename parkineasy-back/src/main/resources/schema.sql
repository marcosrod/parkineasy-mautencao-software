CREATE TABLE IF NOT EXISTS funcionario
(
    id
    BIGINT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    email
    VARCHAR
(
    255
) NOT NULL,
    nome VARCHAR
(
    255
) NOT NULL,
    senha VARCHAR
(
    255
) NOT NULL,
    usuario VARCHAR
(
    255
) NOT NULL
    );
CREATE TABLE IF NOT EXISTS vaga
(
    codigo VARCHAR
(
    255
) PRIMARY KEY,
    ocupada BOOLEAN NOT NULL,
    tipo_vaga INT NOT NULL
    );
CREATE TABLE IF NOT EXISTS ticket
(
    id
    BIGINT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    codigo_vaga
    VARCHAR
(
    255
) NOT NULL,
    data_hora DATE NOT NULL
    )
