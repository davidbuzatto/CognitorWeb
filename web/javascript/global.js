/**
 * Arquivo de configurações globais e namespaces
 *
 * @author    David Buzatto
 * @copyright (c) 2009, by David Buzatto
 * @date      07 de Setembro de 2009
 *
 */

// namespace da aplicação
Ext.ns( 'Application' );

// namespace da tela inicial
Ext.ns( 'TelaInicial' );

// namespace da tela de gerenciamento de usuários
Ext.ns( 'Usuarios' );

// namespace da tela de alteração dos dados pessoais
Ext.ns( 'DadosPessoais' );

// namespace da tela de gerenciamento de configurações globais
Ext.ns( 'Configs' );

// namespace do editor de metadados
Ext.ns( 'Metadados' );

// namespace para funções relacionadas a desenho
Ext.ns( 'Desenhos' );

// namespace do assistente de estruturação do conhecimento
Ext.ns( 'EstrCon' );

// namespace do assistente de inserção de analogias
Ext.ns( 'Analogias' );

// namespace das funções utilitárias
Ext.ns( 'Uteis' );

// namespace dos vtypes
Ext.ns( 'VTypes' );

// namespace do valores globais
Ext.ns( 'Global' );

// contexto
Application.contextPath = '/CognitorWeb'

// imagem invisível
Ext.BLANK_IMAGE_URL = Application.contextPath + '/javascript/ext-3.2.0/resources/images/default/s.gif';

// inicializa quicktips
Ext.QuickTips.init();

// constantes
Global.DESLIGAR = 0;
Global.LIGAR = 1;

// flags
Global.DEBUG = false;

// "objetos" CSS para colocar estilo no "componente" de abertura de material
Global.fundoBtnOver = {
    'background-image': 'url(' + Application.contextPath + '/imagens/mouseOverAbrir.png)',
    'background-repeat': 'repeat-x'
};

Global.fundoBtnOut = {
    'background': '#FFFFFF'
};

Global.fundoBtnClick = {
    'background-image': 'url(' + Application.contextPath + '/imagens/mouseClickAbrir.png)',
    'background-repeat': 'repeat-x'
};
