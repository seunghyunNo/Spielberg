$(function(){
	$("#cinemaName").change(function(){

	});


	$("#theaterBtn").click(function(){
	    var name = $("[name='cinemaName']").val();
	    alert(name);
	    var date = $("[name='date']").val();
	    alert(date);
	    var time = $("[name='time']").val();
        alert(time);

		$("[name='theaterFrm']").submit();
	});

});

