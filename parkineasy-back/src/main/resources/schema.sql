CREATE TABLE IF NOT EXISTS  funcionario (
  id bigint NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL,
  nome varchar(255) NOT NULL,
  senha varchar(255) NOT NULL,
  usuario varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS  vaga (
  codigo varchar(255) NOT NULL,
  ocupada tinyint(1) NOT NULL,
  tipo_vaga int NOT NULL,
  PRIMARY KEY (codigo)
);
    
CREATE TABLE IF NOT EXISTS  ticket (
  id bigint NOT NULL AUTO_INCREMENT,
  codigo_vaga varchar(255) NOT NULL,
  data_hora TIMESTAMP NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT ticket_ibfk_1 FOREIGN KEY (codigo_vaga) REFERENCES vaga (codigo)
);
    
CREATE TABLE IF NOT EXISTS pagamento (
  id bigint NOT NULL AUTO_INCREMENT,
  data_hora date NOT NULL,
  valor decimal(8,2) NOT NULL,
  metodo_pagamento int NOT NULL,
  id_ticket bigint NOT NULL,
  id_vaga varchar(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT pagamento_ibfk_1 FOREIGN KEY (id_ticket) REFERENCES ticket (id),
  CONSTRAINT pagamento_ibfk_2 FOREIGN KEY (id_vaga) REFERENCES vaga (codigo)
);
