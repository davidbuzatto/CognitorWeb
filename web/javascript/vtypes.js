/**
 * Arquivo de scripts para vtypes personalizados.
 *
 * @author    David Buzatto
 * @copyright (c) 2009, by David Buzatto
 * @date      23 de Setembro de 2009
 *
 */

VTypes.initComponents = function() {

    Ext.apply( Ext.form.VTypes, {
        
        /*
         * Validador para dois campos de senha
         */
        password: function(val, field) {
            if (field.initialPassField) {
                var pwd = Ext.getCmp(field.initialPassField);
                return (val == pwd.getValue());
            }
            return true;
        },
        passwordText: Application.i18n.getMsg( 'vtype.password' ),

        /*
         * Validador para existência de e-mail
         */
        existeEmail: function(val, field) {
            var existeEmail = false;
            $.ajax({
                url: Application.contextPath + '/ajax/usuarios/existeEmail.jsp',
                type: 'post',
                data: {
                    email: val
                },
                async: false,
                dataType: 'json',
                cache: false,
                success: function( data, textStatus ) {
                    existeEmail = data.existeEmail;
                }
            });
            return !existeEmail;
        },
        existeEmailText: Application.i18n.getMsg( 'vtype.existeEmail' ),

        emailText: Application.i18n.getMsg( 'vtype.email' )

    });

};