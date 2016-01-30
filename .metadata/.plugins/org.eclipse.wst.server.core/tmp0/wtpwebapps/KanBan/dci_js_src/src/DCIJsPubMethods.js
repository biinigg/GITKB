function get_browser() {
	var N = navigator.appName, ua = navigator.userAgent, tem;
	var M = ua.match(/(opera|chrome|safari|firefox|msie)\/?\s*(\.?\d+(\.\d+)*)/i);
	if (M && (tem = ua.match(/version\/([\.\d]+)/i)) != null)
		M[2] = tem[1];
	M = M ? [ M[1], M[2] ] : [ N, navigator.appVersion, '-?' ];
	return M[0];
}
function get_browser_version() {
	var N = navigator.appName, ua = navigator.userAgent, tem;
	var M = ua.match(/(opera|chrome|safari|firefox|msie)\/?\s*(\.?\d+(\.\d+)*)/i);
	if (M && (tem = ua.match(/version\/([\.\d]+)/i)) != null)
		M[2] = tem[1];
	M = M ? [ M[1], M[2] ] : [ N, navigator.appVersion, '-?' ];
	return M[1];
}


function existInArray(arr, value) {
	var exist = false;
	if (arr == null || arr.length == 0) {
		exist = false;
	} else {
		for ( var i = 0; i < arr.length; i++) {
			if (arr[i] == value) {
				exist = true;
				break;
			}
		}
	}

	return exist;
}


function EvalSound(soundobj) {
  var thissound=document.getElementById(soundobj);
  thissound.play();
}



function buildColObj(cols) {
	var arr = [];

	for ( var i = 0; i < cols.length; i++) {
		arr.push({
			hidden : cols[i].isHidden(),
			width : cols[i].getWidth(),
			text : cols[i].text,
			dataIndex : cols[i].dataIndex
		});
	}

	return arr;
}

function buildHtml(cols, rows, ctype, callback) {
	var html = null;

	html = "<html><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'><body><table border='1'><tr style='background-color : lightblue;'>";
	for ( var j = 0; j < cols.length; j++) {
		// alert(cols[j].style);
		if (!cols[j].isHidden()) {
			html += "<td style='width:" + cols[j].getWidth() + "px'>" + cols[j].text + "</td>";
		}
	}
	html += "</tr>";
	for ( var i = 0; i < rows.length; i++) {
		html += "<tr>";
		for ( var j = 0; j < cols.length; j++) {
			if (!cols[j].isHidden()) {
				if (typeof rows[i].get("code_Color") === 'undefined' || rows[i].get("code_Color") == null) {
					html += "<td>";
				} else {
					if (cols[j].dataIndex == "eq_STATUS_NAME") {
						html += "<td style='background-color : " + rows[i].get("code_Color") + " ;'>";
					} else {
						html += "<td>";
					}
				}

				if (ctype == "1") {
					html += "=\"" + rows[i].get(cols[j].dataIndex) + "\"</td>";
				} else {
					if (rows[i].get(cols[j].dataIndex) == null || rows[i].get(cols[j].dataIndex).length == 0) {
						html += "&nbsp;</td>";
					} else {
						html += rows[i].get(cols[j].dataIndex) + "</td>";
					}
				}
			}
		}
		html += "</tr>";
	}
	html += "</table></body></html>";
	//callback(encodeURIComponent(html));
	callback(html);
	// return html;
}

function buildMultiLangObjct(keys, values) {
	var obj = new Object();
	for ( var i = 0; i < keys.length; i++) {
		if (values[keys[i]] == null || values[keys[i]] == "" || typeof values[keys[i]] == 'undefined') {
			obj[keys[i]] = keys[i];
		} else {
			obj[keys[i]] = values[keys[i]];
		}
	}
	return obj;
}

function setCompsReadOnly(items, readonly) {
	if (items != null) {
		for ( var i = 0; i < items.length; i++) {
			try {
				// if
				// (Ext.getClassName(items.get(i)).toLowerCase().indexOf('button')
				// == -1 &&
				// Ext.getClassName(items.get(i)).toLowerCase().indexOf('ext.img')
				// == -1
				// &&
				// Ext.getClassName(items.get(i)).toLowerCase().indexOf('ext.grid')
				// == -1) {

				if (typeof items.get(i).setReadOnly == 'function') {
					if (Ext.getClassName(items.get(i)).indexOf('DCI') != -1) {
						if (!items.get(i).canEdit) {
							items.get(i).setReadOnly(true);
						} else {
							items.get(i).setReadOnly(readonly);
						}
					} else {
						items.get(i).setReadOnly(readonly);
					}
				}
				// }
			} catch (ex) {
				if (window.console) {
					console.log(Ext.getClassName(items.get(i)) + "-" + ex.message);
				}
			}
		}
	}
}

function setPageEdit(items) {
	if (items != null) {
		for ( var i = 0; i < items.length; i++) {
			try {
				if (typeof items.get(i).setReadOnly == 'function') {
					if (Ext.getClassName(items.get(i)).indexOf('DCI') != -1) {
						if (items.get(i).ispk || !items.get(i).canEdit) {
							items.get(i).setReadOnly(true);
						} else {
							items.get(i).setReadOnly(false);
						}
					}
				}
			} catch (ex) {
				if (window.console) {
					console.log(ex.message);
				}
			}
		}
	}
}

function getComboLabel(combobox, value) {
	var found = false;
	var displayValue = "";
	if (combobox != null) {
		store = combobox.getStore();

		for ( var i = 0; i < store.getCount(); i++) {
			if (value == store.getAt(i).get("value")) {
				displayValue = store.getAt(i).get("label");
				found = true;
				break;
			}
		}
	}

	if (!found) {
		displayValue = value;
	}

	return displayValue;
}

function parseShowDate(date) {
	if (date.length == 8) {
		date = date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6);
	}
	return date;
}
