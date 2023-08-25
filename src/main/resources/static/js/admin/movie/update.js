$(function(){
	$openDate = $("#datePicker").val();
	let result = $openDate.split('-');
	$("#datePicker").val(result[1] + '/' + result[2] + '/' + result[0]);
});