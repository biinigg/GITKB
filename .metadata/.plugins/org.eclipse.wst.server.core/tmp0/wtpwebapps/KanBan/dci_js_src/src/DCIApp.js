var dcicodevalue = "7558BE96-996B-4a34-99FE-9AC3B1478D08";

Ext.Ajax.timeout = 120000;

Ext.define('DCI.DCIModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'frowardTagValue',
		type : 'string',
		mapping : 'frowardTagValue'
	}, {
		name : 'dcitagValue',
		type : 'string'
	}, {
		name : 'langValues'
	}, {
		name : 'langType'
	}, {
		name : 'defLangType'
	}, {
		name : 'userID'
	}, {
		name : 'currVersion'
	} ]
});

var dcistore = new Ext.data.Store({
	id : 'DCIStore',
	requires : 'DCI.DCIModel',
	model : 'DCI.DCIModel',
	// autoload : true,o
	proxy : {
		type : 'ajax',
		url : '/KanBan/DCIAppDatas.dsc',
		actionMethods : {
			read : 'POST'
		},
		reader : {
			type : 'json',
			root : 'parameters'
		},
		extraParams : {
			dcicode : dcicodevalue,
			uid : '',
			loginKey : '',
			keys : ''
		}
	},
	setUid : function(uid) {
		this.getProxy().extraParams['uid'] = uid;
	},
	setLoginKey : function(key) {
		this.getProxy().extraParams['loginKey'] = key;
	},
	setMultiLangKeys : function(keys) {
		var keystr = null;

		for ( var i = 0; i < keys.length; i++) {
			if (i == 0) {
				keystr = keys[i];
			} else {
				keystr += "," + keys[i];
			}
		}

		if (this.getProxy().extraParams.hasOwnProperty('keys')) {
			this.getProxy().extraParams['keys'] = keystr;
		}
	}
});

function setMultiLangKeys(keys) {
	var keystr = null;

	for ( var i = 0; i < keys.length; i++) {
		if (i == 0) {
			keystr = keys[i];
		} else {
			keystr += "," + keys[i];
		}
	}

	if (this.dcistore.getProxy().extraParams.hasOwnProperty('keys')) {
		this.dcistore.getProxy().extraParams['keys'] = keystr;
	}
}

var loginstore = new Ext.data.Store({
	id : 'DCIStore',
	requires : 'DCI.DCIModel',
	model : 'DCI.DCIModel',
	// autoload : true,o
	proxy : {
		type : 'ajax',
		url : '/KanBan/DCIAppDatas.dsc',
		actionMethods : {
			read : 'POST'
		},
		reader : {
			type : 'json',
			root : 'parameters'
		},
		extraParams : {
			dcicode : dcicodevalue,
			action : "login",
			keys : '',
			langtp : ''
		}
	},
	setLoginMultiLangKeys : function(keys, langtp) {
		var keystr = null;

		for ( var i = 0; i < keys.length; i++) {
			if (i == 0) {
				keystr = keys[i];
			} else {
				keystr += "," + keys[i];
			}
		}

		if (this.getProxy().extraParams.hasOwnProperty('keys')) {
			this.getProxy().extraParams['keys'] = keystr;
		}
		if (this.getProxy().extraParams.hasOwnProperty('langtp')) {
			this.getProxy().extraParams['langtp'] = langtp;
		}
	}
});

function setLoginMultiLangKeys(keys, langtp) {
	var keystr = null;

	for ( var i = 0; i < keys.length; i++) {
		if (i == 0) {
			keystr = keys[i];
		} else {
			keystr += "," + keys[i];
		}
	}

	if (this.loginstore.getProxy().extraParams.hasOwnProperty('keys')) {
		this.loginstore.getProxy().extraParams['keys'] = keystr;
	}
	if (this.loginstore.getProxy().extraParams.hasOwnProperty('langtp')) {
		this.loginstore.getProxy().extraParams['langtp'] = langtp;
	}
}
