$('#j_bill-type').on('change',function(){
	var type = $(this).val();
	$.ajax({
		cache : true,
		type : "POST",
		url : "rest/user/getCategorys",
		data : {'type':type},
		dataType : 'json',
		async : false,
		error : function(request) {
			alert("Connection error:" + request.error);
		},
		success : function(data) {
			if(data.success){
				$('select[name="category"]').html('');
				data.data.forEach(function(i){
					$('select[name="category"]').append('<option value="'+i.id+'">'+i.name+'</option>');
				});
			}else{
				alert("ERROR!");
			}
		}
	});
});
function submitBill() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "rest/user/createBill",
		data : $('#create_bill').serialize(),
		dataType : 'json',
		async : false,
		error : function(request) {
			alert("Connection error:" + request.error);
		},
		success : function(data) {
			//alert("SUCCESS!");
			getBillPage();
		}
	});
}
function submitCategory() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "rest/user/createCategory",
		data : $('#create_category').serialize(),
		dataType : 'json',
		async : false,
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
}
function getBillPage() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "rest/user/getBillPage",
		data : {pageNo:1,pageSize:100},
		dataType : 'json',
		async : false,
		error : function(request) {
			alert("Connection error:" + request.error);
		},
		success : function(data) {
			if(data.success){
				$('.j_bill-page').html('');
				data.data.forEach(function(i){
					$('.j_bill-page').append('<li id="'+i.id+'">'+ (i.type==0?'支出':'收入') +'-'+ i.categoryObj.name + '-' + i.money+'</li>');
				});
			}else{
				alert("ERROR!");
			}
		}
	});
}
$(function(){
	getBillPage();
});