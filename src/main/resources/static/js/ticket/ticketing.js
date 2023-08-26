$(function () {
    const showInfoId = $("#showInfoId").val();
    const userId = $("#userId").val();
    var adultNum;
    var studentNum;
    $("#adult").change(function () {
        adultNum = parseInt($(this).val());
        console.log(adultNum);
    });

    $("#student").change(function () {
        studentNum = parseInt($(this).val());
        console.log(studentNum);
    });

    var cnt = 0;
    var people = adultNum + studentNum;
    alert(showInfoId);
    // 처음 예약 좌석 / 미예약 좌석 확인

    // 예약된 좌석이면 버튼 클릭시 경고출력

    // 가격 책정

    // 남은것 인원수 == 자리수 확인   가격 측정 price처리 

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
                    saveSeat(column, row, showInfoId);
                }
            },
        });

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
