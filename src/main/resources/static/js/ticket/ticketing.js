$(function () {
    const showInfoId = $("#showInfoId").val();
    const userId = $("#userId").val();
    var adultNum = 0;
    var studentNum = 0;
    var people = 0;
    $("#adult").change(function () {
        adultNum = parseInt($(this).val());
        people += adultNum;
        console.log(adultNum+"명");
    });

    $("#student").change(function () {
        studentNum = parseInt($(this).val());
        people += studentNum;
        console.log(studentNum+"명");
    });

    var cnt = 0;
//    alert(showInfoId);
    // 처음 예약 좌석 / 미예약 좌석 확인

    // 예약된 좌석이면 버튼 클릭시 경고출력

    $("[data-column-row]").click(function () {
        let row = $(this).siblings("span").attr("data-seat-row");
        let column = $(this).parent().siblings("div").attr("data-seat-column");
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
            success: function (data) {
                if (data.count == 1) {
                    crnObj.removeClass("selectSeat");
                    crnObj.addClass("seat");
                    cnt--;
                    deleteSeat(column, row);
                }
                else {
                    crnObj.removeClass("seat");
                    crnObj.addClass("selectSeat");
                    cnt++;
                    console.log("카운트"+cnt);
                    saveSeat(column, row, showInfoId);
                }
            },
        });

    });
    $("#purchaseBtn").click(function(){
        console.log("카운트"+cnt);
        console.log("인원수"+people);
        if(cnt == people)
        {
            $("[name='ticketingFrm']").submit();
        }
        else
        {
            alert("선택하신 인원수 만큼 좌석을 선택하십시오.");
        }

    });

});


function saveSeat(seatColumn, seatRow, showInfoId) {
    $.ajax({
        url: "/seat/save",
        type: "POST",
        cache: false,
        data: {
            "seatRow": seatRow,
            "seatColumn": seatColumn,
            "showInfoId": showInfoId,
            "userId": "1",
        },
        success: function (data) {

        },

    });
}

function deleteSeat(seatColumn, seatRow) {
    $.ajax({
        url: "/seat/delete",
        type: "POST",
        cache: false,
        data: {
            "seatRow": seatRow,
            "seatColumn": seatColumn,
        },
        success: function (data) {

        },

    });
}
