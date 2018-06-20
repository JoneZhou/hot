/**
 * APP主页面，用来管理Page对象
 */
define(function(require, exports, module){

	var App = Backbone.View.extend({
		initialize : function(options) {
			console.info(this.cid + ".initialize");
		},
		delegateEvents: function() {
			$('body').on('click','.nav li',function(event){
				var $obj = $(event.currentTarget);
				$obj.siblings().removeClass("active");
				$obj.addClass("active");
			});
		},
		/*
		 * forceRefresh=true 表示需要强制刷新
		 */
		render: function(page,forceRefresh){
			//第一次初始化
			if(this.lastPage==null){
				this.lastPage = page;
			//两个不同页面，需要销毁之前页面，加载新页面
			}else if( this.lastPage.pageKey != page.pageKey || forceRefresh){
				if(this.lastPage.remove){
					this.lastPage.remove();
				}
				this.lastPage = page;
			}else{//只更新page的子页面
				this.lastPage.initialize(page);
				this.lastPage.update();
				page.remove();
				page = null;
				return;
			}
			this.lastPage.render();
		},
		renderBill:function(param){
			var BillPage = require("views/bill/billPage");
			this.billPage = new BillPage({
				"type": param
			});
			this.render(this.billPage);
		}
	});

	module.exports = App;

});
