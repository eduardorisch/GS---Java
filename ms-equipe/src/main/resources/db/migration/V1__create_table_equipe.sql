CREATE TABLE tb_equipe (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    especialidade VARCHAR(100) NOT NULL,
    ocorrencia_id BIGINT NULL,
    quantidade_integrantes INT NOT NULL,
    status TINYINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT chk_equipe_status CHECK (status BETWEEN 0 AND 2)
);