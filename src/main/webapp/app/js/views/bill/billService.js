define(function(require,exports,module){
	var BillService = {
		createBill: function(bill,success,error){
			$.ajax({
				cache : true,
				type : "POST",
				url : "rest/bill/createBill",
				data : bill,
				dataType : 'json',
				async : true,
				error : function(request) {
					alert("Connection error:" + request.error);
				},
				success : function(data) {
					if(success) success(data);
					if(data.success){
						alert("SUCCESS!");
					}else{
						alert("ERROR!");
					}
				}
			});
		},
		createCategory: function(category,success,error){
			$.ajax({
				cache : true,
				type : "POST",
				url : "rest/user/createCategory",
				data : category,
				dataType : 'json',
				async : true,
				error : function(request) {
					alert("Connection error:" + request.error);
				},
				success : function(data) {
					if(success) success(data);
					if(data.success){
						alert("SUCCESS!");
					}else{
						alert("ERROR!");
					}
				}
			});
		},
		queryBills: function(query,success,error){
			$.ajax({
				cache : true,
				type : "POST",
				url : "rest/bill/queryBills",
				contentType: "application/json",
				data : JSON.stringify(query),
				dataType : 'json',
				async : true,
				error : function(request) {
					alert("Connection error:" + request.error);
				},
				success : function(data) {
					if(success) success(data);
					if(data.success){
						console.info("queryBills SUCCESS!");
					}else{
						alert("ERROR!");
					}
				}
			});
		},
		getReport: function(report,success,error){
			$.ajax({
				cache : true,
				type : "POST",
				url : "rest/bill/getReport",
				contentType: "application/json",
				data : JSON.stringify(report),
				dataType : 'json',
				async : true,
				error : function(request) {
					alert("Connection error:" + request.error);
				},
				success : function(data) {
					if(success) success(data);
				}
			});
		},
		getCategorys: function(category,success,error){
			$.ajax({
				cache : true,
				type : "POST",
				url : "rest/bill/getCategorys",
				data : category,
				dataType : 'json',
				async : true,
				error : function(request) {
					alert("Connection error:" + request.error);
				},
				success : function(data) {
					if(success) success(data);
				}
			});
		}
	};
	
	module.exports = BillService;
});