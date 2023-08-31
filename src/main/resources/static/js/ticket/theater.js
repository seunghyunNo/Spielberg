$(function(){
    let movieId = $("#movieId").val();
    alert(typeof(movieId));
	$("#cinemaName").change(function(){
        let name = $(this).val();
        let dateS = $("#date");
        dateS.empty();
        dateS.append(`<option selected>날짜 선택</option>`);
        $.ajax({
            url: "/ticketSelect/selectDate",
            type: "POST",
            cache: false,
            data: {
                "cinemaName": name,
                "movieId": movieId,
            },
            success:function(data)
            {
                alert(data.length);
                console.log(data);
                for(var i = 0; i < data.length; i++)
                {
                    let date = data[i].trim();
                    console.log(i);
                    dateS.append(`<option value="${date}">${date}<option>`);
                }

                for(var x = 0; x < data.length; x++)
                {
                    let index = 2 * (x+1);
                    $(`select[name=date] option:eq(${index})`).remove();
                }
                let lastIndex =$("select[name=date] option").length-1;
                 $(`select[name=date] option:eq(${lastIndex})`).remove();
                console.log(dateS.val());
            },
        });
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

