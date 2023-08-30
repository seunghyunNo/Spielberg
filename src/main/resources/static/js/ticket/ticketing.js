$(function(){
	const showInfoId = $("#showInfoId").val();
	const userId = $("#userId").val();
	const priceId = $("#priceId").val();
	alert(showInfoId);
	// 처음 예약 좌석 / 미예약 좌석 확인

	// 예약된 좌석이면 버튼 클릭시 경고출력

	// 가격 책정

	checkSeat();

});
function checkSeat()
{
	$("[data-column-row]").click(function(){
            let row = $(this).siblings("span").attr("data-seat-row");
            let column = $(this).siblings("div").attr("data-seat-column");
            let crnObj = $(this);
            console.log(row);
            console.log(column);
            $.ajax({
                url: "/seat/check",
                type: "POST",
                cache: false,
                data: {
                    "seatRow": row,
                    "seatColumn": column
                },
                success: function(data)
                {
                    if(data.count==1)
                    {
					    crnObj.removeClass("selectSeat");
						crnObj.addClass("seat");
                        deleteSeat(column,row);
                    }
                    else
                    {
					    crnObj.removeClass("seat");
						crnObj.addClass("selectSeat");
                        saveSeat(column,row);
                    }
                },
            });

        });
}

function saveSeat(seatColumn,seatRow)
{
	$.ajax({
		url: "/seat/save",
		type: "POST",
		cache: false,
		data: {
			"seatRow": seatRow,
			"seatColumn": seatColumn,
			"showInfoId": showInfoId,
			"userId": "1",
			"priceId": priceId,
		}

	});
}

function deleteSeat(seatColumn,seatRow)
{

}
