var Ws = function () {

	var handleWS = function() {
		$('.bill').on('click',function() {
			$.ajax({
                type : "post",
                cache : false,
                url : '/rest/bill/create',
                dataType : "json",
                data: {'type':'1','money':'12.35'},
                success : function(res) {
                   console.info(res);
                },
                error : function(xhr, ajaxOptions, thrownError) {
                   
                },
                async : false
			});
		});
		$('.getBills').on('click',function() {
			$.ajax({
				type : "post",
				cache : false,
				url : '/rest/bill/getBills',
				dataType : "json",
				success : function(res) {
					console.info(res);
				},
				error : function(xhr, ajaxOptions, thrownError) {
					
				},
				async : false
			});
		});
		
	}
    
    return {
        //main function to initiate the module
        init: function () {
        	
        	handleWS();
	       
        }

    };

}();