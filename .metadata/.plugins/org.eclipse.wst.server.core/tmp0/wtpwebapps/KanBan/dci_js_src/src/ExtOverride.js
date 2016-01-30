Ext.define('Ext.grid.PanelOverride', {
	override : 'Ext.grid.Panel',
	initComponent : function() {
		var showHorScrollBar = function() {
			try {
				if (typeof this.getView() !== 'undefined' && this.getView() != null) {
					var tt = this.getView().getEl().getById(this.getView().getId() + "-table");
					var tb = this.getView().getEl().getById(this.getView().getId() + "-body");
					if (tt != null && typeof tt !== 'undefined' && tb != null && typeof tb !== 'undefined') {
						if (tb.getHeight() == 0 && tt.getHeight() == 0) {
							var bname = get_browser();
							var bversion = get_browser_version();
							tt.setStyle("height", "1px");
							try {
								if (bname == 'MSIE') {
									if (bversion == '7.0' || bversion == '8.0') {
										tb.setHeight(1);
										tb.insertHtml("afterBegin", "<tr sytle='height:1px;'></tr>", false);
									}
								} else {
									tb.setHTML("<tr sytle='height:1px;'></tr>");
								}
							} catch (err) {
							}
						}
					}
				}
			} catch (err) {
				// alert(err);
				this.setLoading(false);
			}

			// alert(this.getStore().getCount());
		};
		this.on('afterlayout', showHorScrollBar, this);
		this.callParent(arguments);
	}
});

/*
 * Ext.define('Ext.buttonOverride', { override : 'Ext.Button', initComponent :
 * function() { var isSessionAlive = function() { var sessionok = false; var
 * docheck = true; // alert(docheck + "---" + this.checkSession); if (typeof
 * this.checkSession !== 'undefined') { docheck = this.checkSession; } if
 * (docheck) { var checkSessionStore = new Ext.data.Store({ fields : [ "result" ],
 * autoLoad : false, proxy : { type : 'ajax', url :
 * '/KanBan/FrontSessionCheck.dsc', actionMethods : { read : 'POST' }, reader : {
 * type : 'json' } } });
 * 
 * checkSessionStore.load(function(records) { if (records == null) { sessionok =
 * false; } else { if (records.length == 1) { if (records[0].get("result") ==
 * "1") { sessionok = true; } else { sessionok = false; } } else { sessionok =
 * false; } } // alert(sessionok); if (!sessionok) {
 * location.replace('/LKB/ErrorPage.jsp?errcode=dcie01'); } }); } };
 * this.on('click', isSessionAlive, this); } });
 */

/*
 * Ext.define('Override.Ext.data.Store', { override : 'Ext.data.proxy.Proxy',
 * timeout : 300000 });
 */

Ext.define('Override.Ext.data.Store', {
	override : 'Ext.data.Store',
	constructor : function(config) {
		this.callParent(arguments);
		var proxy = this.getProxy();
		if (proxy != null) {
			proxy.on("exception", function(proxy, response, operation, eOpts) {
				// console.log(response.responseText);
				if (response.responseText.indexOf('@dcifiltererrtag@') != -1) {
					var result = response.responseText.split('$');
					if (result.length >= 2) {
						// console.log(result[1]);
						var resultdata = Ext.JSON.decode(result[1]);
						// console.log(resultdata.msg);
						Ext.Msg.alert("error", resultdata.msg, function() {
							iserror = true;
							window.location = resultdata.result;
						});
					}
				}
			});
		}
	}
});

Ext.define('DCI.Override.ComboBox', {
	override : 'Ext.form.field.ComboBox',
	listeners : {
		resize : function(combo, width, height, oldWidth, oldHeight, eOpts) {
			if (combo.listConfig) {
				combo.listConfig["minWidth"] = width - combo.labelWidth;
			} else {
				combo.listConfig = {
					minWidth : width - combo.labelWidth
				};
			}
		}
	},
	initComponent : function() {
		this.callParent(arguments);
		var me = this;
		if (me.listConfig) {
			me.listConfig["minWidth"] = me.width - me.labelWidth;
		} else {
			me.listConfig = {
				minWidth : me.width - me.labelWidth
			};
		}
	}
});

Ext.override(Ext.view.Table, {
	focusRow : function(rowIdx) {
		var me = this, row, gridCollapsed = me.ownerCt && me.ownerCt.collapsed, record;
		if (me.isVisible(true) && !gridCollapsed && (row = me.getNode(rowIdx, true)) && me.el) {
			record = me.getRecord(row);
			rowIdx = me.indexInStore(row);

			me.selModel.setLastFocused(record);
			if (!Ext.isIE) {
				row.focus();
			}
			me.focusedRow = row;
			me.fireEvent('rowfocus', record, row, rowIdx);
		}
	}
});



