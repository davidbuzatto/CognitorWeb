/**
 * @author Shea Frederick - http://www.vinylfox.com
 * @class Ext.ux.form.HtmlEditor.Table
 * @extends Ext.util.Observable
 * <p>A plugin that creates a button on the HtmlEditor for making simple tables.</p>
 */
Ext.ux.form.HtmlEditorCog.Table = Ext.extend(Ext.util.Observable, {
    // private
    cmd: 'table',
    /**
     * @cfg {Array} tableBorderOptions
     * A nested array of value/display options to present to the user for table border style. Defaults to a simple list of 5 varrying border types.
     */
    // private
    init: function(cmp){
        this.cmp = cmp;
        this.cmp.on('render', this.onRender, this);
        this.tableBorderOptions = [
            ['1px solid #000', Application.i18n.getMsg( 'editorTex.plugins.table.b.sf' )],
            ['2px solid #000', Application.i18n.getMsg( 'editorTex.plugins.table.b.sg' )],
            ['1px dashed #000', Application.i18n.getMsg( 'editorTex.plugins.table.b.t' )],
            ['1px dotted #000', Application.i18n.getMsg( 'editorTex.plugins.table.b.p' )],
            ['none', Application.i18n.getMsg( 'editorTex.plugins.table.b.n' )]
        ];
    },
    // private
    onRender: function(){
        var cmp = this.cmp;
        var btn = this.cmp.getToolbar().addButton({
            iconCls: 'x-edit-table',
            handler: function(){
                if (!this.tableWindow){
                    this.tableWindow = new Ext.Window({
                        title: Application.i18n.getMsg( 'editorTex.plugins.table.titulo' ),
                        iconCls: 'iconeInserirTabela',
                        closeAction: 'hide',
                        modal: true,
                        resizable: false,
                        items: [{
                            itemId: 'insert-table',
                            xtype: 'form',
                            border: false,
                            plain: true,
                            bodyStyle: 'padding: 10px;',
                            labelWidth: 60,
                            labelAlign: 'right',
                            items: [{
                                xtype: 'numberfield',
                                allowBlank: false,
                                allowDecimals: false,
                                fieldLabel: Application.i18n.getMsg( 'editorTex.plugins.table.linhas' ),
                                name: 'row',
                                width: 60
                            }, {
                                xtype: 'numberfield',
                                allowBlank: false,
                                allowDecimals: false,
                                fieldLabel: Application.i18n.getMsg( 'editorTex.plugins.table.colunas' ),
                                name: 'col',
                                width: 60
                            }, {
                                xtype: 'combo',
                                fieldLabel: Application.i18n.getMsg( 'editorTex.plugins.table.borda' ),
                                name: 'border',
                                forceSelection: true,
                                mode: 'local',
                                store: new Ext.data.ArrayStore({
                                    autoDestroy: true,
                                    fields: ['spec', 'val'],
                                    data: this.tableBorderOptions
                                }),
                                triggerAction: 'all',
                                value: '1px solid #000',
                                displayField: 'val',
                                valueField: 'spec',
                                width: 110
                            }]
                        }],
                        buttons: [{
                            text: Application.i18n.getMsg( 'inserir' ),
                            handler: function(){
                                var frm = this.tableWindow.getComponent('insert-table').getForm();
                                if (frm.isValid()) {
                                    var border = frm.findField('border').getValue();
                                    var rowcol = [frm.findField('row').getValue(), frm.findField('col').getValue()];
                                    if (rowcol.length == 2 && rowcol[0] > 0 && rowcol[1] > 0) {
                                        var coluna = '';
                                        var html = '<table>';
                                        for (var row = 0; row < rowcol[0]; row++) {

                                            html += '<tr>';

                                            if ( row == 0 )
                                                coluna = Application.i18n.getMsg( 'coluna' );
                                            else
                                                coluna = '';

                                            for (var col = 0; col < rowcol[1]; col++) {
                                                html += '<td style="border: ' + border + ';">' + 
                                                    ( coluna == '' ? '' : coluna + ' ' + ( col + 1 ) ) +
                                                    '</td>';
                                            }

                                            html += '</tr>';
                                            
                                        }
                                        html += '</table><br/>';
                                        this.cmp.insertAtCursor(html);
                                    }
                                    this.tableWindow.hide();
                                }else{
                                    if (!frm.findField('row').isValid()){
                                        frm.findField('row').getEl().frame();
                                    }else if (!frm.findField('col').isValid()){
                                        frm.findField('col').getEl().frame();
                                    }
                                }
                            },
                            scope: this
                        }, {
                            text: Application.i18n.getMsg( 'cancelar' ),
                            handler: function(){
                                this.tableWindow.hide();
                            },
                            scope: this
                        }]
                    });
                
                }else{
                    this.tableWindow.getEl().frame();
                }
                this.tableWindow.show();
            },
            scope: this
        });
    }
});
