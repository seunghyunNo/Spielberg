$(function(){
	$.ajax({
		url: "/adminRest/movieList",
		type: 'post',
		cache: false,
		success: function(data, status, xhr){
			if(status == "success"){
				loadMovieSelect(data.data);
			}
		}
	});

	fetch('/adminRest/cinemaList', {method: 'post'})
		.then(response => response.json())
		.then(data => data.cinemaList)
		.then(data => {
			data.forEach(element => {
				$("#selectCinema").append(`<option value="${element.id}">${element.name}</option>`)	
			})
		});
	
	$("#selectCinema").change(function(){
		let cinemaId = $(this).val();
		$('#selectTheater').empty();
		$.ajax({
			url: "/adminRest/theaterList",
			type: 'post',
			cache: false,
			data: {"cinemaId": cinemaId},
			success: function(data, status){
				if(status == "success"){
					
					data.theaterList.forEach(element => {
						$('#selectTheater').append(`<option value="${element.id}">${element.theaterNum}관</option>`)
					});
				}
			}
		})
	});

	$('#registerBtn').click(function(){
		var frm = $("[name='showForm']");
		frm.attr('method', 'post');
		frm.attr('action', 'register');
		let showDateTime = "";
		showDateTime += $('#datePicker').val() + 'T';	
		showDateTime += $('#inputHour').val() + ':';
		let minute = $('#inputMinute').val();
		if(minute < 10){
			showDateTime += '0';
		}
		showDateTime += minute;
		$('#resultDateTime').val(showDateTime);
		frm.submit();
	});

});

function loadMovieSelect(data){
	data.forEach(element => {
		$("#selectMovie").append(`<option value="${element.id}">#${element.id} : ${element.title}</option>`);
	});
}