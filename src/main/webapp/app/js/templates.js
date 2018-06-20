define(function(require, exports, module) {
	var templates = {
		"bill.search":require("tpl/bill/search.html"),
		"bill.info":require("tpl/bill/info.html"),
		"bill.list":require("tpl/bill/list.html"),
	 	"bill.report":require("tpl/bill/report.html")
	};
	module.exports = templates;
})