define(function(require) {
	var Setting = require('core/setting');
	var AppRouter = require('core/router');
	
	var setting = new Setting();
	window.SETTING = setting;
	
	var router = new AppRouter();
	window.ROUTER = router;

	Backbone.history.start({
		pushState : true
	});

	// 给页面中所有的a标签委托事件
	$('body').on('click', '.router', function(event) {
		event.preventDefault();
		var href = $(this).attr('href');
		if (!href || href == "#") {
			return;
		}
		router.navigate(href, {
			trigger : true
		});
	});
	
});
