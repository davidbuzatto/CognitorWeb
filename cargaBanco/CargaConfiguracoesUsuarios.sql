INSERT INTO Configuracao (propriedade, valor) VALUES
    ('versao', '1.0'),
    ('debug', 'false'),
    ('producao', 'true'),
    ('tamanhoMaximoArquivo', '524288'),
    ('emailCognitor', 'cognitor@dc.ufscar.br'),
    ('nomeCognitor', 'Cognitor'),
    ('emailTestador', 'testador@dc.ufscar.br'),
    ('servidorEmail', 'mail.dc.ufscar.br'),
    ('repositorioArquivos', 'C:/RepositorioCognitor/');

INSERT INTO Usuario (complemento, dataNascimento, email, escolaridade, nomeMeio, numero, ocupacao, primeiroNome, rua, senha, sexo, tipo, ultimoNome, cidade_id, estado_id, pais_id) VALUES
    ('', '2010-01-08', 'cognitor@dc.ufscar.br', 'MSC', '', '', 'Administrador', 'Cognitor', '', 'ra`2vSRRs^x_@4', 'M', 'A', 'LIA', NULL, NULL, 31);
    ('', '2009-10-02', 'testador@dc.ufscar.br', 'EFC', '', '', 'Testador', 'Testador', '', '|r]l{_l;i\\wj@4', 'M', 'T', 'Testador', NULL, NULL, 31);

COMMIT;