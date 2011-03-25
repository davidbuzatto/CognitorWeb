CREATE PROCEDURE consultaMateriais(
        IN titulo VARCHAR(200),
        IN descricao VARCHAR(200),
        IN palavraChave VARCHAR(200),
        IN inicio INTEGER(11),
        IN fim INTEGER(11)
    )
    NOT DETERMINISTIC
    READS SQL DATA
    SQL SECURITY DEFINER
    COMMENT 'Procedure para obtenção de materiais.\r\nAutor: David Buzatto'
BEGIN

    /*
     * Para usar o limit em uma stored procedure do mysql
     * é necessário criar um statement e então inserir os valores
     */
    SET @titulo := titulo;
    SET @descricao := descricao;
    SET @palavraChave := palavraChave;
    SET @inicio := inicio;
    SET @fim := fim;

    SET @query := CONCAT('SELECT ',
        'm.id, ',
        'm.compartilhado, ',
        'm.dataCriacao, ',
        'm.dataAtualizacao, ',
        'm.titulo, ',
        'm.conhecimentoEstruturado, ',
        'm.layout, ',
        'm.metadata_id, ',
        'm.usuario_id ',
    'FROM ',
        'Material m, ',
        'Usuario u, ',
        'Metadata mt, ',
        'Lom l, ',
        'General g, ',
        'GeneralDescription gd, ',
        'GeneralDescription_LangString gdl, ',
        'GeneralKeyword gk, ',
        'GeneralKeyword_LangString gkl, ',
        'LangString lsd, ',
        'LangString lsk ',
    'WHERE ',
        'm.usuario_id = u.id AND ',
        'm.metadata_id = mt.id AND ',
        'mt.lom_id = l.id AND ',
        'l.general_id = g.id AND ',
        'gd.general_id = g.id AND ',
        'gdl.GeneralDescription_id = gd.id AND ',
        'gk.general_id = g.id AND ',
        'gkl.GeneralKeyword_id = gk.id AND ',
        'gdl.strings_id = lsd.id AND ',
        'gkl.strings_id = lsk.id AND ',
        'm.compartilhado = True AND ',
        'm.titulo LIKE ? AND ',
        'lsd.lsvalue LIKE ? AND ',
        'lsk.lsvalue LIKE ? ',
        'ORDER BY m.titulo, m.dataAtualizacao ',
    'LIMIT ?, ?');

    PREPARE stmt FROM @query;
    EXECUTE stmt USING @titulo, @descricao, @palavraChave, @inicio, @fim;

END;