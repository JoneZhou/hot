define(function(require,exports,module){
	var Utils = require('utils');
	
	var SearchView = Backbone.View.extend({
		events: {
		    "change #j_bill-type"			: "renderCategory",
	    	"click .j_search-bill"			: "searchBill"
		},
		initialize: function(options){
			this.$el = options.$el;
			this.page = options.page;
			this.type = options.type;
			this.render(options.type);
		},
		render: function(type){
			var view = this;
			view.el = Utils.template("bill.search",{type:type});
			view.$el.html(view.el);
			view.renderCategory();
		},
		update: function(type) {
			var view = this;
			if(type == "list") {
				view.$el.find(".j_search_bill").removeClass("hidden");
			} else {
				view.$el.find(".j_search_bill").addClass("hidden");
			}
		},
		renderCategory: function(){
			var view = this;
			var	$form = view.$el.find('#search_bill');
			var type = $form.find('select[name="type"]').val();
			$.ajax({
				cache : true,
				type : "POST",
				url : "rest/bill/getCategorys",
				data : {'type':type},
				dataType : 'json',
				async : true,
				error : function(request) {
					alert("Connection error:" + request.error);
				},
				success : function(data) {
					if(data.success){
						$form.find('select[name="category"]').html('');
						data.data.forEach(function(i){
							$form.find('select[name="category"]').append('<option value="'+i.id+'">'+i.name+'</option>');
						});
					}else{
						alert("ERROR!");
					}
				}
			});
			
		},
		searchBill: function() {
			var view = this;
			var conditions = [
								{
								    "field": {
								        "fieldId": "create_time", 
								        "fieldType": "Date"
								    }, 
								    "compareType": "range", 
								    "fieldValue": view.$el.find(".j_date-start").val() + "~" + view.$el.find(".j_date-end").val()
								},
				                {
				                    "field": {
				                        "fieldId": "type", 
				                        "fieldType": "Number"
				                    }, 
				                    "compareType": "eq", 
				                    "fieldValue": view.$el.find(".j_billType").val()
				                },
				                {
				                    "field": {
				                        "fieldId": "category", 
				                        "fieldType": "Number"
				                    }, 
				                    "compareType": "eq", 
				                    "fieldValue": view.$el.find(".j_category").val()
				                },
				                {
				                    "field": {
				                        "fieldId": "description", 
				                        "fieldType": "Text"
				                    }, 
				                    "compareType": "like", 
				                    "fieldValue": view.$el.find(".j_description").val()
				                }
				                ];
			view.page.billView.doSearch(conditions);
		},
		remove: function(){
			
		}
	});
	
	module.exports = SearchView;
});