/*
 * XXX API for Ext JS
 * Copyright(c) 2009, YNHENC
 * bkkim@ynhenc.co.kr
 * http://www.ynhenc.co.kr
 *
 * License: XXX
 *
 * Version: 0.1
 *
 */

Ext.namespace('Ext.ux');

/**
 *
 * @class YSearchPanel
 * @extends Ext.Panel
 */
Ext.ux.YSearchPanel = Ext.extend(Ext.Panel, {

  initComponent: function(){	
  	// call parent initComponent
    Ext.ux.YSearchPanel.superclass.initComponent.call(this);
  }, // end of function initComponent
  
  onRender: function(){
  }, // end of function onRender
  
  getSearchPanel: function(){
  	
  	// Map Object
  	var mapObj = null;
  	
		var startX = new Ext.form.TextField({
			fieldLabel: 'Start X',
			fieldClass: 'x-form-field',
			name: 'sx',
			value: '127.061054',
			anchor: '100%'
		});  	
		
		var startY = new Ext.form.TextField({
			fieldLabel: 'Start Y',
			fieldClass: 'x-form-field',
			name: 'sy',
			value: '37.4392602',
			anchor: '100%'
		});  	
		
		var endX = new Ext.form.TextField({
			fieldLabel: 'End X',
			fieldClass: 'x-form-field',
			name: 'ex',
			value: '127.046032',
			anchor: '100%'
		});  	
		
		var endY = new Ext.form.TextField({
			fieldLabel: 'End Y',
			fieldClass: 'x-form-field',
			name: 'ey',
			value: '37.6528058',
			anchor: '100%'
		});  	
		
		var submitButton = new Ext.Button({
				text: 'Search',
				formBind: true,
				handler: function(){
					
					if(searchPanel.getForm().isValid()){
						/*
						searchPanel.getForm().submit({	
							
							url: 'http://localhost:8080/PR170_ROUTE/jsp/v1.9.0/route/P001_SearchPath.jsp?sx=127.061054&sy=37.4392602&ex=127.046032&ey=37.6528058',
							//url: 'http://localhost:8080/PR170_ROUTE/jsp/v1.9.0/route/P001_SearchPath.jsp',
							waitMsg: 'Route Search',
							success: function(searchPanel, o){ msg('success', 'route search is success'); }
						});
						*/
						
						//var params = '?sx=127.061054&sy=37.4392602&ex=127.046032&ey=37.6528058';
						var params = '?sx=' + startX.getValue() + '&sy=' + startY.getValue() + '&ex=' + endX.getValue() + '&ey=' + endY.getValue();
						var url = 'http://localhost:8080/PR170_ROUTE/goodroute/dynamic/jsp/v1.9.0/route/P001_SearchPath.jsp'; + params;
						
						url = url + params;
						earthPanel.fetchKml(url);
						
					}
					
				}
			});
  	
  	var searchPanel = new Ext.FormPanel({
			title: 'Search Route',
			url: 'http://localhost:8080/PR170_ROUTE/jsp/v1.9.0/route/P001_SearchPath.jsp',
			defaultType: 'textfield',
			buttonAlign: 'center',
			items: [startX, startY, endX, endY],
			buttons: [submitButton]
		});
		
		return searchPanel;
  } // end of function getSearchPanel
  
});

// register xtype
Ext.reg('ysearchpanel', Ext.ux.YSearchPanel);

// end of file