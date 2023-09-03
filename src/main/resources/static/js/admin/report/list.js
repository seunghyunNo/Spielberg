$(function(){
	getReport();

	$("#selectReportType").change(function(){
		$selected = $('#selectReportType option:selected').val();
		
		getReport($selected);
		
	});

});

function getReport(value){
	if(value){
		fetch("/adminRest/reportList?reportType=" + value)
		.then(response => response.json())
		.then(data => {
		buildReport(data);
	});
	}else{
		fetch("/adminRest/reportList")
		.then(response => response.json())
		.then(data => {
			buildReport(data);
		});
	}
}

function buildReport(data){
	$('#reportTable tbody').empty();
	data.forEach(element => {
		$('#reportTable tbody').append(`
			<tr>
				<td>${element.id}</td>
				<td>${element.reportType}</td>
				<td>${element.reporter.id}</td>
				<td>${element.reporter.username}</td>
				<td>${element.review.id}</td>
				<td>${element.review.content}</td>
				<td>${element.review.createdAt}</td>
				<td>${element.writer.id}</td>
				<td>${element.writer.username}</td>
				<td><button class="btn btn-outline-danger" data-report-id="${element.id}">삭제</button></td>
			</tr>
		`);
	});
	$('[data-report-id]').click(function(){
		var answer = confirm('삭제하겠습니까?');
		if(answer){
			fetch("/adminRest/reportDel/" + $(this).attr('data-report-id'))
			.then(response => response.json())
			.then(data => {
				if(data.status == "OK"){
					alert("삭제 성공");
				}else{
					alert("삭제 실패");
				}
				getReport();
			})
		}
	});
}