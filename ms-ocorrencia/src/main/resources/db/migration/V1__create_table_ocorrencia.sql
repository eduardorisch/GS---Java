CREATE TABLE tb_ocorrencia (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(50) NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    categoria VARCHAR(50)  NOT NULL,
    equipe_id BIGINT NULL,
    status TINYINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT chk_ocorrencia_status CHECK (status BETWEEN 0 AND 2)
);