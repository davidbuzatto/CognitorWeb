/**
 * Arquivo de scripts da tela de metadados
 *
 * @author    David Buzatto
 * @copyright (c) 2009, by David Buzatto
 * @date      26 de Agosto de 2009
 *
 */

Metadados.initComponents = function() {

    // referência para a estrutura do material
    var estruturaMaterial = null;

    // flag para indicar se as tooltips devem ser registradas
    var registrarToolTips = true;

    // armazena o no selecionado anteriormente na árvore
    var noAnterior = null;

    /*
     * Tipos de entrada
     */
    var entries = new Ext.data.SimpleStore({
        fields: [ 'entry' ],
        data: [ [ 'URL' ], [ 'URI' ], [ 'DOI' ], [ 'ISBN' ], [ 'ISSN' ] ]
    });

    /*
     * Tipos de estrutura
     */
    var structures = new Ext.data.SimpleStore({
        fields: [ 'label', 'structure' ],
        data: [ [ Application.i18n.getMsg( 'metadados.struc.at' ), 'atomic' ],
                [ Application.i18n.getMsg( 'metadados.struc.co' ), 'collection' ],
                [ Application.i18n.getMsg( 'metadados.struc.ne' ), 'networked' ],
                [ Application.i18n.getMsg( 'metadados.struc.hi' ), 'hierarquical' ],
                [ Application.i18n.getMsg( 'metadados.struc.li' ), 'linear' ] ]
    });

    /*
     * Tipos de status
     */
    var status = new Ext.data.SimpleStore({
        fields: [ 'label', 'status' ],
        data: [ [ Application.i18n.getMsg( 'metadados.stat.dr' ), 'draft' ],
                [ Application.i18n.getMsg( 'metadados.stat.fi' ), 'final' ],
                [ Application.i18n.getMsg( 'metadados.stat.re' ), 'revised' ],
                [ Application.i18n.getMsg( 'metadados.stat.un' ), 'unavailable' ] ]
    });

    /*
     * Tipos de roles
     */
    var roles = new Ext.data.SimpleStore({
        fields: [ 'label', 'role' ],
        data: [ [ Application.i18n.getMsg( 'metadados.rol.au' ), 'author' ],
                [ Application.i18n.getMsg( 'metadados.rol.pu' ), 'publisher' ],
                [ Application.i18n.getMsg( 'metadados.rol.un' ), 'unknown' ],
                [ Application.i18n.getMsg( 'metadados.rol.in' ), 'initiator' ],
                [ Application.i18n.getMsg( 'metadados.rol.te' ), 'terminator' ],
                [ Application.i18n.getMsg( 'metadados.rol.va' ), 'validator' ],
                [ Application.i18n.getMsg( 'metadados.rol.ed' ), 'editor' ],
                [ Application.i18n.getMsg( 'metadados.rol.gd' ), 'graphical designer' ],
                [ Application.i18n.getMsg( 'metadados.rol.ti' ), 'technical implementer' ],
                [ Application.i18n.getMsg( 'metadados.rol.cp' ), 'content provider' ],
                [ Application.i18n.getMsg( 'metadados.rol.tv' ), 'technical validator' ],
                [ Application.i18n.getMsg( 'metadados.rol.ev' ), 'educational validator' ],
                [ Application.i18n.getMsg( 'metadados.rol.sw' ), 'script writer' ],
                [ Application.i18n.getMsg( 'metadados.rol.id' ), 'instructional designer' ],
                [ Application.i18n.getMsg( 'metadados.rol.se' ), 'subject matter expert' ] ]
    });

    /*
     * Idiomas
     */
    var languages = new Ext.data.SimpleStore({
        fields: [ 'label', 'language' ],
        data: [ [ Application.i18n.getMsg( 'metadados.lang.en' ), 'en' ],
                [ Application.i18n.getMsg( 'metadados.lang.pt' ), 'pt' ] ]
    });

    /*
     * Níveis de agregação
     */
    var aggregationLevels = new Ext.data.SimpleStore({
        fields: [ 'aggregationLevel' ],
        data: [ [ '1' ], [ '2' ], [ '3' ], [ '4' ] ]
    });


    /*
     * Tipos de interatividade de Educational
     */
    var eTi = new Ext.data.SimpleStore({
        fields: [ 'label', 'valor' ],
        data: [ [ Application.i18n.getMsg( 'metadados.ti.ac' ), 'active' ],
                [ Application.i18n.getMsg( 'metadados.ti.ex' ), 'expositive' ],
                [ Application.i18n.getMsg( 'metadados.ti.mi' ), 'mixed' ] ]
    });

    /*
     * Tipos de recurso de aprendizagem de Educational
     */
    var eTr = new Ext.data.SimpleStore({
        fields: [ 'label', 'valor' ],
        data: [ [ Application.i18n.getMsg( 'metadados.tr.ex' ), 'exercise' ],
                [ Application.i18n.getMsg( 'metadados.tr.si' ), 'simulation' ],
                [ Application.i18n.getMsg( 'metadados.tr.qu' ), 'questionnaire' ],
                [ Application.i18n.getMsg( 'metadados.tr.di' ), 'diagram' ],
                [ Application.i18n.getMsg( 'metadados.tr.fi' ), 'figure' ],
                [ Application.i18n.getMsg( 'metadados.tr.gr' ), 'graph' ],
                [ Application.i18n.getMsg( 'metadados.tr.in' ), 'index' ],
                [ Application.i18n.getMsg( 'metadados.tr.sl' ), 'slide' ],
                [ Application.i18n.getMsg( 'metadados.tr.ta' ), 'table' ],
                [ Application.i18n.getMsg( 'metadados.tr.na' ), 'narrative text' ],
                [ Application.i18n.getMsg( 'metadados.tr.exa' ), 'exam' ],
                [ Application.i18n.getMsg( 'metadados.tr.exp' ), 'experiment' ],
                [ Application.i18n.getMsg( 'metadados.tr.pr' ), 'problem statement' ],
                [ Application.i18n.getMsg( 'metadados.tr.se' ), 'self assessment' ],
                [ Application.i18n.getMsg( 'metadados.tr.le' ), 'lecture' ] ]
    });

    /*
     * Níveis de interatividade de Educational
     */
    var eNi = new Ext.data.SimpleStore({
        fields: [ 'label', 'valor' ],
        data: [ [ Application.i18n.getMsg( 'metadados.ni.vl' ), 'very low' ],
                [ Application.i18n.getMsg( 'metadados.ni.lo' ), 'low' ],
                [ Application.i18n.getMsg( 'metadados.ni.me' ), 'medium' ],
                [ Application.i18n.getMsg( 'metadados.ni.hi' ), 'high' ],
                [ Application.i18n.getMsg( 'metadados.ni.vh' ), 'very high' ] ]
    });

    /*
     * Densidades semânticas de Educational
     */
    var eDs = new Ext.data.SimpleStore({
        fields: [ 'label', 'valor' ],
        data: [ [ Application.i18n.getMsg( 'metadados.ds.vl' ), 'very low' ],
                [ Application.i18n.getMsg( 'metadados.ds.lo' ), 'low' ],
                [ Application.i18n.getMsg( 'metadados.ds.me' ), 'medium' ],
                [ Application.i18n.getMsg( 'metadados.ds.hi' ), 'high' ],
                [ Application.i18n.getMsg( 'metadados.ds.vh' ), 'very high' ] ]
    });

    /*
     * Papel do usuário final de Educational
     */
    var ePd = new Ext.data.SimpleStore({
        fields: [ 'label', 'valor' ],
        data: [ [ Application.i18n.getMsg( 'metadados.pd.te' ), 'teacher' ],
                [ Application.i18n.getMsg( 'metadados.pd.au' ), 'author' ],
                [ Application.i18n.getMsg( 'metadados.pd.le' ), 'learner' ],
                [ Application.i18n.getMsg( 'metadados.pd.ma' ), 'manager' ] ]
    });

    /*
     * Contextos de Educational
     */
    var eCon = new Ext.data.SimpleStore({
        fields: [ 'label', 'valor' ],
        data: [ [ Application.i18n.getMsg( 'metadados.con.sc' ), 'school' ],
                [ Application.i18n.getMsg( 'metadados.con.hi' ), 'higher education' ],
                [ Application.i18n.getMsg( 'metadados.con.tr' ), 'training' ],
                [ Application.i18n.getMsg( 'metadados.con.ot' ), 'other' ] ]
    });

    /*
     * Dificuldades de Educational
     */
    var eDi = new Ext.data.SimpleStore({
        fields: [ 'label', 'valor' ],
        data: [ [ Application.i18n.getMsg( 'metadados.di.ve' ), 'very easy' ],
                [ Application.i18n.getMsg( 'metadados.di.ea' ), 'easy' ],
                [ Application.i18n.getMsg( 'metadados.di.me' ), 'medium' ],
                [ Application.i18n.getMsg( 'metadados.di.di' ), 'difficult' ],
                [ Application.i18n.getMsg( 'metadados.di.vd' ), 'very difficult' ] ]
    });

    /*
     * Tipos de Technical
     */
    var tTypes = new Ext.data.SimpleStore({
        fields: [ 'label', 'type' ],
        data: [ [ Application.i18n.getMsg( 'metadados.ty.op' ), 'operating system' ],
                [ Application.i18n.getMsg( 'metadados.ty.br' ), 'browser' ] ]
    });

    /*
     * Nomes de Technical
     */
    var tNames = new Ext.data.SimpleStore({
        fields: [ 'label', 'name' ],
        data: [ [ Application.i18n.getMsg( 'metadados.na.pc' ), 'pc-dos' ],
                [ Application.i18n.getMsg( 'metadados.na.ms' ), 'ms-windows' ],
                [ Application.i18n.getMsg( 'metadados.na.ma' ), 'macos' ],
                [ Application.i18n.getMsg( 'metadados.na.un' ), 'unix' ],
                [ Application.i18n.getMsg( 'metadados.na.mu' ), 'multi-os' ],
                [ Application.i18n.getMsg( 'metadados.na.no' ), 'none' ],
                [ Application.i18n.getMsg( 'metadados.na.an' ), 'any' ],
                [ Application.i18n.getMsg( 'metadados.na.ne' ), 'netscape communicator' ],
                [ Application.i18n.getMsg( 'metadados.na.ie' ), 'ms-internet explorer' ],
                [ Application.i18n.getMsg( 'metadados.na.op' ), 'opera' ],
                [ Application.i18n.getMsg( 'metadados.na.am' ), 'amaya' ] ]
    });
    
    /*
     * Tipos de Relation
     */
    var rKinds = new Ext.data.SimpleStore({
        fields: [ 'label', 'kind' ],
        data: [ [ Application.i18n.getMsg( 'metadados.kin.isp' ), 'ispartof' ],
                [ Application.i18n.getMsg( 'metadados.kin.hasp' ), 'haspart' ],
                [ Application.i18n.getMsg( 'metadados.kin.isv' ), 'isversionof' ],
                [ Application.i18n.getMsg( 'metadados.kin.hasv' ), 'hasversion' ],
                [ Application.i18n.getMsg( 'metadados.kin.isf' ), 'isformatof' ],
                [ Application.i18n.getMsg( 'metadados.kin.hasf' ), 'hasformat' ],
                [ Application.i18n.getMsg( 'metadados.kin.ref' ), 'references' ],
                [ Application.i18n.getMsg( 'metadados.kin.isr' ), 'isreferencedby' ],
                [ Application.i18n.getMsg( 'metadados.kin.isbo' ), 'isbasedon' ],
                [ Application.i18n.getMsg( 'metadados.kin.isbf' ), 'isbasisfor' ],
                [ Application.i18n.getMsg( 'metadados.kin.req' ), 'requires' ],
                [ Application.i18n.getMsg( 'metadados.kin.isrq' ), 'isrequiredby' ] ]
    });

    /*
     * Propósitos de Classification
     */
    var cPurposes = new Ext.data.SimpleStore({
        fields: [ 'label', 'purp' ],
        data: [ [ Application.i18n.getMsg( 'metadados.purp.di' ), 'discipline' ],
                [ Application.i18n.getMsg( 'metadados.purp.id' ), 'idea' ],
                [ Application.i18n.getMsg( 'metadados.purp.pr' ), 'prerequisite' ],
                [ Application.i18n.getMsg( 'metadados.purp.edo' ), 'educational objective' ],
                [ Application.i18n.getMsg( 'metadados.purp.ac' ), 'accessibility restrictions' ],
                [ Application.i18n.getMsg( 'metadados.purp.edl' ), 'educational level' ],
                [ Application.i18n.getMsg( 'metadados.purp.sk' ), 'skill level' ],
                [ Application.i18n.getMsg( 'metadados.purp.se' ), 'security level' ],
                [ Application.i18n.getMsg( 'metadados.purp.co' ), 'competency' ] ]
    });


    /*
     * Relações de Minsky
     */
    var relations = new Ext.data.SimpleStore({
       fields: [ 'relation', 'texto' ],
       data: [ [ 'IsA', Application.i18n.getMsg( 'sc.relation.ia' ) ],
               [ 'PropertyOf', Application.i18n.getMsg( 'sc.relation.po' ) ],
               [ 'PartOf', Application.i18n.getMsg( 'sc.relation.pto' ) ],
               [ 'MadeOf', Application.i18n.getMsg( 'sc.relation.mo' ) ],
               [ 'DefinedAs', Application.i18n.getMsg( 'sc.relation.da' ) ],
               [ 'EffectOf', Application.i18n.getMsg( 'sc.relation.eo' ) ],
               [ 'DesirousEffectOf', Application.i18n.getMsg( 'sc.relation.doe' ) ],
               [ 'CapableOf', Application.i18n.getMsg( 'sc.relation.co' ) ],
               [ 'UsedFor', Application.i18n.getMsg( 'sc.relation.uf' ) ],
               [ 'CapableOfReceivingAction', Application.i18n.getMsg( 'sc.relation.cor' ) ],
               [ 'ConceptuallyRelatedTo', Application.i18n.getMsg( 'sc.relation.crt' ) ],
               [ 'PrerequisiteEventOf', Application.i18n.getMsg( 'sc.relation.pof' ) ],
               [ 'FirstSubeventOf', Application.i18n.getMsg( 'sc.relation.fso' ) ],
               [ 'SubeventOf', Application.i18n.getMsg( 'sc.relation.so' ) ],
               [ 'LastSubeventOf', Application.i18n.getMsg( 'sc.relation.lso' ) ],
               [ 'LocationOf', Application.i18n.getMsg( 'sc.relation.lo' ) ],
               [ 'MotivationOf', Application.i18n.getMsg( 'sc.relation.mto' ) ],
               [ 'DesireOf', Application.i18n.getMsg( 'sc.relation.do' ) ],
               [ 'All', Application.i18n.getMsg( 'sc.relation.todos' ) ] ]
    });


    // Aba General
    var abaGeneral = new Ext.Panel({
        title: Application.i18n.getMsg( 'metadados.cat.gen' ),
        autoScroll: true,
        items: [{
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.gen.id' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.gen.cat' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboGenCat',
                width: 130,
                mode: 'local',
                store: entries,
                displayField: 'entry',
                valueField: 'entry',
                name: 'entries',
                hiddenName: 'genCat',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.gen.ent' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldGenEntry',
                name: 'genEntry',
                width: 400,
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'titulo' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'titulof' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldGenTitulo',
                name: 'genTitulo',
                readOnly: true,
                width: 400,
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.gen.lang' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.gen.flang' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboGenLang',
                width: 130,
                mode: 'local',
                store: languages,
                displayField: 'label',
                valueField: 'language',
                name: 'languages',
                hiddenName: 'genLang',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.gen.desc' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.gen.fdesc' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textarea',
                id: 'areaGenDesc',
                name: 'genDesc',
                width: 400,
                height: 200
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/commonSense.png',
                handler: function( btn, evt ) {
                    criarJanelaSensoComum( 'areaGenDesc' );
                }
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.gen.key' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.gen.fkey' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldPcGen',
                name: 'genPc',
                width: 400
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/commonSense.png',
                handler: function( btn, evt ) {
                    criarJanelaSensoComum( 'fieldPcGen' );
                }
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.gen.cov' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.gen.fcov' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textarea',
                id: 'areaGenCob',
                name: 'genCob',
                width: 400
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/commonSense.png',
                handler: function( btn, evt ) {
                    criarJanelaSensoComum( 'areaGenCob' );
                }
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.gen.str' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.gen.src' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldGenEstructSrc',
                name: 'genEstructSrc',
                readOnly: true,
                value: 'LOMv1.0',
                width: 60,
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.gen.val' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboGenEstruct',
                width: 110,
                mode: 'local',
                store: structures,
                displayField: 'label',
                valueField: 'structure',
                name: 'structures',
                hiddenName: 'genEstruct',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.gen.al' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.gen.src' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldGenAgLevelSrc',
                name: 'genAgLevelSrc',
                readOnly: true,
                value: 'LOMv1.0',
                width: 60,
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.gen.val' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboGenAgLevel',
                width: 110,
                mode: 'local',
                store: aggregationLevels,
                displayField: 'aggregationLevel',
                valueField: 'aggregationLevel',
                name: 'agregationLevels',
                hiddenName: 'genAgLevel',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }]
    });

    // Aba LifeCycle
    var abaLifeCycle = new Ext.Panel({
        title: Application.i18n.getMsg( 'metadados.cat.lf' ),
        autoScroll: true,
        items: [{
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.lc.ver' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.lc.fver' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldLCVer',
                name: 'lcVer',
                width: 325,
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.lc.stat' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.lc.scr' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldLCStatSrc',
                name: 'lcStatSrc',
                readOnly: true,
                value: 'LOMv1.0',
                width: 60,
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.lc.val' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboLCStat',
                width: 130,
                mode: 'local',
                store: status,
                displayField: 'label',
                valueField: 'status',
                name: 'statuss',
                hiddenName: 'lcStat',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.lc.cont' ),
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            items: [{
                xtype: 'panel',
                title: Application.i18n.getMsg( 'metadados.cat.lc.pap' ),
                layout: 'table',
                bodyStyle: 'padding: 4px;',
                bodyBorder: false,
                layoutConfig: {
                    columns: 3
                },
                items: [{
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.lc.scr' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'textfield',
                    id: 'fieldLCRoleSrc',
                    name: 'lcRoleSrc',
                    readOnly: true,
                    value: 'LOMv1.0',
                    width: 60,
                    colspan: 2
                }, {
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.lc.val' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'combo',
                    id: 'comboLCRole',
                    width: 160,
                    mode: 'local',
                    store: roles,
                    displayField: 'label',
                    valueField: 'role',
                    name: 'roles',
                    hiddenName: 'lcRole',
                    editable: false,
                    triggerAction: 'all',
                    emptyText: Application.i18n.getMsg( 'selecione' ),
                    colspan: 2
                }]
            }, {
                xtype: 'panel',
                title: Application.i18n.getMsg( 'metadados.cat.lc.ent' ),
                layout: 'table',
                bodyStyle: 'padding: 4px;',
                bodyBorder: false,
                layoutConfig: {
                    columns: 3
                },
                items: [{
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.lc.fent' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'textfield',
                    id: 'fieldLCEnt',
                    name: 'lcEnt',
                    readOnly: true,
                    width: 400
                }, {
                    xtype: 'button',
                    cellCls: 'espacamentoSensoComum',
                    icon: Application.contextPath + '/imagens/icones/page_edit.png',
                    handler: function( btn, evt ) {
                        criarJanelaVCard( 'fieldLCEnt' );
                    }
                }]
            }, {
                xtype: 'panel',
                title: Application.i18n.getMsg( 'metadados.cat.lc.date' ),
                layout: 'table',
                bodyStyle: 'padding: 4px;',
                bodyBorder: false,
                layoutConfig: {
                    columns: 3
                },
                items: [{
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.lc.datetime' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'textfield',
                    id: 'fieldLCDate',
                    name: 'lcDate',
                    readOnly: true,
                    width: 200
                }, {
                    xtype: 'button',
                    cellCls: 'espacamentoSensoComum',
                    icon: Application.contextPath + '/imagens/icones/page_edit.png',
                    handler: function( btn, evt ) {
                        criarJanelaDataHora( 'fieldLCDate' );
                    }
                }, {
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.lc.desc' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'textarea',
                    id: 'areaLCDesc',
                    name: 'lcDesc',
                    width: 400,
                    height: 200
                }, {
                    xtype: 'button',
                    cellCls: 'espacamentoSensoComum',
                    icon: Application.contextPath + '/imagens/icones/commonSense.png',
                    handler: function( btn, evt ) {
                        criarJanelaSensoComum( 'areaLCDesc' );
                    }
                }]
            }]
        }]
    });

    // Aba MetaMetadata
    var abaMetaMetadata = new Ext.Panel({
        title: Application.i18n.getMsg( 'metadados.cat.mt' ),
        autoScroll: true,
        items: [{
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.mt.id' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.mt.cat' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboMtCat',
                width: 130,
                mode: 'local',
                store: entries,
                displayField: 'entry',
                valueField: 'entry',
                name: 'entries',
                hiddenName: 'mtCat',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.mt.entry' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldMtEntry',
                name: 'mtEntryVal',
                width: 400,
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.mt.cont' ),
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            items: [{
                xtype: 'panel',
                title: Application.i18n.getMsg( 'metadados.cat.mt.pap' ),
                layout: 'table',
                bodyStyle: 'padding: 4px;',
                bodyBorder: false,
                layoutConfig: {
                    columns: 3
                },
                items: [{
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.mt.scr' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'textfield',
                    id: 'fieldMtRoleSrc',
                    name: 'mtRoleSrc',
                    readOnly: true,
                    value: 'LOMv1.0',
                    width: 60,
                    colspan: 2
                }, {
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.mt.val' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'combo',
                    id: 'comboMtRole',
                    width: 160,
                    mode: 'local',
                    store: roles,
                    displayField: 'label',
                    valueField: 'role',
                    name: 'roles',
                    hiddenName: 'mtRole',
                    editable: false,
                    triggerAction: 'all',
                    emptyText: Application.i18n.getMsg( 'selecione' ),
                    colspan: 2
                }]
            }, {
                xtype: 'panel',
                title: Application.i18n.getMsg( 'metadados.cat.mt.ent' ),
                layout: 'table',
                bodyStyle: 'padding: 4px;',
                bodyBorder: false,
                layoutConfig: {
                    columns: 3
                },
                items: [{
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.mt.fent' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'textfield',
                    id: 'fieldMtEnt',
                    name: 'mtEnt',
                    readOnly: true,
                    width: 400
                }, {
                    xtype: 'button',
                    cellCls: 'espacamentoSensoComum',
                    icon: Application.contextPath + '/imagens/icones/page_edit.png',
                    handler: function( btn, evt ) {
                        criarJanelaVCard( 'fieldMtEnt' );
                    }
                }]
            }, {
                xtype: 'panel',
                title: Application.i18n.getMsg( 'metadados.cat.mt.date' ),
                layout: 'table',
                bodyStyle: 'padding: 4px;',
                bodyBorder: false,
                layoutConfig: {
                    columns: 3
                },
                items: [{
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.mt.datetime' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'textfield',
                    id: 'fieldMtDate',
                    name: 'mtDate',
                    readOnly: true,
                    width: 200
                }, {
                    xtype: 'button',
                    cellCls: 'espacamentoSensoComum',
                    icon: Application.contextPath + '/imagens/icones/page_edit.png',
                    handler: function( btn, evt ) {
                        criarJanelaDataHora( 'fieldMtDate' );
                    }
                }, {
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.mt.desc' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'textarea',
                    id: 'areaMtDesc',
                    name: 'mtDesc',
                    width: 400,
                    height: 200
                }, {
                    xtype: 'button',
                    cellCls: 'espacamentoSensoComum',
                    icon: Application.contextPath + '/imagens/icones/commonSense.png',
                    handler: function( btn, evt ) {
                        criarJanelaSensoComum( 'areaMTDesc' );
                    }
                }]
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.mt.mtsch' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.mt.sch' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldMtSch',
                name: 'mtSch',
                width: 200
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.mt.lang' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.mt.flang' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboMtLang',
                width: 130,
                mode: 'local',
                store: languages,
                displayField: 'label',
                valueField: 'language',
                name: 'languages',
                hiddenName: 'mtLang',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }]
    });

    // Aba Technical
    var abaTechnical = new Ext.Panel({
        title: Application.i18n.getMsg( 'metadados.cat.tc' ),
        autoScroll: true,
        items: [{
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.tc.for' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.tc.ffor' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldTcFor',
                name: 'tcFor',
                width: 400,
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.tc.si' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.tc.fsi' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldTcSize',
                name: 'tcSize',
                width: 200,
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.tc.loc' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.tc.floc' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textarea',
                id: 'areaTcLoc',
                name: 'tcLoc',
                width: 400,
                height: 200,
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.tc.req' ),
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            items: [{
                xtype: 'panel',
                title: Application.i18n.getMsg( 'metadados.cat.tc.orc' ),
                bodyStyle: 'padding: 4px;',
                bodyBorder: false,
                items: [{
                    xtype: 'panel',
                    title: Application.i18n.getMsg( 'metadados.cat.tc.ty' ),
                    layout: 'table',
                    bodyStyle: 'padding: 4px;',
                    bodyBorder: false,
                    layoutConfig: {
                        columns: 3
                    },
                    items: [{
                        xtype: 'label',
                        html: Application.i18n.getMsg( 'metadados.cat.tc.src' ),
                        cellCls: 'espacamentoFormMetadados'
                    }, {
                        xtype: 'textfield',
                        id: 'fieldTcTypeSrc',
                        name: 'tcTypeSrc',
                        readOnly: true,
                        value: 'LOMv1.0',
                        width: 60,
                        colspan: 2
                    }, {
                        xtype: 'label',
                        html: Application.i18n.getMsg( 'metadados.cat.tc.val' ),
                        cellCls: 'espacamentoFormMetadados'
                    }, {
                        xtype: 'combo',
                        id: 'comboTcType',
                        width: 160,
                        mode: 'local',
                        store: tTypes,
                        displayField: 'label',
                        valueField: 'type',
                        name: 'types',
                        hiddenName: 'tcType',
                        editable: false,
                        triggerAction: 'all',
                        emptyText: Application.i18n.getMsg( 'selecione' ),
                        colspan: 2
                    }]
                }, {
                    xtype: 'panel',
                    title: Application.i18n.getMsg( 'metadados.cat.tc.na' ),
                    layout: 'table',
                    bodyStyle: 'padding: 4px;',
                    bodyBorder: false,
                    layoutConfig: {
                        columns: 3
                    },
                    items: [{
                        xtype: 'label',
                        html: Application.i18n.getMsg( 'metadados.cat.tc.src' ),
                        cellCls: 'espacamentoFormMetadados'
                    }, {
                        xtype: 'textfield',
                        id: 'fieldTcNameSrc',
                        name: 'tcNameSrc',
                        readOnly: true,
                        value: 'LOMv1.0',
                        width: 60,
                        colspan: 2
                    }, {
                        xtype: 'label',
                        html: Application.i18n.getMsg( 'metadados.cat.tc.val' ),
                        cellCls: 'espacamentoFormMetadados'
                    }, {
                        xtype: 'combo',
                        id: 'comboTcName',
                        width: 200,
                        mode: 'local',
                        store: tNames,
                        displayField: 'label',
                        valueField: 'name',
                        name: 'names',
                        hiddenName: 'tcName',
                        editable: false,
                        triggerAction: 'all',
                        emptyText: Application.i18n.getMsg( 'selecione' ),
                        colspan: 2
                    }]
                }, {
                    xtype: 'panel',
                    title: Application.i18n.getMsg( 'metadados.cat.tc.ver' ),
                    layout: 'table',
                    bodyStyle: 'padding: 4px;',
                    bodyBorder: false,
                    layoutConfig: {
                        columns: 3
                    },
                    items: [{
                        xtype: 'label',
                        html: Application.i18n.getMsg( 'metadados.cat.tc.min' ),
                        cellCls: 'espacamentoFormMetadados'
                    }, {
                        xtype: 'textfield',
                        id: 'fieldTcMinVer',
                        name: 'tcMinVer',
                        width: 200,
                        colspan: 2
                    }, {
                        xtype: 'label',
                        html: Application.i18n.getMsg( 'metadados.cat.tc.max' ),
                        cellCls: 'espacamentoFormMetadados'
                    }, {
                        xtype: 'textfield',
                        id: 'fieldTcMaxVer',
                        name: 'tcMaxVer',
                        width: 200,
                        colspan: 2
                    }]
                }]
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.tc.ir' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.tc.fir' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textarea',
                id: 'areaTcInstRem',
                name: 'tcInstRem',
                width: 400,
                height: 200
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/commonSense.png',
                handler: function( btn, evt ) {
                    criarJanelaSensoComum( 'areaTcInstRem' );
                }
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.tc.opr' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.tc.fopr' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textarea',
                id: 'areaTcOthInst',
                name: 'tcInstOthInst',
                width: 400,
                height: 200
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/commonSense.png',
                handler: function( btn, evt ) {
                    criarJanelaSensoComum( 'areaTcOthInst' );
                }
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.tc.dur' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.tc.fdur' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldTcDur',
                name: 'tcDur',
                readOnly: true,
                width: 200
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/page_edit.png',
                handler: function( btn, evt ) {
                    criarJanelaDuracao( 'fieldTcDur' );
                }
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.tc.desc' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textarea',
                id: 'areaTcDesc',
                name: 'tcDesc',
                width: 400,
                height: 200
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/commonSense.png',
                handler: function( btn, evt ) {
                    criarJanelaSensoComum( 'areaTcDesc' );
                }
            }]
        }]
    });

    // Aba Educational
    var abaEducational = new Ext.Panel({
        title: Application.i18n.getMsg( 'metadados.cat.ed' ),
        autoScroll: true,
        items: [{
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.ed.ti' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.src' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldEdTiSrc',
                name: 'edTiSrc',
                readOnly: true,
                value: 'LOMv1.0',
                width: 60,
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.val' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboEdTi',
                width: 180,
                mode: 'local',
                store: eTi,
                displayField: 'label',
                valueField: 'valor',
                name: 'edTis',
                hiddenName: 'edTi',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.ed.tr' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.src' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldEdTrSrc',
                name: 'edTrSrc',
                readOnly: true,
                value: 'LOMv1.0',
                width: 60,
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.val' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboEdTr',
                width: 180,
                mode: 'local',
                store: eTr,
                displayField: 'label',
                valueField: 'valor',
                name: 'edTrs',
                hiddenName: 'edTr',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.ed.ni' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.src' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldEdNiSrc',
                name: 'edNiSrc',
                readOnly: true,
                value: 'LOMv1.0',
                width: 60,
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.val' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboEdNi',
                width: 180,
                mode: 'local',
                store: eNi,
                displayField: 'label',
                valueField: 'valor',
                name: 'edNis',
                hiddenName: 'edNi',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.ed.ds' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.src' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldEdDsSrc',
                name: 'edDsSrc',
                readOnly: true,
                value: 'LOMv1.0',
                width: 60,
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.val' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboEdDs',
                width: 180,
                mode: 'local',
                store: eDs,
                displayField: 'label',
                valueField: 'valor',
                name: 'edDss',
                hiddenName: 'edDs',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.ed.ps' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.src' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldEdPdSrc',
                name: 'edPdSrc',
                readOnly: true,
                value: 'LOMv1.0',
                width: 60,
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.val' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboEdPd',
                width: 180,
                mode: 'local',
                store: ePd,
                displayField: 'label',
                valueField: 'valor',
                name: 'edPds',
                hiddenName: 'edPd',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.ed.con' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.src' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldEdConSrc',
                name: 'edConSrc',
                readOnly: true,
                value: 'LOMv1.0',
                width: 60,
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.val' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboEdCon',
                width: 180,
                mode: 'local',
                store: eCon,
                displayField: 'label',
                valueField: 'valor',
                name: 'edCons',
                hiddenName: 'edCon',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.ed.tar' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.int' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldEdTar',
                name: 'edTar',
                readOnly: true,
                width: 100
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/page_edit.png',
                handler: function( btn, evt ) {
                    criarJanelaIntervalo( 'fieldEdTar' );
                }
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.ed.di' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.src' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldEdDiSrc',
                name: 'edDiSrc',
                readOnly: true,
                value: 'LOMv1.0',
                width: 60,
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.val' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboEdDi',
                width: 180,
                mode: 'local',
                store: eDi,
                displayField: 'label',
                valueField: 'valor',
                name: 'edDis',
                hiddenName: 'edDi',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.ed.tta' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.dur' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldEdDur',
                name: 'edDur',
                readOnly: true,
                width: 200
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/page_edit.png',
                handler: function( btn, evt ) {
                    criarJanelaDuracao( 'fieldEdDur' );
                }
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.fdesc' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textarea',
                id: 'areaEdDurDesc',
                name: 'edDurDesc',
                width: 400,
                height: 200
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/commonSense.png',
                handler: function( btn, evt ) {
                    criarJanelaSensoComum( 'areaEdDurDesc' );
                }
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.ed.desc' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.fdesc' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textarea',
                id: 'areaEdDesc',
                name: 'edDesc',
                width: 400,
                height: 200
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/commonSense.png',
                handler: function( btn, evt ) {
                    criarJanelaSensoComum( 'areaEdDesc' );
                }
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.ed.lang' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ed.flang' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboEdLang',
                width: 130,
                mode: 'local',
                store: languages,
                displayField: 'label',
                valueField: 'language',
                name: 'languages',
                hiddenName: 'edLang',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }]
    });

    // Aba Rights
    var abaRights = new Ext.Panel({
        title: Application.i18n.getMsg( 'metadados.cat.ri' ),
        autoScroll: true,
        items: [{
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.ri.cost' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ri.src' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldRigCostSrc',
                name: 'rigCostSrc',
                readOnly: true,
                value: 'LOMv1.0',
                width: 60,
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ri.val' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboRigCost',
                width: 110,
                mode: 'local',
                store: new Ext.data.SimpleStore({
                    fields: [ 'label', 'valor' ],
                    data: [ [ Application.i18n.getMsg( 'sim' ), 'yes' ],
                            [ Application.i18n.getMsg( 'nao' ), 'no' ] ]
                }),
                displayField: 'label',
                valueField: 'valor',
                name: 'simNaoCost',
                hiddenName: 'rigCost',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.ri.cop' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ri.src' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldRigCopSrc',
                name: 'rigCopSrc',
                readOnly: true,
                value: 'LOMv1.0',
                width: 60,
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ri.val' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboRigCop',
                width: 110,
                mode: 'local',
                store: new Ext.data.SimpleStore({
                    fields: [ 'label', 'valor' ],
                    data: [ [ Application.i18n.getMsg( 'sim' ), 'yes' ],
                            [ Application.i18n.getMsg( 'nao' ), 'no' ] ]
                }),
                displayField: 'label',
                valueField: 'valor',
                name: 'simNaoCop',
                hiddenName: 'rigCop',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.ri.desc' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.ri.fdesc' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textarea',
                id: 'areaRigDesc',
                name: 'rigDesc',
                width: 400,
                height: 200
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/commonSense.png',
                handler: function( btn, evt ) {
                    criarJanelaSensoComum( 'areaRigDesc' );
                }
            }]
        }]
    });

    // Aba Relation
    var abaRelation = new Ext.Panel({
        title: Application.i18n.getMsg( 'metadados.cat.re' ),
        autoScroll: true,
        items: [{
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.re.kin' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.re.src' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldReKinSrc',
                name: 'reKinSrc',
                readOnly: true,
                value: 'LOMv1.0',
                width: 60,
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.re.val' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboReKin',
                width: 160,
                mode: 'local',
                store: rKinds,
                displayField: 'label',
                valueField: 'kind',
                name: 'kindRe',
                hiddenName: 'reKin',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.re.re' ),
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            items: [{
                xtype: 'panel',
                title: Application.i18n.getMsg( 'metadados.cat.re.id' ),
                layout: 'table',
                bodyStyle: 'padding: 4px;',
                bodyBorder: false,
                layoutConfig: {
                    columns: 3
                },
                items: [{
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.re.cat' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'combo',
                    id: 'comboReCat',
                    width: 130,
                    mode: 'local',
                    store: entries,
                    displayField: 'entry',
                    valueField: 'entry',
                    name: 'entries',
                    hiddenName: 'reCat',
                    editable: false,
                    triggerAction: 'all',
                    emptyText: Application.i18n.getMsg( 'selecione' ),
                    colspan: 2
                }, {
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.re.ent' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'textfield',
                    id: 'fieldReEntry',
                    name: 'reEntry',
                    width: 400,
                    colspan: 2
                }]
            }, {
                xtype: 'panel',
                title: Application.i18n.getMsg( 'metadados.cat.re.desc' ),
                layout: 'table',
                bodyStyle: 'padding: 4px;',
                bodyBorder: false,
                layoutConfig: {
                    columns: 3
                },
                items: [{
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.re.fdesc' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'textarea',
                    id: 'areaReDesc',
                    name: 'reDesc',
                    width: 400,
                    height: 200
                }, {
                    xtype: 'button',
                    cellCls: 'espacamentoSensoComum',
                    icon: Application.contextPath + '/imagens/icones/commonSense.png',
                    handler: function( btn, evt ) {
                        criarJanelaSensoComum( 'areaReDesc' );
                    }
                }]
            }]
        }]
    });

    // Aba Annotation
    var abaAnnotation = new Ext.Panel({
        title: Application.i18n.getMsg( 'metadados.cat.an' ),
        autoScroll: true,
        items: [{
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.an.ent' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.an.fent' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldAnEnt',
                name: 'anEnt',
                readOnly: true,
                width: 400
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/page_edit.png',
                handler: function( btn, evt ) {
                    criarJanelaVCard( 'fieldAnEnt' );
                }
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.an.date' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.an.fdatetime' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldAnDate',
                name: 'anDate',
                readOnly: true,
                width: 200
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/page_edit.png',
                handler: function( btn, evt ) {
                    criarJanelaDataHora( 'fieldAnDate' );
                }
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.an.fdesc' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textarea',
                id: 'areaAnDateDesc',
                name: 'anDateDesc',
                width: 400,
                height: 200
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/commonSense.png',
                handler: function( btn, evt ) {
                    criarJanelaSensoComum( 'areaAnDateDesc' );
                }
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.an.desc' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.an.fdesc' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textarea',
                id: 'areaAnDesc',
                name: 'anDesc',
                width: 400,
                height: 200
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/commonSense.png',
                handler: function( btn, evt ) {
                    criarJanelaSensoComum( 'areaAnDesc' );
                }
            }]
        }]
    });

    // Aba Classfication
    var abaClassification = new Ext.Panel({
        title: Application.i18n.getMsg( 'metadados.cat.cl' ),
        autoScroll: true,
        items: [{
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.cl.purp' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.cl.src' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldClPurpSrc',
                name: 'clPurpSrc',
                readOnly: true,
                value: 'LOMv1.0',
                width: 60,
                colspan: 2
            }, {
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.cl.val' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'combo',
                id: 'comboClPurp',
                width: 180,
                mode: 'local',
                store: cPurposes,
                displayField: 'label',
                valueField: 'purp',
                name: 'purposes',
                hiddenName: 'clPurp',
                editable: false,
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                colspan: 2
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.cl.tpath' ),
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            items: [{
                xtype: 'panel',
                title: Application.i18n.getMsg( 'metadados.cat.cl.psrc' ),
                layout: 'table',
                bodyStyle: 'padding: 4px;',
                bodyBorder: false,
                layoutConfig: {
                    columns: 3
                },
                items: [{
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.cl.src' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'textfield',
                    id: 'fieldClTaxSrc',
                    name: 'clTaxSrc',
                    width: 390,
                    colspan: 2
                }]
            }, {
                xtype: 'panel',
                title: Application.i18n.getMsg( 'metadados.cat.cl.tax' ),
                layout: 'table',
                bodyStyle: 'padding: 4px;',
                bodyBorder: false,
                layoutConfig: {
                    columns: 3
                },
                items: [{
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.cl.id' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'textfield',
                    id: 'fieldClTaxTaxonId',
                    name: 'clTaxTaxonId',
                    width: 380,
                    colspan: 2
                }, {
                    xtype: 'label',
                    html: Application.i18n.getMsg( 'metadados.cat.cl.ent' ),
                    cellCls: 'espacamentoFormMetadados'
                }, {
                    xtype: 'textfield',
                    id: 'fieldClTaxTaxonEntry',
                    name: 'clTaxTaxonEntry',
                    width: 380,
                    colspan: 2
                }]
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.cl.desc' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.cl.fdesc' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textarea',
                id: 'areaClDesc',
                name: 'clDesc',
                width: 400,
                height: 200
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/commonSense.png',
                handler: function( btn, evt ) {
                    criarJanelaSensoComum( 'areaClDesc' );
                }
            }]
        }, {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'metadados.cat.cl.key' ),
            layout: 'table',
            bodyStyle: 'padding: 4px;',
            bodyBorder: false,
            layoutConfig: {
                columns: 3
            },
            items: [{
                xtype: 'label',
                html: Application.i18n.getMsg( 'metadados.cat.cl.fkey' ),
                cellCls: 'espacamentoFormMetadados'
            }, {
                xtype: 'textfield',
                id: 'fieldClKey',
                name: 'clKey',
                width: 400
            }, {
                xtype: 'button',
                cellCls: 'espacamentoSensoComum',
                icon: Application.contextPath + '/imagens/icones/commonSense.png',
                handler: function( btn, evt ) {
                    criarJanelaSensoComum( 'fieldClKey' );
                }
            }]
        }]
    });

    /*
     * Formulário para o envio dos dados dos metadados.
     */
    var painelDados = new Ext.FormPanel({
        url: Application.contextPath + '/servlets/MetadadosServlet',
        border: false,
        bodyStyle: 'padding: 4px;',
        region: 'center',
        layout: 'border',
        items: [{
            xtype: 'tabpanel',
            region: 'center',
            hideLabel: true,
            labelSeparator: '',
            anchor: '100%',
            enableTabScroll: true,
            activeTab: 0,
            items: [
                abaGeneral,
                abaLifeCycle,
                abaMetaMetadata,
                abaTechnical,
                abaEducational,
                abaRights,
                abaRelation,
                abaAnnotation,
                abaClassification
            ]
        }]
    });

    /*
     * Árvore para os OAs.
     */
    var arvoreOAs = new Ext.tree.TreePanel({
        region: 'west',
        width: 170,
        autoScroll: true,
        containerScroll: true,
        useArrow: true,
        animate: false,
        root: new Ext.tree.TreeNode({
            leaf: false
        }),
        listeners: {
            click: function( no ) {

                if ( Global.DEBUG ) {
                    Uteis.alertNo( no );
                }
                
                ligarDesligarInterface( Global.LIGAR );

                /*
                 * se um nó foi selecionado anteriormente, salva os seus dados
                 * e carrega os dados do selecionado.
                 */
                if ( noAnterior && noAnterior.attributes.idInterno ) {
                    // dentro da função de salvar está a chamada para a carga
                    salvarMetadados( noAnterior, no, false );
                } else {
                    carregarMetadados( no );
                }
                
            },
            beforeclick: function( no, evt ) {
                noAnterior = arvoreOAs.getSelectionModel().getSelectedNode();
            }
        },
        buttons: [{
            icon: Application.contextPath + '/imagens/icones/expandir.png',
            handler: function( b, evt ) {
                arvoreOAs.expandAll();
            }
        }, {
            icon: Application.contextPath + '/imagens/icones/contrair.png',
            handler: function( b, evt ) {
                arvoreOAs.collapseAll();
            }
        }]
    })

    /*
     * Janela de metadados. É uma janela que vai ser reaproveitada
     */
    var janelaMetadados = new Ext.Window({
        id: 'janelaMetadados',
        iconCls: 'iconeMetadados',
        title: Application.i18n.getMsg( 'metadados.titulo' ),
        layout: 'border',
        height: 400,
        width: 720,
        modal: true,
        resizable: false,
        closeAction: 'hide',
        items: [
            painelDados,
            arvoreOAs
        ],
        fbar: [{
            text: Application.i18n.getMsg( 'salvarFechar' ),
            id: 'btnMetadadosSalvar',
            handler: function( btn, evt ) {
                var no = arvoreOAs.getSelectionModel().getSelectedNode();
                salvarMetadados( no, null, true );
            }
        }],
        listeners: {
            hide: function( janela ) {
                painelDados.getForm().reset();
            }
        }
    });

    /*
     * Função para salvar os metadados de um OA representado por um nó.
     * Se noSelecionado não for null, carrega os dados desse nó.
     */
    var salvarMetadados = function( no, noSelecionado, fechar ) {

        // se existe id interno, significa que o nó representa em OA
        if ( no.attributes.idInterno ) {
            painelDados.getForm().submit({
                params: {
                    id: no.attributes.idInterno,
                    tipo: no.attributes.tipo,
                    acao: 'salvar'
                },
                success: function( form, action ) {
                    if ( noSelecionado )
                        carregarMetadados( noSelecionado );
                    if ( fechar )
                        janelaMetadados.hide();
                },
                failure: function( form, action ) {
                    Uteis.exibirMensagemErro( Uteis.criarMensagemErro( action, Application.i18n ) );
                },
                waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                waitMsg: Application.i18n.getMsg( 'salvando' )
            });
        }

    };

    /*
     * Função para carregar os metadados.
     */
    var carregarMetadados = function( no ) {

        var tipo = no.attributes.tipo;

        switch ( tipo ) {

            case 'material':
            case 'grupo':
            case 'pagina':
            case 'imagem':
            case 'video':
            case 'som':

                painelDados.getForm().load({
                    url: Application.contextPath + '/servlets/MetadadosServlet',
                    params: {
                        id: no.attributes.idInterno,
                        tipo: tipo,
                        acao: 'obter'
                    },
                    success: function( form, action ) {
                    },
                    waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                    waitMsg: Application.i18n.getMsg( 'carregando' )
                });

                break;

            default:
                painelDados.getForm().reset();
                break;

        }

    }

    /*
     * Cria a janela de senso comum para cada campo.
     */
    var criarJanelaSensoComum = function( idCampoQueChamou ) {

        var campoQueChamou = Ext.getCmp( idCampoQueChamou );

        /*
         * Lista dos dados do senso comum.
         */
        var dadosSensoComum = new Ext.list.ListView({
            region: 'center',
            height: 200,
            autoScroll: true,
            border: false,
            multiSelect: true,
            hideHeaders: true,
            store: new Ext.data.Store({
                data: [],
                reader: new Ext.data.ArrayReader( {
                    idIndex: 0
                },
                Ext.data.Record.create([
                    { name: 'conceito' },
                    { name: 'relacaoMinsky' }
                ]))
            }),
            columns: [{
                dataIndex: 'conceito'
            }]
        });

        var form = new Ext.FormPanel({
            url: Application.contextPath + '/servlets/CommonSenseServlet',
            border: false,
            bodyStyle: 'padding: 4px;',
            labelWidth: 50,
            labelAlign: 'right',
            items: [{
                xtype: 'textfield',
                fieldLabel: Application.i18n.getMsg( 'conceito' ),
                name: 'concept',
                allowBlank: false,
                blankText: Application.i18n.getMsg( 'vtype.blank' ),
                width: 200
            }, {
                xtype: 'combo',
                fieldLabel: Application.i18n.getMsg( 'relacao' ),
                name: 'relations',
                hiddenName: 'relation',
                allowBlank: false,
                editable: false,
                blankText: Application.i18n.getMsg( 'vtype.blank' ),
                mode: 'local',
                store: relations,
                valueField: 'relation',
                displayField: 'texto',
                triggerAction: 'all',
                emptyText: Application.i18n.getMsg( 'selecione' ),
                width: 200
            }, {
                xtype: 'panel',
                title: Application.i18n.getMsg( 'sc.resultados' ),
                items: [
                    dadosSensoComum
                ]
            }]
        });

        var janela = new Ext.Window( {
            title: Application.i18n.getMsg( 'sc.titulo' ),
            iconCls: 'iconeSensoComum',
            modal: true,
            resizable: false,
            items: [
                form
            ],
            fbar: [{
                text: Application.i18n.getMsg( 'sc.buscar' ),
                handler: function( btn, evt ) {
                    form.getForm().submit({
                        success: function( form, action ) {
                            Uteis.preencheDadosSensoComum( action.result.results, dadosSensoComum );
                        },
                        failure: function( form, action ) {
                            Uteis.exibirMensagemErro( Uteis.criarMensagemErro( action, Application.i18n ) );
                        },
                        waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                        waitMsg: Application.i18n.getMsg( 'sc.obtendoDados' )
                    });
                }
            }, {
                text: Application.i18n.getMsg( 'sc.copiar' ),
                handler: function( btn, evt ) {

                    var nos = dadosSensoComum.getSelectedRecords();
                    var valores = '';

                    for ( var i = 0; i < nos.length; i++ ) {
                        valores += ' ' + nos[i].data.conceito;
                    }

                    campoQueChamou.setValue( campoQueChamou.getValue() + valores );
                    dadosSensoComum.clearSelections();

                }
            }, {
                text: Application.i18n.getMsg( 'cancelar' ),
                handler: function( btn, evt ) {
                    janela.close();
                }
            }]
        });

        janela.show();
        janela.center();

    };

    /*
     * Cria a janela de vCard para um campo.
     */
    var criarJanelaVCard = function( idCampoQueChamou ) {

        var campoQueChamou = Ext.getCmp( idCampoQueChamou );
        var conteudoCampo = campoQueChamou.getValue();
        var pLinha = "&#13;&#10;";

        var janela = new Ext.Window( {
            title: Application.i18n.getMsg( 'metadados.tipos.edtVCard' ),
            iconCls: 'iconeEdicao',
            modal: true,
            resizable: false,
            items: [{
                xtype: 'panel',
                layout: 'form',
                labelAlign: 'right',
                border: false,
                bodyStyle: 'padding: 4px;',
                labelWidth: 100,
                items: [{
                    xtype: 'textfield',
                    id: 'fieldvCardNome',
                    fieldLabel: Application.i18n.getMsg( 'metadados.tipos.nome' ),
                    width: 200
                }, {
                    xtype: 'textfield',
                    id: 'fieldvCardEnd',
                    fieldLabel: Application.i18n.getMsg( 'endereco' ),
                    width: 200
                }, {
                    xtype: 'textfield',
                    id: 'fieldvCardTel',
                    fieldLabel: Application.i18n.getMsg( 'metadados.tipos.telefone' ),
                    width: 120
                }, {
                    xtype: 'textfield',
                    id: 'fieldvCardEmail',
                    fieldLabel: Application.i18n.getMsg( 'email' ),
                    width: 200
                }, {
                    xtype: 'textfield',
                    id: 'fieldvCardTit',
                    fieldLabel: Application.i18n.getMsg( 'titulo' ),
                    width: 120
                }, {
                    xtype: 'textfield',
                    id: 'fieldvCardOcu',
                    fieldLabel: Application.i18n.getMsg( 'ocupacao' ),
                    width: 120
                }, {
                    xtype: 'textfield',
                    id: 'fieldvCardOrg',
                    fieldLabel: Application.i18n.getMsg( 'metadados.tipos.organizacao' ),
                    width: 200
                }]
            }],
            fbar: [{
                text: Application.i18n.getMsg( 'ok' ),
                handler: function( btn, evt ) {

                    var conteudo = 'BEGIN:VCARD' + pLinha + 'VERSION:2.1' + pLinha;

                    var nome = Ext.util.Format.trim( Ext.getCmp( 'fieldvCardNome' ).getValue() );
                    var end = Ext.util.Format.trim( Ext.getCmp( 'fieldvCardEnd' ).getValue() );
                    var tel = Ext.util.Format.trim( Ext.getCmp( 'fieldvCardTel' ).getValue() );
                    var email = Ext.util.Format.trim( Ext.getCmp( 'fieldvCardEmail' ).getValue() );
                    var tit = Ext.util.Format.trim( Ext.getCmp( 'fieldvCardTit' ).getValue() );
                    var ocu = Ext.util.Format.trim( Ext.getCmp( 'fieldvCardOcu' ).getValue() );
                    var org = Ext.util.Format.trim( Ext.getCmp( 'fieldvCardOrg' ).getValue() );

                    if ( nome != '' )
                        conteudo += 'FN:' + nome + pLinha;
                    if ( end != '' )
                        conteudo += 'ADR:' + end + pLinha;
                    if ( tel != '' )
                        conteudo += 'TEL:' + tel + pLinha;
                    if ( email != '' )
                        conteudo += 'EMAIL:' + email + pLinha;
                    if ( tit != '' )
                        conteudo += 'TITLE:' + tit + pLinha;
                    if ( ocu != '' )
                        conteudo += 'ROLE:' + ocu + pLinha;
                    if ( org != '' )
                        conteudo += 'ORG:' + org + pLinha;

                    conteudo += 'END:VCARD';

                    campoQueChamou.setValue( conteudo );

                    janela.close();
                }
            }, {
                text: Application.i18n.getMsg( 'cancelar' ),
                handler: function( btn, evt ) {
                    janela.close();
                }
            }],
            listeners: {
                show: function() {

                    var valores = conteudoCampo.split( pLinha );
                    var mapa = [];

                    for ( var i = 0; i < valores.length; i++ ) {
                        var k = valores[i].substring( 0, valores[i].indexOf( ':' ) );
                        var v = valores[i].substring( valores[i].indexOf( ':' ) + 1, valores[i].length );
                        mapa[k] = v;
                    }

                    if ( mapa[ 'FN' ] )
                        Ext.getCmp( 'fieldvCardNome' ).setValue( mapa[ 'FN' ] );
                    if ( mapa[ 'ADR' ] )
                        Ext.getCmp( 'fieldvCardEnd' ).setValue( mapa[ 'ADR' ] );
                    if ( mapa[ 'TEL' ] )
                        Ext.getCmp( 'fieldvCardTel' ).setValue( mapa[ 'TEL' ] );
                    if ( mapa[ 'EMAIL' ] )
                        Ext.getCmp( 'fieldvCardEmail' ).setValue( mapa[ 'EMAIL' ] );
                    if ( mapa[ 'TITLE' ] )
                        Ext.getCmp( 'fieldvCardTit' ).setValue( mapa[ 'TITLE' ] );
                    if ( mapa[ 'ROLE' ] )
                        Ext.getCmp( 'fieldvCardOcu' ).setValue( mapa[ 'ROLE' ] );
                    if ( mapa[ 'ORG' ] )
                        Ext.getCmp( 'fieldvCardOrg' ).setValue( mapa[ 'ORG' ] );

                }
            }
        });

        janela.show();
        janela.center();

    };

    /*
     * Cria a janela de data e hora para um campo.
     */
    var criarJanelaDataHora = function( idCampoQueChamou ) {

        var campoQueChamou = Ext.getCmp( idCampoQueChamou );
        var conteudoCampo = campoQueChamou.getValue();

        var janela = new Ext.Window( {
            title: Application.i18n.getMsg( 'metadados.tipos.edtDataHora' ),
            iconCls: 'iconeEdicao',
            modal: true,
            resizable: false,
            items: [{
                xtype: 'panel',
                layout: 'form',
                labelAlign: 'right',
                border: false,
                bodyStyle: 'padding: 4px;',
                labelWidth: 100,
                items: [{
                    xtype: 'datefield',
                    id: 'fieldDataHoraData',
                    format: 'Y-m-d',
                    fieldLabel: Application.i18n.getMsg( 'metadados.tipos.data' ),
                    invalidText: Application.i18n.getMsg( 'vtype.date' )
                }, {
                    xtype: 'timefield',
                    id: 'fieldDataHoraHora',
                    fieldLabel: Application.i18n.getMsg( 'metadados.tipos.hora' ),
                    invalidText: Application.i18n.getMsg( 'vtype.time' )
                }]
            }],
            fbar: [{
                text: Application.i18n.getMsg( 'ok' ),
                handler: function( btn, evt ) {

                    var data = Ext.getCmp( 'fieldDataHoraData' ).getValue();
                    var hora = Ext.getCmp( 'fieldDataHoraHora' ).getValue();
                    var valor = '';

                    if ( data ) {

                        var dia = Number( data.getDate() ) < 10 ? '0' + data.getDate() : data.getDate();
                        var mes = Number( data.getMonth() + 1 ) < 10 ? '0' + ( data.getMonth() + 1 ) : data.getMonth() + 1;
                        valor = data.getFullYear() + '-' + mes+ '-' + dia;

                        if ( hora != '' ) {
                            var h = hora.split( ':' );
                            var min = h[1].substr( 0, 2 );
                            var ho = Number( h[0] );

                            if ( h[1].substr( 3, 2 ) == 'PM' )
                                ho += 12;

                            ho = ho < 10 ? '0' + ho : String( ho );

                            valor += 'T:' + ho + ':' + min;

                        }
                    }

                    campoQueChamou.setValue( valor );
                    janela.close();

                }
            }, {
                text: Application.i18n.getMsg( 'cancelar' ),
                handler: function( btn, evt ) {
                    janela.close();
                }
            }],
            listeners: {
                show: function() {

                    var cData = Ext.getCmp( 'fieldDataHoraData' );
                    var cHora = Ext.getCmp( 'fieldDataHoraHora' );

                    if ( conteudoCampo != '' ) {

                        var ind = conteudoCampo.indexOf( 'T' );

                        if ( ind == -1 ) {

                            cData.setValue( conteudoCampo );

                        } else {

                            var s = conteudoCampo.split( 'T' );
                            cData.setValue( s[0] );
                            cHora.setValue( s[1].substring( 1, s[1].length ) );

                        }

                    }

                }
            }
        });

        janela.show();
        janela.center();

    };

    /*
     * Cria a janela de intervalo para um campo.
     */
    var criarJanelaIntervalo = function( idCampoQueChamou ) {

        var campoQueChamou = Ext.getCmp( idCampoQueChamou );
        var conteudoCampo = campoQueChamou.getValue();

        var janela = new Ext.Window( {
            title: Application.i18n.getMsg( 'metadados.tipos.edtIntId' ),
            iconCls: 'iconeEdicao',
            modal: true,
            resizable: false,
            items: [{
                xtype: 'panel',
                layout: 'form',
                labelAlign: 'right',
                border: false,
                bodyStyle: 'padding: 4px;',
                labelWidth: 100,
                items: [{
                    xtype: 'numberfield',
                    id: 'fieldInterMin',
                    minValue: 0,
                    maxValue: 130,
                    minText: Application.i18n.getMsg( 'vtype.minimum' ),
                    maxText: Application.i18n.getMsg( 'vtype.maximum' ),
                    fieldLabel: Application.i18n.getMsg( 'metadados.tipos.idMin' )
                }, {
                    xtype: 'numberfield',
                    id: 'fieldInterMax',
                    minValue: 0,
                    maxValue: 130,
                    minText: Application.i18n.getMsg( 'vtype.minimum' ),
                    maxText: Application.i18n.getMsg( 'vtype.maximum' ),
                    fieldLabel: Application.i18n.getMsg( 'metadados.tipos.idMax' )
                }]
            }],
            fbar: [{
                text: Application.i18n.getMsg( 'ok' ),
                handler: function( btn, evt ) {

                    var min = Ext.getCmp( 'fieldInterMin' ).getValue();
                    var max = Ext.getCmp( 'fieldInterMax' ).getValue();

                    if ( min != '' ) {
                        min = Number( min );
                        if ( min <= 130 ) {
                            if ( max != '' ) {
                                max = Number( max );
                                if ( max <= 130 ) {
                                    if ( min <= max ) {
                                        campoQueChamou.setValue( min + '-' + max );
                                    } else {
                                        campoQueChamou.setValue( min + '-' );
                                    }
                                } else {
                                    campoQueChamou.setValue( min + '-' );
                                }
                            } else {
                                campoQueChamou.setValue( min + '-' );
                            }
                        } else {
                            campoQueChamou.setValue( '' );
                        }
                    }

                    janela.close();

                }
            }, {
                text: Application.i18n.getMsg( 'cancelar' ),
                handler: function( btn, evt ) {
                    janela.close();
                }
            }],
            listeners: {
                show: function() {

                    var s = conteudoCampo.split( '-' );

                    var min = Ext.getCmp( 'fieldInterMin' );
                    var max = Ext.getCmp( 'fieldInterMax' );

                    min.setValue( s[0] );
                    max.setValue( s[1] );

                }
            }
        });

        janela.show();
        janela.center();

    };

    /*
     * Cria a janela de duracao para um campo.
     */
    var criarJanelaDuracao = function( idCampoQueChamou ) {

        var campoQueChamou = Ext.getCmp( idCampoQueChamou );
        var conteudoCampo = campoQueChamou.getValue();

        var janela = new Ext.Window( {
            title: Application.i18n.getMsg( 'metadados.tipos.edtDur' ),
            iconCls: 'iconeEdicao',
            modal: true,
            resizable: false,
            items: [{
                xtype: 'panel',
                layout: 'form',
                labelAlign: 'right',
                border: false,
                bodyStyle: 'padding: 4px;',
                labelWidth: 160,
                items: [{
                    xtype: 'numberfield',
                    id: 'fieldDurA',
                    minValue: 0,
                    minText: Application.i18n.getMsg( 'vtype.minimum' ),
                    fieldLabel: Application.i18n.getMsg( 'metadados.tipos.qAno' ),
                    width: 60
                }, {
                    xtype: 'numberfield',
                    id: 'fieldDurM',
                    minValue: 0,
                    minText: Application.i18n.getMsg( 'vtype.minimum' ),
                    fieldLabel: Application.i18n.getMsg( 'metadados.tipos.qMes' ),
                    width: 60
                }, {
                    xtype: 'numberfield',
                    id: 'fieldDurD',
                    minValue: 0,
                    minText: Application.i18n.getMsg( 'vtype.minimum' ),
                    fieldLabel: Application.i18n.getMsg( 'metadados.tipos.qDia' ),
                    width: 60
                }, {
                    xtype: 'numberfield',
                    id: 'fieldDurH',
                    minValue: 0,
                    minText: Application.i18n.getMsg( 'vtype.minimum' ),
                    fieldLabel: Application.i18n.getMsg( 'metadados.tipos.qHora' ),
                    width: 60
                }, {
                    xtype: 'numberfield',
                    id: 'fieldDurMi',
                    minValue: 0,
                    minText: Application.i18n.getMsg( 'vtype.minimum' ),
                    fieldLabel: Application.i18n.getMsg( 'metadados.tipos.qMin' ),
                    width: 60
                }, {
                    xtype: 'numberfield',
                    id: 'fieldDurS',
                    minValue: 0,
                    minText: Application.i18n.getMsg( 'vtype.minimum' ),
                    fieldLabel: Application.i18n.getMsg( 'metadados.tipos.qSeg' ),
                    width: 60
                }]
            }],
            fbar: [{
                text: Application.i18n.getMsg( 'ok' ),
                handler: function( btn, evt ) {

                    var ano = Ext.getCmp( 'fieldDurA' ).getValue();
                    var mes = Ext.getCmp( 'fieldDurM' ).getValue();
                    var dia = Ext.getCmp( 'fieldDurD' ).getValue();
                    var hora = Ext.getCmp( 'fieldDurH' ).getValue();
                    var min = Ext.getCmp( 'fieldDurMi' ).getValue();
                    var seg = Ext.getCmp( 'fieldDurS' ).getValue();
                    var jaP = false;
                    var jaT = false;
                    var valor = 'P';

                    if ( ano != '' ) {
                        valor += ano + 'Y'
                    }
                    if ( mes != '' ) {
                        valor += mes + 'M'
                    }
                    if ( dia != '' ) {
                        valor += dia + 'D'
                    }

                    var vT = '';

                    if ( hora != '' ) {
                        jaT = true;
                        vT += hora + 'H'
                    }
                    if ( min != '' ) {
                        jaT = true;
                        vT += min + 'M'
                    }
                    if ( seg != '' ) {
                        jaT = true;
                        vT += seg + 'S'
                    }
                    if ( jaT )
                        vT = 'T' + vT;

                    valor += vT;

                    if ( valor.length == 1 )
                        campoQueChamou.setValue( '' );
                    else
                        campoQueChamou.setValue( valor );

                    janela.close();

                }
            }, {
                text: Application.i18n.getMsg( 'cancelar' ),
                handler: function( btn, evt ) {
                    janela.close();
                }
            }],
            listeners: {
                show: function() {

                    if ( conteudoCampo != '' ) {

                        var map = [];
                        var array = conteudoCampo.split( '' );
                        var achouT = false;

                        for ( var i = 1; i < array.length; i++ ) {

                            var v = '';

                            if ( array[i] == 'T' )
                                achouT = true;
                            
                            while ( !isNaN( Number( array[ i ] ) ) ) {
                                v += array[ i ];
                                i++;
                            }

                            var k = array[i];

                            if ( achouT )
                                k = 'T' + k;

                            map[ k ] = v;

                        }
                        
                        Ext.getCmp( 'fieldDurA' ).setValue( map[ 'Y' ] );
                        Ext.getCmp( 'fieldDurM' ).setValue( map[ 'M' ] );
                        Ext.getCmp( 'fieldDurD' ).setValue( map[ 'D' ] );
                        Ext.getCmp( 'fieldDurH' ).setValue( map[ 'TH' ] );
                        Ext.getCmp( 'fieldDurMi' ).setValue( map[ 'TM' ] );
                        Ext.getCmp( 'fieldDurS' ).setValue( map[ 'TS' ] );

                    }

                }
            }
        });

        janela.show();
        janela.center();

    };

    /*
     * Função para carregar de forma síncrona os nós da árvore de OAs.
     */
    var carregarArvore = function( idMaterial ) {

        var noGrupos = new Ext.tree.TreeNode({
            leaf: false,
            text: Application.i18n.getMsg( 'objetos.grp' ),
            icon: Application.contextPath + '/imagens/icones/book_add.png',
            tipo: 'grupos'
        });

        var noPaginas = new Ext.tree.TreeNode({
            leaf: false,
            text: Application.i18n.getMsg( 'objetos.pag' ),
            tipo: 'paginas'
        });

        var noImagens = new Ext.tree.TreeNode({
            leaf: false,
            text: Application.i18n.getMsg( 'objetos.img' ),
            icon: Application.contextPath + '/imagens/icones/picture.png',
            tipo: 'imagens'
        });

        var noVideos = new Ext.tree.TreeNode({
            leaf: false,
            text: Application.i18n.getMsg( 'objetos.vid' ),
            icon: Application.contextPath + '/imagens/icones/film.png',
            tipo: 'videos'
        });

        var noSons = new Ext.tree.TreeNode({
            leaf: false,
            text: Application.i18n.getMsg( 'objetos.sons' ),
            icon: Application.contextPath + '/imagens/icones/music.png',
            tipo: 'sons'
        });

        var categorias = [
            noGrupos,
            noPaginas,
            noImagens,
            noVideos,
            noSons
        ];

        // obtém os OAs
        $.ajax({

            dataType: 'json',
            type: 'post',
            asynch: false,
            cache: false,
            url: Application.contextPath + '/ajax/materiais/oasMaterialPorId.jsp',
            data: {
                id: idMaterial
            },
            success: function( data, textStatus ) {
                if ( data.success ) {

                    var oas = data.oas;

                    for ( var i = 0; i < oas.length; i++ ) {

                        var no = new Ext.tree.TreeNode({
                            leaf: true,
                            idInterno: oas[i].idInterno,
                            text: oas[i].text,
                            icon: oas[i].icon,
                            tipo: oas[i].tipo
                        });

                        switch ( oas[i].tipo ) {

                            case 'grupo':
                                noGrupos.appendChild( no );
                                break;

                            case 'pagina':
                                noPaginas.appendChild( no );
                                break;

                            case 'imagem':
                                noImagens.appendChild( no );
                                break;

                            case 'video':
                                noVideos.appendChild( no );
                                break;

                            case 'som':
                                noSons.appendChild( no );
                                break;

                        }

                    }

                    for ( i = 0; i < categorias.length; i++ ) {
                        if ( categorias[i].childNodes.length > 0 )
                            arvoreOAs.getRootNode().appendChild( categorias[i] );
                    }

                    arvoreOAs.expandAll();

                }
            }

        });

    };

    /*
     * Função para ligar/desligar a interface de metadados
     */
    var ligarDesligarInterface = function( ligar ) {

        if ( ligar == Global.LIGAR ) {
            painelDados.enable();
            Ext.getCmp( 'btnMetadadosSalvar' ).enable();
        } else {
            painelDados.disable();
            Ext.getCmp( 'btnMetadadosSalvar' ).disable();
        }

    };

    /*
     * Abre a janela de metadados, a qual é reaproveitada a cada chamada.
     *
     * estruturaMaterial é a referência para a árvore do material.
     */
    Metadados.abrir = function( extr ) {

        // armazena a referência para a estrutura do material
        estruturaMaterial = extr;

        // prepara a árvore
        arvoreOAs.setRootNode(new Ext.tree.TreeNode({
            leaf: false,
            text: estruturaMaterial.getRootNode().text,
            icon: Application.contextPath + '/imagens/icones/book_open.png',
            tipo: 'material',
            idInterno: estruturaMaterial.getRootNode().attributes.idInterno
        }));

        // carrega a estrutura da árvore de OAs
        carregarArvore( estruturaMaterial.getRootNode().attributes.idInterno );

        janelaMetadados.show();
        janelaMetadados.center();

        // se algo ainda estiver selecionado, limpa
        arvoreOAs.getSelectionModel().clearSelections();

        arvoreOAs.disable();
        ligarDesligarInterface( Global.DESLIGAR );

        if ( registrarToolTips ) {

            // abre cada aba para as tooltips serem registradas corretamente
            abaLifeCycle.setVisible( true );
            abaMetaMetadata.setVisible( true );
            abaTechnical.setVisible( true );
            abaEducational.setVisible( true );
            abaRights.setVisible( true );
            abaRelation.setVisible( true );
            abaAnnotation.setVisible( true );
            abaClassification.setVisible( true );
            abaGeneral.setVisible( true );

            var titulo = Application.i18n.getMsg( 'metadados.tt.titulo' );

            /*
             * Registro das Tool tips
             */
            Ext.QuickTips.register({
                target: 'comboGenCat',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.gen.cCat' )
            }, {
                target: 'fieldGenEntry',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.gen.fEntry' )
            }, {
                target: 'fieldGenTitulo',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.gen.fTit' )
            }, {
                target: 'comboGenLang',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.gen.cLang' )
            }, {
                target: 'areaGenDesc',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.gen.aDesc' )
            }, {
                target: 'fieldPcGen',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.gen.fPc' )
            }, {
                target: 'areaGenCob',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.gen.aCob' )
            }, {
                target: 'comboGenEstruct',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.gen.cEst' )
            }, {
                target: 'comboGenAgLevel',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.gen.cAgLevel' )
            }, {
                target: 'fieldLCVer',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.lc.fVer' )
            }, {
                target: 'comboLCStat',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.lc.cStat' )
            }, {
                target: 'comboLCRole',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.lc.cRole' )
            }, {
                target: 'fieldLCEnt',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.lc.fEnt' )
            }, {
                target: 'fieldLCDate',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.lc.fDate' )
            }, {
                target: 'areaLCDesc',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.lc.fDesc' )
            }, {
                target: 'comboMtCat',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.mt.cCat' )
            }, {
                target: 'fieldMtEntry',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.mt.fEntry' )
            }, {
                target: 'comboMtRole',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.mt.cRole' )
            }, {
                target: 'fieldMtEnt',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.mt.fEnt' )
            }, {
                target: 'fieldMtDate',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.mt.fDate' )
            }, {
                target: 'areaMtDesc',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.mt.aDesc' )
            }, {
                target: 'fieldMtSch',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.mt.fSch' )
            }, {
                target: 'comboMtLang',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.mt.cLang' )
            }, {
                target: 'fieldTcFor',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.tc.fFor' )
            }, {
                target: 'fieldTcSize',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.tc.fSiz' )
            }, {
                target: 'areaTcLoc',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.tc.aLoc' )
            }, {
                target: 'comboTcType',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.tc.cTyp' )
            }, {
                target: 'comboTcName',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.tc.cNam' )
            }, {
                target: 'fieldTcMinVer',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.tc.fMin' )
            }, {
                target: 'fieldTcMaxVer',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.tc.fMax' )
            }, {
                target: 'areaTcInstRem',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.tc.aIns' )
            }, {
                target: 'areaTcOthInst',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.tc.aOth' )
            }, {
                target: 'fieldTcDur',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.tc.fDur' )
            }, {
                target: 'areaTcDesc',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.tc.aDes' )
            }, {
                target: 'comboEdTi',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.ed.cTi' )
            }, {
                target: 'comboEdTr',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.ed.cTr' )
            }, {
                target: 'comboEdNi',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.ed.cNi' )
            }, {
                target: 'comboEdDs',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.ed.cDs' )
            }, {
                target: 'comboEdPd',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.ed.cPd' )
            }, {
                target: 'comboEdCon',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.ed.cCon' )
            }, {
                target: 'fieldEdTar',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.ed.fTar' )
            }, {
                target: 'comboEdDi',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.ed.cDi' )
            }, {
                target: 'fieldEdDur',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.ed.fDur' )
            }, {
                target: 'areaEdDurDesc',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.ed.aDurDesc' )
            }, {
                target: 'areaEdDesc',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.ed.aDesc' )
            }, {
                target: 'comboEdLang',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.ed.cLang' )
            }, {
                target: 'comboRigCost',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.ri.cCos' )
            }, {
                target: 'comboRigCop',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.ri.cCop' )
            }, {
                target: 'areaRigDesc',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.ri.aDes' )
            }, {
                target: 'comboReKin',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.re.cKin' )
            }, {
                target: 'comboReCat',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.re.cCat' )
            }, {
                target: 'fieldReEntry',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.re.fEnt' )
            }, {
                target: 'areaReDesc',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.re.aDes' )
            }, {
                target: 'fieldAnEnt',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.an.fEnt' )
            }, {
                target: 'fieldAnDate',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.an.fDate' )
            }, {
                target: 'areaAnDateDesc',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.an.fDateDesc' )
            }, {
                target: 'areaAnDesc',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.an.aDesc' )
            }, {
                target: 'comboClPurp',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.cl.cPurp' )
            }, {
                target: 'fieldClTaxSrc',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.cl.fTaxSrc' )
            }, {
                target: 'fieldClTaxTaxonId',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.cl.fTaxonId' )
            }, {
                target: 'fieldClTaxTaxonEntry',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.cl.fTaxonEnt' )
            }, {
                target: 'areaClDesc',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.cl.aDesc' )
            }, {
                target: 'fieldClKey',
                title: titulo,
                text: Application.i18n.getMsg( 'metadados.tt.cl.aKey' )
            });
            
            registrarToolTips = false;

        }

        arvoreOAs.expandAll();
        abaGeneral.setVisible( true );
        noAnterior = null;

        arvoreOAs.enable();

    };

};