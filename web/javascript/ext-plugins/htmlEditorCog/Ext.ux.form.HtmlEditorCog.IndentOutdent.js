/**
 * @author Shea Frederick - http://www.vinylfox.com
 * @class Ext.ux.form.HtmlEditor.IndentOutdent
 * @extends Ext.ux.form.HtmlEditor.MidasCommand
 * <p>A plugin that creates two buttons on the HtmlEditor for indenting and outdenting of selected text.</p>
 */
Ext.ux.form.HtmlEditorCog.IndentOutdent = Ext.extend(Ext.ux.form.HtmlEditorCog.MidasCommand, {
    // private
    midasBtns: ['|', {
        cmd: 'indent'
    }, {
        cmd: 'outdent'
    }]
});
