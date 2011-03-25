Ext.ns('Ext.i18n');

/**
 * @class Ext.18n.Bundle
 * @constructor
 * @param config.bundle: {String} 
 * @param config.path: {String}
 * @param config.lang: {String}
 * 	
 */
Ext.i18n.Bundle = function(config){
    this.bundle = config.bundle;
    this.path = config.path;
    this.language = config.lang || this.guessLanguage();
    this.resourceExt = config.resourceExt;
    var url;
    if(this.path)
        url = this.path + '/';
    url+=this.bundle+'_'+this.language+this.resourceExt;
	
    Ext.i18n.Bundle.superclass.constructor.call(this, {
        autoLoad: true,
        proxy: new Ext.data.HttpProxy({
            url: url,
            method: 'POST'
        }),
        reader: new Ext.i18n.PropertyReader()
    });

    this.on('loadexception', this.loadParent);
};

Ext.extend(Ext.i18n.Bundle, Ext.data.Store,{ 
    defaultLanguage: 'en-US',
    loadFlag: false,
    resourceExt: '.properties',
    bundle: '',
    path: null,
	
    //private
    guessLanguage: function(){
        return (navigator.language || navigator.browserLanguage
            || navigator.userLanguage || this.defaultLanguage);
    },
	
    getMsg: function(key) {
        return this.getById(key)? Ext.util.Format.htmlDecode(this.getById(key).data) : key + ' undefined';
    },
	
    onReady: function(fn){
        this.readyFn = fn;
        this.on('load', this.readyFn);
    },
	
    loadParent: function(){
        if(!this.loadFlag){
            this.loadFlag=true;
            var url;
            if(this.path)
                url = this.path + '/';
            url+=this.bundle+this.resourceExt;
            this.proxy = new Ext.data.HttpProxy({
                url: url,
                method: 'POST'
            });
            this.load();
        }else{
            throw {
                message: 'Resource Bundle not found'
            };
        }
    }

});

