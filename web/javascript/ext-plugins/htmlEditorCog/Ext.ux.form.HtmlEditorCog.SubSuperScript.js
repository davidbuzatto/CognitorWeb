/**
 * @author Shea Frederick - http://www.vinylfox.com
 * @class Ext.ux.form.HtmlEditor.SubSuperScript
 * @extends Ext.ux.form.HtmlEditor.MidasCommand
 * <p>A plugin that creates two buttons on the HtmlEditor for superscript and subscripting of selected text.</p>
 */
Ext.ux.form.HtmlEditorCog.SubSuperScript = Ext.extend(Ext.ux.form.HtmlEditorCog.MidasCommand, {
    // private
    midasBtns: ['|', {
        enableOnSelection: true,
        cmd: 'subscript'
    }, {
        enableOnSelection: true,
        cmd: 'superscript'
    }]
});
