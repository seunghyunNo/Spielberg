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
		})
	;
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
		});
	});

	$('#registerBtn').click(function(){
		$hour = $('#inputHour').val();
		$minute = $('#inputMinute').val()
		if($hour<0 || $hour>24 || $hour.trim().length == 0){
			alert("정확한 시간을 입력하세요");
			return;
		}
		if($minute<0 || $minute>60 || $hour.trim().length == 0){
			alert("정확한 분을 입력하세요");
			return;
		}
		$selectMovie = $('#selectMovie option:selected').val();
		if($selectMovie == 0){
			alert('영화를 선택해주세요');
			return;
		}
		$selectCinema = $('#selectCinema option:selected').val();
		if($selectCinema == 0){
			alert('영화관을 선택해주세요');
			return;
		}
		$selectTheater = $('#selectTheater option:selected').val();
		if($selectTheater == 0){
			alert('극장을 선택해주세요');
			return;
		}
		$datePicker = ""
		$datePicker += $('#datePicker').val();
		if($datePicker.trim().length == 0){
			alert('날짜를 입력해주세요');
			return;
		}
		$index = $datePicker.indexOf('/');
		if($index == -1 || $index != 2){
			alert('날짜 형식이 잘못되었습니다.');
			return;
		}else if($datePicker.indexOf('/', 4) != 5){
			alert('날짜 형식이 잘못되었습니다.');
			return;
		}

		var frm = $("[name='showForm']");
		frm.attr('method', 'post');
		frm.attr('action', 'register');
		let showDateTime = "";
		showDateTime += $('#datePicker').val() + 'T';
		if($hour<10){
			showDateTime +='0';
		}
		showDateTime += $hour + ':';
		if($minute < 10){
			showDateTime += '0';
		}
		showDateTime += $minute;
		$('#resultDateTime').val(showDateTime);
		frm.submit();
	});

});

function loadMovieSelect(data){
	data.forEach(element => {
		$("#selectMovie").append(`<option value="${element.id}">#${element.id} : ${element.title}</option>`);
	});
}