Ext.ns('Ext.i18n');
/**
 * 
 * @class Ext.i18n.PropertyReader
 * @constructor
 * @param config.propertySeparator {String}. The String that is used as separator between key and value. Default " ". Optional
 */
Ext.i18n.PropertyReader = function(config){
    config = config || {};
    Ext.apply(this, config);
	
    var meta = config.meta || {};
    var recordType = config.recordType || {};
	
    //call super
    //for ExtJS 3.0 we need to define a meta object and recordType and pass them thru constructor. weird.
    Ext.i18n.PropertyReader.superclass.constructor.call(this, {
        meta:meta,
        recordType: recordType
    });
	
}

Ext.extend(Ext.i18n.PropertyReader, Ext.data.DataReader,{
    /**
     * @cfg propertySeparator: {String}. Default: "=". Optional
     */
    propertySeparator: "=",

    read: function(response){
        var propertyFile = response.responseText;
        if(!propertyFile)
            throw {
                message: "PropertyReader.read: File not found"
            };
						
        return this.readRecords(propertyFile);
    },
	
    readRecords: function(propertyFile){
        var totalRecords = 0, success = true;
        var records = [];
		
        var f = this.readLines(propertyFile);
		
        for(var i = 0; i < f.length; i++){
            var key, value;
            kLim = f[i].indexOf(this.propertySeparator);
            key = String(f[i].substring(0, kLim));
            //value = eval(f[i].substring(kLim+1));
            value = f[i].substring(kLim+1);
			
            var record = new Ext.data.Record(value, key);
            records[i] = record;
        }
		
		
        return {
            success : success,
            records : records,
            totalRecords : f.length || totalRecords
        };
	
    },
	
    //private
    readLines: function(file){
        var aux = String(file).split('\n');
        var lines = new Array();
		
        for(var i = 0; i < aux.length; i++){
            if(aux[i].indexOf("#") < 0 || (aux[i].indexOf("#") < aux[i].indexOf("\""))){
                line = Ext.util.Format.trim(aux[i]);
                if(line.length > 0 )
                    lines.push(Ext.util.Format.trim(aux[i]));
            }
        }
        return lines;
    }

});