Ext.define('Override.Ext.grid.column.Column', {
	override : 'Ext.grid.column.Column',
	resizeTimes : 0,
	resizeByCode : false,
	renderer : function(value, metaData, record, row, col, store, view, column) {
		if (column) {
			var dataidx = column.dataIndex;

			if (record.data.hasOwnProperty(dataidx + "_dpvalue1")) {
				if (record.get(dataidx + "_dpvalue1") && record.get(dataidx + "_dpvalue1") != "") {
					metaData.style += "color:#" + record.get(dataidx + "_dpvalue1") + "!important;";
				}
			}
			if (record.data.hasOwnProperty(dataidx + "_dpvalue2")) {
				if (record.get(dataidx + "_dpvalue2") && record.get(dataidx + "_dpvalue2") != "") {
					metaData.style += "background-color:#" + record.get(dataidx + "_dpvalue2") + "!important;";
					metaData.tdAttr += "style='background-color:#" + record.get(dataidx + "_dpvalue2") + "!important;'";
				}
			}
		}
		return value;
	},
	listeners : {
		resize : function(column, width, height, oldWidth, oldHeight, eOpts) {
			var me = this;
			if (me.ispivotsubcolumn && me.resizeTimes > 0 && !me.resizeByCode) {

				var grid = me.up().up().up();
				var cols = null;
				var colName = column.colid.split("$")[1];
				if (grid) {
					cols = grid.headerCt.getGridColumns();
					Ext.suspendLayouts();
					for ( var i = 0; i < cols.length; i++) {
						if (me.colid != cols[i].colid) {
							if (cols[i].colid.indexOf("$" + colName) != -1) {
								if (cols[i].getWidth() != width) {
									cols[i].resizeByCode = true;
									cols[i].setWidth(width);
								}
							}
						}
					}
					Ext.resumeLayouts(true);
				}
			}
			if (me.resizeTimes == 0) {
				me.resizeTimes++;
			}
		},
		afterlayout : function(column, layout, eOpts) {
			this.resizeByCode = false;
		}
	}
});

Ext.define('Override.Ext.view.Table', {
	override : 'Ext.view.Table',
	headerRowCellTpl : [ '<td class="dci-row-header {tdCls}" {tdAttr}>', '<div {unselectableAttr} class="' + Ext.baseCSSPrefix + 'grid-cell-inner"',
			'style="text-align:{align};<tpl if="style">{style}</tpl>">{value}</div>', '</td>', {
				priority : 0
			} ],
	initFeatures : function(grid) {
		var me = this, i, features, feature, len;
		me.tableTpl = Ext.XTemplate.getTpl(this, 'tableTpl');
		me.rowTpl = Ext.XTemplate.getTpl(this, 'rowTpl');
		me.cellTpl = Ext.XTemplate.getTpl(this, 'cellTpl');
		me.headerRowCellTpl = Ext.XTemplate.getTpl(this, 'headerRowCellTpl');
		me.featuresMC = new Ext.util.MixedCollection();
		features = me.features = me.constructFeatures();
		len = features ? features.length : 0;
		for (i = 0; i < len; i++) {
			feature = features[i];

			feature.view = me;
			feature.grid = grid;
			me.featuresMC.add(feature);
			feature.init(grid);
		}
	},
	renderCell : function(column, record, recordIndex, columnIndex, out) {
		var me = this, selModel = me.selModel;
		var cellValues = me.cellValues, classes = cellValues.classes;
		var fieldValue = record.data[column.dataIndex];
		var cellTpl = me.cellTpl, headerRowCellTpl = me.headerRowCellTpl, value, clsInsertPoint;

		cellValues.record = record;
		cellValues.column = column;
		cellValues.recordIndex = recordIndex;
		cellValues.columnIndex = columnIndex;
		cellValues.cellIndex = columnIndex;
		cellValues.align = column.align;
		cellValues.tdCls = column.tdCls;
		cellValues.style = cellValues.tdAttr = "";
		cellValues.unselectableAttr = me.enableTextSelection ? '' : 'unselectable="on"';
		if (record.data[column.dataIndex + "#rows"]) {
			if (record.data[column.dataIndex + "#rows"] != '' && record.data[column.dataIndex + "#rows"] > 1) {
				cellValues.rows = record.data[column.dataIndex + "#rows"];
			}
		}

		if (column.renderer && column.renderer.call) {
			value = column.renderer.call(column.scope || me.ownerCt, fieldValue, cellValues, record, recordIndex, columnIndex, me.dataSource, me, column);
			if (cellValues.css) {
				record.cssWarning = true;
				cellValues.tdCls += ' ' + cellValues.css;
				delete cellValues.css;
			}
		} else {
			value = fieldValue;
		} //console.log(column);
		cellValues.value = (value == null || value === '') ? '&#160;' : value;

		classes[1] = Ext.baseCSSPrefix + 'grid-cell-' + column.getItemId();

		clsInsertPoint = 2;

		if (column.tdCls) {
			classes[clsInsertPoint++] = column.tdCls;
		}
		if (me.markDirty && record.isModified(column.dataIndex)) {
			classes[clsInsertPoint++] = me.dirtyCls;
		}
		if (column.isFirstVisible) {
			classes[clsInsertPoint++] = me.firstCls;
		}
		if (column.isLastVisible) {
			classes[clsInsertPoint++] = me.lastCls;
		}
		if (!me.enableTextSelection) {
			classes[clsInsertPoint++] = Ext.baseCSSPrefix + 'unselectable';
		}

		classes[clsInsertPoint++] = cellValues.tdCls;
		if (selModel && selModel.isCellSelected && selModel.isCellSelected(me, recordIndex, columnIndex)) {
			classes[clsInsertPoint++] = (me.selectedCellCls);
		}

		classes.length = clsInsertPoint;

		cellValues.tdCls = classes.join(' ');
		if (column.headerRow) {
			headerRowCellTpl.applyOut(cellValues, out);
		} else {
			cellTpl.applyOut(cellValues, out);
		}

		cellValues.column = null;
	}
});