$(function () {
    var showInfoId = $("#showInfoId").val();
    var userId = "1";
    var adultNum = 0;
    var studentNum = 0;
    var people = 0;
    $("#adult").change(function () {
        adultNum = parseInt($(this).val());
        people += adultNum;
        console.log(adultNum + "명");
    });

    $("#student").change(function () {
        studentNum = parseInt($(this).val());
        people += studentNum;
        console.log(studentNum + "명");
    });

    var cnt = 0;
    //    alert(showInfoId);
    // 처음 예약 좌석 / 미예약 좌석 확인

    // 예약된 좌석이면 버튼 클릭시 경고출력

    let nowColumn;
    $("input").siblings("div").each(function () {
        nowColumn = $(this).attr("data-seat-column");
        $(this).find("p").each(function () {
            let nowRow = $(this).attr("data-seat-row");
            const current = $(this).siblings("button");
            console.log(nowColumn + "열" + nowRow + "행");
            $.ajax({
                url: "/seat/check",
                type: "POST",
                cache: false,
                data: {
                    "seatRow": nowRow,
                    "seatColumn": nowColumn,
                    "showInfoId": showInfoId,
                    "userId": "1",
                },
                success: function (data) {
                    if (data.count == 1) {
                        current.removeClass("seat");
                        current.addClass("selectSeat");
                        current.attr("disabled",true);
                    }
                    else {

                    }
                },
            });
        });
    });


    // 좌석 클릭 시
    $("[data-column-row]").click(function () {
        let row = $(this).siblings("p").attr("data-seat-row");
        let column = $(this).parents("div").attr("data-seat-column");
        let writeRow = $(this).siblings("input");
        let writeColumn = $(this).parent().siblings("input");
        let crnObj = $(this);
        console.log(row);
        console.log(column);
        $.ajax({
            url: "/seat/check",
            type: "POST",
            cache: false,
            data: {
                "seatRow": row,
                "seatColumn": column,
                "showInfoId": showInfoId,
                "userId": userId,
            },
            success: function (data) {
                if (data.count == 1) {
                    crnObj.removeClass("selectSeat");
                    crnObj.addClass("seat");
                    cnt--;
                    deleteSeat(column, row,showInfoId,userId);
                }
                else {
                    crnObj.removeClass("seat");
                    crnObj.addClass("selectSeat");
                    cnt++;
                    console.log("카운트" + cnt);
                    writeRow.val(row);
                    writeColumn.val(column);
                    console.log(writeRow.val()+writeColumn.val());
                }
            },
        });

    });
    $("#purchaseBtn").click(function () {
        console.log("카운트" + cnt);
        console.log("인원수" + people);
        if (cnt == people) {
            $("[name='ticketingFrm']").submit();
        }
        else {
            alert("선택하신 인원수 만큼 좌석을 선택하십시오.");
        }

    });

});

//
//function saveSeat(seatColumn, seatRow, showInfoId,userId) {
//    $.ajax({
//        url: "/seat/save",
//        type: "POST",
//        cache: false,
//        data: {
//            "seatRow": seatRow,
//            "seatColumn": seatColumn,
//            "showInfoId": showInfoId,
//            "userId": userId,
//        },
//        success: function (data) {
//
//        },
//
//    });
//}
//
function deleteSeat(seatColumn, seatRow,showInfoId,userId) {
    $.ajax({
        url: "/seat/delete",
        type: "POST",
        cache: false,
        data: {
            "seatRow": seatRow,
            "seatColumn": seatColumn,
            "showInfoId": showInfoId,
            "userId": userId,
        },
        success: function (data) {

        },

    });
}


