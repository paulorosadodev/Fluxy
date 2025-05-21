CREATE DATABASE fluxy_db;
USE fluxy_db;

CREATE TABLE pessoa (
                        id_pessoa INT PRIMARY KEY AUTO_INCREMENT,
                        rua VARCHAR(100),
                        numero VARCHAR(100),
                        bairro VARCHAR(100),
                        cidade VARCHAR(100),
                        cep VARCHAR(20)
);

CREATE TABLE cliente (
                         id_cliente INT PRIMARY KEY AUTO_INCREMENT,
                         FOREIGN KEY (id_cliente) REFERENCES pessoa(id_pessoa)
);

CREATE TABLE categoria (
                           codigo VARCHAR(50) PRIMARY KEY UNIQUE,
                           nome VARCHAR(100)
);

CREATE TABLE produto (
                         id_produto INT PRIMARY KEY AUTO_INCREMENT,
                         codigo_categoria VARCHAR(50),
                         qtd_estoque INT,
                         cod_ea VARCHAR(13) UNIQUE,
                         preco DECIMAL(10,2),
                         nome VARCHAR(100),
                         FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo)
);

CREATE TABLE fornecedor (
                            id_fornecedor INT PRIMARY KEY,
                            cnpj VARCHAR(20) UNIQUE,
                            nome VARCHAR(100),
                            FOREIGN KEY (id_fornecedor) REFERENCES pessoa(id_pessoa)
);

CREATE TABLE funcionario (
                             id_funcionario INT PRIMARY KEY,
                             matricula VARCHAR(50) UNIQUE,
                             nome VARCHAR(100),
                             cpf VARCHAR(20) UNIQUE,
                             salario DECIMAL(10,2),
                             setor VARCHAR(100),
                             turno VARCHAR(50),
                             funcao VARCHAR(50),
                             id_supervisor INT,
                             FOREIGN KEY (id_funcionario) REFERENCES pessoa(id_pessoa),
                             FOREIGN KEY (id_supervisor) REFERENCES funcionario(id_funcionario)
);

CREATE TABLE historico_preco_produto (
                                         id_historico_preco_produto INT AUTO_INCREMENT PRIMARY KEY,
                                         codigo_produto INT,
                                         preco DECIMAL(10,2),
                                         data DATETIME,
                                         FOREIGN KEY (codigo_produto) REFERENCES produto(id_produto)
);

CREATE TABLE fisico (
                        fk_cliente_id INT PRIMARY KEY,
                        nome VARCHAR(100),
                        cpf VARCHAR(20) UNIQUE,
                        FOREIGN KEY (fk_cliente_id) REFERENCES cliente(id_cliente)
);

CREATE TABLE juridico (
                          fk_cliente_id INT PRIMARY KEY,
                          inscr_estadual VARCHAR(20) UNIQUE,
                          cnpj VARCHAR(20) UNIQUE,
                          razao_social VARCHAR(100),
                          FOREIGN KEY (fk_cliente_id) REFERENCES cliente(id_cliente)
);

CREATE TABLE telefone (
                          numero VARCHAR(20),
                          id_telefone INT,
                          PRIMARY KEY (numero),
                          FOREIGN KEY (id_telefone) REFERENCES pessoa(id_pessoa)
);

CREATE TABLE usuario (
                         id_usuario INT PRIMARY KEY AUTO_INCREMENT,
                         nome VARCHAR(100),
                         senha VARCHAR(100),
                         cargo VARCHAR(100)
);

CREATE TABLE compra (
                        numero INT AUTO_INCREMENT,
                        data DATE,
                        hora TIME,
                        parcelas INT,
                        tipo VARCHAR(50),
                        qtd_produto INT,
                        fk_produto_id INT,
                        fk_cliente_id INT,
                        fk_operacional_id_funcionario INT,
                        FOREIGN KEY (fk_produto_id) REFERENCES produto(id_produto),
                        FOREIGN KEY (fk_cliente_id) REFERENCES cliente(id_cliente),
                        FOREIGN KEY (fk_operacional_id_funcionario) REFERENCES funcionario(id_funcionario),
                        PRIMARY KEY (numero, fk_produto_id, fk_cliente_id, fk_operacional_id_funcionario)
);

CREATE TABLE entrega (
                         id_entrega INT AUTO_INCREMENT,
                         fk_fornecedor_id INT,
                         fk_produto_id INT,
                         qnt_fornecida INT,
                         valor_pago DECIMAL(10,2),
                         data_reposicao DATE,
                         FOREIGN KEY (fk_fornecedor_id) REFERENCES fornecedor(id_fornecedor),
                         FOREIGN KEY (fk_produto_id) REFERENCES produto(id_produto),
                         PRIMARY KEY (id_entrega,fk_fornecedor_id, fk_produto_id)
);

DELIMITER $$

CREATE FUNCTION preco_diferente(preco1 DECIMAL(10,2), preco2 DECIMAL(10,2))
    RETURNS BOOLEAN
    DETERMINISTIC
BEGIN
    RETURN ROUND(preco1, 2) <> ROUND(preco2, 2);
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER trg_historico_preco_produto
    AFTER INSERT ON produto
    FOR EACH ROW
BEGIN
    INSERT INTO historico_preco_produto (codigo_produto, preco, data)
    VALUES (NEW.id_produto, NEW.preco, NOW());
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER trg_update_preco_produto
    AFTER UPDATE ON produto
    FOR EACH ROW
BEGIN
    IF preco_diferente(NEW.preco, OLD.preco) THEN
        INSERT INTO historico_preco_produto (codigo_produto, preco, data)
        VALUES (NEW.id_produto, NEW.preco, NOW());
    END IF;
END $$

DELIMITER ;






