define(function(require,exports,module){
	var Page = require('core/page');
	var Utils = require('utils');
	var BillSearch = require('views/bill/search');
	var BillView = require('views/bill/bill');
	var SearchView = require('views/bill/search');
	
	var BillPage = Page.extend({
		events: {
		    
		},
		initialize : function(options) {
			this.el = 'body';
			this.pageKey = "billPage";
			this.type = options.type;
			console.info(this.cid + ".initialize:" +this.type);
			
		},
		
		render: function() {
			var view = this;
			view.searchView = new SearchView({
				$el: $("body .searchContainer"),
				type: view.type,
				page: view
			});

			view.billView = new BillView({
				$el: $("body .mainContainer"),
				type: view.type
			});
		},
		update: function() {
			var view = this;
			console.info(this.cid + ".update");
			if(view.billView){
				view.billView.render(view.type);
			}
			if(view.searchView){
				view.searchView.update(view.type);
			}
		},
		remove: function() {
			this.$el.remove();
			this.stopListening();
			var view = this;
			if(view.billView != null) {
				console.info("billView remove");
				view.billView.remove();
			}
			if(view.searchView != null) {
				console.info("searchView remove");
				view.searchView.remove();
			}
			console.info(view.cid + ".remove");
		}
	});

	module.exports = BillPage;
});