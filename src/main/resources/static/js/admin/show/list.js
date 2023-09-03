$(function(){
	$("#selectStatus").change(function(){
		var frm = $("[name='formStatus']")
		frm.attr("method", "get");
		frm.attr("action", "./show");
		frm.submit();
	});
	$(`option[value='${selected}']`).attr("selected", "selected");

	$('[show-del-btn]').click(function(){
		let answer = confirm("삭제하시겠습니까?");
		if(answer){
			$("[name='showInfoId']").val($(this).attr('show-del-btn'));
			$("[name='frmDel']").submit();	
		}
		
	})

});