define(function(require,exports,module){
	var Utils = require('utils');
	
	var BillView = Backbone.View.extend({
		events: {
		    "change #j_bill-type"			: "renderCategory",
		    "click .j_submitBill"			: "submitBill",
		    "click .j_submitCategory"		: "submitCategory",
		    "click .j_getBillPage"			: "getBillPage",
		    "click .j_prevPage"				: "prevPage",
		    "click .j_nextPage"				: "nextPage"
		},
		initialize: function(options){
			this.$el = options.$el;
			this.pageNo = 1;
			this.pageSize =5;
			this.render(options.type);
		},
		render: function(type){
			var view = this;
			if(type=="list"){
				this.getBillPage();
			} else if(type=="report"){
				this.getBillReport();
			} else {
				this.getBillInfo();
				this.renderCategory();
			}
		},
		submitBill: function() {
			$.ajax({
				cache : true,
				type : "POST",
				url : "rest/bill/createBill",
				data : $('#create_bill').serialize(),
				dataType : 'json',
				async : true,
				error : function(request) {
					alert("Connection error:" + request.error);
				},
				success : function(data) {
					
				}
			});
		},
		submitCategory: function() {
			$.ajax({
				cache : true,
				type : "POST",
				url : "rest/user/createCategory",
				data : $('#create_category').serialize(),
				dataType : 'json',
				async : true,
				error : function(request) {
					alert("Connection error:" + request.error);
				},
				success : function(data) {
					if(data.success){
						alert("SUCCESS!");
					}else{
						alert("ERROR!");
					}
				}
			});
		},
		prevPage: function(event) {
			event.preventDefault();
			var $obj = $(event.currentTarget);
			if($obj.hasClass('disabled')) {
				return;
			}
			var view = this;
			view.pageNo--;
			if(view.pageNo <= 0){
				view.pageNo = 1;
			}
			view.getBillPage();
		},
		nextPage: function(event) {
			var $obj = $(event.currentTarget);
			if($obj.hasClass('disabled')) {
				return;
			}
			var view = this;
			view.pageNo++;
			view.getBillPage();
		},
		getBillInfo: function() {
			var view = this;
			view.el = Utils.template("bill.info");
			view.$el.html(view.el);
		},
		getBillPage: function() {
			var view = this;
			$.ajax({
				cache : true,
				type : "POST",
				url : "rest/bill/queryBills",
				contentType: "application/json",
				data : JSON.stringify({
					"pageNo": view.pageNo, 
					"pageSize": view.pageSize,
					"conditions": view.conditions, 
					"orderFields": [
					                {
					                	"fieldId": "create_time",
					                	"orderType": "desc"
					                }
					                ]
				}),
				dataType : 'json',
				async : true,
				error : function(request) {
					alert("Connection error:" + request.error);
				},
				success : function(data) {
					if(data.success){
						view.el = Utils.template("bill.list",{
							page:data.data
						});
						view.$el.html(view.el);
					}else{
						alert("ERROR!");
					}
				}
			});
		},
		getBillReport: function() {
			var view = this;
			var reportParam = {
				    "name": null, 
				    "entity": "bill", 
				    "groupFields": [
				        {
				        	"fieldName":"日期",
				            "fieldId": "create_time", 
				            "fieldType": "Date", 
				            "dateGroupType": "month"
				        }, 
				        {
				        	"fieldName":"类型",
				            "fieldId": "type"
				        },
				        {
				        	"fieldName":"用途",
				        	"fieldId": "category",
				        	"fieldShow": "name"
				        }
				    ], 
				    "statisticsFields": [
				        {
				        	"fieldName":"总金额",
				            "fieldId": "money", 
				            "statisticsType": "sum"
				        }, 
				        /*{
				        	"fieldName":"最大金额",
				            "fieldId": "money", 
				            "statisticsType": "max"
				        }, 
				        {
				        	"fieldName":"最小金额",
				            "fieldId": "money", 
				            "statisticsType": "min"
				        }, 
				        {
				        	"fieldName":"平均",
				            "fieldId": "money", 
				            "statisticsType": "avg"
				        }, */
				        {
				        	"fieldName":"记录数",
				            "fieldId": "id", 
				            "statisticsType": "count"
				        }
				    ], 
				    "orderFields": [
	                    {
	                    	"fieldId": "gf_create_time", 
	                    	"orderType": "asc"
	                    }, 
				        {
				            "fieldId": "gf_type", 
				            "orderType": "asc"
				        }, 
				        {
				            "fieldId": "sum_money", 
				            "orderType": "desc"
				        }
				    ]
				};
			$.ajax({
				cache : true,
				type : "POST",
				url : "rest/bill/getReport",
				contentType: "application/json",
				data : JSON.stringify(reportParam),
				dataType : 'json',
				async : true,
				error : function(request) {
					alert("Connection error:" + request.error);
				},
				success : function(data) {
					if(data.success){
						var ths = [];
						$.each(reportParam.groupFields,function(index,item){
							ths.push(["gf_"+item.fieldId,item.fieldName])
						});
						$.each(reportParam.statisticsFields,function(index,item){
							ths.push([item.statisticsType+"_"+item.fieldId,item.fieldName])
						});
						view.el = Utils.template("bill.report",{
							"ths":ths,
							"list":data.data
						});
						view.$el.html(view.el);
						var myChart = echarts.init(view.$el.find('#main')[0]);
						// 使用刚指定的配置项和数据显示图表。
				        myChart.setOption(Utils.chartOption());
					}else{
						alert("ERROR!");
					}
				}
			});
		},
		renderCategory: function($form){
			var view = this;
			if(!$form){
				$form = view.$el.find('#create_bill');
			}
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
		doSearch: function(conditions) {
			var view = this;
			view.conditions = conditions;
			$.ajax({
				cache : true,
				type : "POST",
				url : "rest/bill/queryBills",
				contentType: "application/json",
				data : JSON.stringify({
					"pageNo": view.pageNo, 
					"pageSize": view.pageSize,
					"conditions": conditions,
					"orderFields": [
					                {
					                	"fieldId": "create_time", 
					                	"orderType": "desc"
					                }
					                ]
				}),
				dataType : 'json',
				async : true,
				error : function(request) {
					alert("Connection error:" + request.error);
				},
				success : function(data) {
					if(data.success){
						view.el = Utils.template("bill.list",{
							page:data.data
						});
						view.$el.html(view.el);
					}else{
						alert("ERROR!");
					}
				}
			});
		},
		remove: function(){
			
		}
	});
	
	module.exports = BillView;
});