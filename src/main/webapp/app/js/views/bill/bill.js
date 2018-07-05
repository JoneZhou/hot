define(function(require,exports,module){
	var Utils = require('utils');
	var BillService = require('views/bill/billService');
	
	var BillView = Backbone.View.extend({
		events: {
		    "change #j_bill-type"			: "renderCategory",
		    "click .j_submitBill"			: "submitBill",
		    "click .j_submitCategory"		: "submitCategory",
		    "click .j_getBillPage"			: "doSearch",
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
				this.doSearch();
			} else if(type=="report"){
				this.getBillReport();
			} else {
				this.getBillInfo();
				this.renderCategory();
			}
		},
		submitBill: function() {
			BillService.createBill($('#create_bill').serialize());
		},
		submitCategory: function() {
			BillService.createCategory($('#create_category').serialize());
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
			view.doSearch(view.conditions);
		},
		nextPage: function(event) {
			var $obj = $(event.currentTarget);
			if($obj.hasClass('disabled')) {
				return;
			}
			var view = this;
			view.pageNo++;
			view.doSearch(view.conditions);
		},
		getBillInfo: function() {
			var view = this;
			view.el = Utils.template("bill.info");
			view.$el.html(view.el);
		},
		getBillReport: function() {
			var view = this;
			var report = {
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
			BillService.getReport(report,function(data) {
				if(data.success){
					var ths = [];
					$.each(report.groupFields,function(index,item){
						ths.push(["gf_"+item.fieldId,item.fieldName])
					});
					$.each(report.statisticsFields,function(index,item){
						ths.push([item.statisticsType+"_"+item.fieldId,item.fieldName])
					});
					var reportEl = Utils.template("bill.report",{
						"ths":ths,
						"list":data.data
					});
					view.$el.html(reportEl);
					
					Utils.mergeTable(view.$el.find('table')[0],1,2);
					
					var myChart = echarts.init(view.$el.find('#main')[0]);
					var option = {
						    tooltip: {
						        trigger: 'axis',
						        axisPointer: {
						            type: 'cross',
						            crossStyle: {
						                color: '#f00'
						            }
						        }
						    },
						    toolbox: {
						        feature: {
						            dataView: {}
						        }
						    },
						    legend: {
						    	type:'scroll',
						        data:[]
						    },
						    xAxis: [
						        {
						            type: 'category',
						            data: [],
						            axisPointer: {
						                type: 'shadow'
						            }
						        },
						         {
						            type: 'category',
						            data: [],
						            axisTick:{show:true},
						            axisPointer: {
						                type: 'line'
						            },
						        }
						    ],
						    yAxis: [
						        {
						            type: 'value'
						        }
						    ],
						    dataZoom: [
						            
						            {
						                type: 'slider',
						                start: 0,
						                end: 100,
						                xAxisIndex:0,
						                handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
						            }/*,
						            {
						                type: 'inside',
						                start: 0,
						                end: 100,
						                xAxisIndex:0
						            }*/
						        ],
						    series: []
						};
					var xAxis1Data = [];
					
					
					$.each(data.data,function(i,item){
						var flag = false;
						if(option.legend.data.indexOf(item[ths[2][0]]) == -1){
							option.legend.data.push(item[ths[2][0]]);
						}
						if(xAxis1Data.indexOf(item[ths[1][0]]) == -1){
							xAxis1Data.push(item[ths[1][0]]);
						}
						if(option.xAxis[0].data.indexOf(item[ths[0][0]]) == -1){
							option.xAxis[0].data.push(item[ths[0][0]]);
						}
					});
					$.each(option.legend.data,function(i,legend){
						var seriesItem = {
								name:legend,
								type:'bar',
								data:[],
								stack:'',
								xAxisIndex:0,
								label:{show:true/*,formatter: '{a}: {c}'*/}
						};
						option.series.push(seriesItem);
					});
					$.each(option.xAxis[0].data,function(i,xAxis0){
						option.xAxis[1].data.add(xAxis1Data);
						$.each(option.series,function(j,serie){
							if(data.data.length > 0){
								$.each(data.data,function(k,item){
									if(item[ths[0][0]] == xAxis0 && item[ths[2][0]] == serie.name){
										serie.data[i] = item[ths[3][0]];
										serie.stack = item[ths[1][0]];
										data.data.remove(item);
										return false
									}
								});
							} else {
								return false
							}
						});
					});
					myChart.setOption(option);
				}else{
					alert("ERROR!");
				}
			});
		},
		renderCategory: function($form){
			var view = this;
			$form = view.$el.find('#create_bill');
			var type = $form.find('select[name="type"]').val();
			var category = {'type':type};
			BillService.getCategorys(category,function(data) {
				if(data.success){
					$form.find('select[name="category"]').html('');
					data.data.forEach(function(i){
						$form.find('select[name="category"]').append('<option value="'+i.id+'">'+i.name+'</option>');
					});
				}else{
					alert("ERROR!");
				}
			});
		},
		doSearch: function(conditions) {
			var view = this;
			view.conditions = conditions;
			var query = {
				"pageNo": view.pageNo, 
				"pageSize": view.pageSize,
				"conditions": view.conditions,
				"orderFields": [
				                {
				                	"fieldId": "create_time", 
				                	"orderType": "desc"
				                }
				                ]
			};
			BillService.queryBills(query,function(data) {
				if(data.success){
					view.el = Utils.template("bill.list",{
						page:data.data
					});
					view.$el.html(view.el);
				}else{
					alert("ERROR!");
				}
			});
		},
		getTypeReport: function() {
			var view = this;
			var report = {
				    "name": null, 
				    "entity": "bill", 
				    "groupFields": [
//						{
//							"fieldName":"用户",
//							"fieldId": "user",
//							"fieldShow": "username"
//						},
						{
				        	"fieldName":"日期",
				            "fieldId": "create_time", 
				            "fieldType": "Date", 
				            "dateGroupType": "month"
				        }, 
//				        {
//				        	"fieldName":"类型",
//				        	"fieldId": "type"
//				        },
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
//				        {
//				            "fieldId": "gf_type", 
//				            "orderType": "asc"
//				        }, 
//				        {
//				            "fieldId": "sum_money", 
//				            "orderType": "desc"
//				        }
				    ]
				};
			BillService.getReport(report,function(data) {
				if(data.success){
					var ths = [];
					$.each(report.groupFields,function(index,item){
						ths.push(["gf_"+item.fieldId,item.fieldName])
					});
					$.each(report.statisticsFields,function(index,item){
						ths.push([item.statisticsType+"_"+item.fieldId,item.fieldName])
					});
					var reportEl = Utils.template("bill.report",{
						"ths":ths,
						"list":data.data
					});
					view.$el.html(reportEl);
					
					Utils.mergeTable(view.$el.find('table')[0],1,3);
				}
			});
		},
					
		remove: function(){
			
		}
	});
	
	module.exports = BillView;
});