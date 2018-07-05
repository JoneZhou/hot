define(function(require, exports, module) {
	
	var App = require('core/app');
	var AppRouter = Backbone.Router.extend({
		//初始化
		initialize : function(options) {
			this.app = new App();
		},

		routes : {
			"rest/bill/bill"				: "bill",
			"rest/bill/bill;:jid"			: "bill",
			"rest/bill/bill/:param"			: "bill"
		},
		
		bill : function(param){
			this.app.renderBill(param);
		}
	});

	module.exports = AppRouter;
});
