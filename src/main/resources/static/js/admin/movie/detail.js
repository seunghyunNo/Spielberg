$(function(){
	$("#btnDel").click(function(){
		let answer = confirm("삭제하시겠습니까?");
		if(answer){
			const frmDelete = $("form[name='frmMovieId']");
			frmDelete.attr("method", "POST");
			frmDelete.attr("action", "../delete");
			frmDelete.submit();
		}
	});
});