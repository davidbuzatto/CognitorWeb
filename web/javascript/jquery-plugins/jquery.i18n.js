/**
 * Extensão da jQuery para carga de i18n.
 *
 * @author    David Buzatto
 * @copyright (c) 2009, by David Buzatto
 * @date      10 de Outubro de 2009
 *
 */

(function($){

    // dados i18n
    var i18nData = undefined;

    /*
     * Carrega os dados de um arquivo .properties (caso resourceExt não seja definido)
     * em uma variável no formato de um array associativo. Faz requisição
     * síncrona para obtenção dos valores.
     */
    $.loadI18N = function( configs ) {

        var resourceBundle = configs.resourceBundle;
        var resourceExt = '.properties'
        
        if ( configs.resourceExt ) {
            resourceExt = configs.resourceExt;
        }

        // resolve idioma
        var idioma = navigator.language || navigator.userLanguage;
        idioma = idioma.substring( 0, 2 );

        if ( idioma != 'pt' )
            idioma = 'en';

        $.ajax({
            url: resourceBundle + '_' + idioma + resourceExt,
            async: false,
            dataType: 'text',
            cache: false,
            type: 'post',
            success: function( data, textStatus ) {
                // a partir do texto, cria um mapa
                var linhas = data.split('\n');
                var obj = [];
                for ( var i = 0; i < linhas.length; i++ ) {
                    var chave = linhas[i].substring( 0, linhas[i].indexOf( '=' ) );
                    var valor = linhas[i].substr( linhas[i].indexOf( '=' ) + 1 );
                    obj[chave] = valor;
                }
                i18nData = obj;
            }
        });

    };

    /*
     * Obtém um valor do arquivo de propriedades que foi carregado.
     */
    $.getMsg = function( chave ) {
        if ( i18nData )
            return i18nData[chave] ? i18nData[chave] : chave + ' undefined';
        else
            return 'undefined';
    }
    
})(jQuery);