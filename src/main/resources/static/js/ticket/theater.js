$(function(){
    let movieId = $("#movieId").val();
    let name;
    alert(typeof(movieId));
	$("#cinemaName").change(function(){
        name = $(this).val();
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
                    dateS.append(`<option value="${date}">${date}<option>`);
                }

                for(var x = 1; x <= data.length; x++)
                {
                    let index = x+1;


                    $(`select[name=date] option:eq(${index})`).remove();
                }

            },
        });
	});

	$("#date").change(function(){
	    let day = $(this).val();
	    let timeS = $("#time");
	    timeS.empty();
	    timeS.append(`<option selected>시간 선택</option>`);

	    $.ajax({
                url: "/ticketSelect/selectTime",
                type: "POST",
                cache: false,
                data: {
                    "cinemaName": name,
                    "movieId": movieId,
                    "day": day,
                },
                success:function(data)
                {
                    alert(data.length);
                    console.log(data);
                    for(var i = 0; i < data.length; i++)
                    {
                        let time = data[i].trim();
                        console.log(i);
                        timeS.append(`<option value="${time}">${time}<option>`);
                    }

                    for(var x = 1; x <= data.length; x++)
                    {
                        let index = x + 1;
                        $(`select[name=time] option:eq(${index})`).remove();
                    }

                },
            });

	})


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

