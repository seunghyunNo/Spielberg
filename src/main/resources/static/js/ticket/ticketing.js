$(function () {
    var showInfoId = $("#showInfoId").val();
    var userId = $("#userId").val();
    var adultNum = 0;
    var studentNum = 0;
    var people = 0;
    $("#adult").change(function () {
        adultNum = parseInt($(this).val());
    });

    $("#student").change(function () {
        studentNum = parseInt($(this).val());
    });

    var cnt = 0;
    //    alert(showInfoId);
    // 처음 예약 좌석 / 미예약 좌석 확인

    // 예약된 좌석이면 버튼 클릭시 경고출력

    let nowColumn;
    let ticketId = 0;
    $("input").siblings("div").each(function () {
        nowColumn = $(this).attr("data-seat-column");
        ticketId++;
        $(this).find("p").each(function () {
            let nowRow = $(this).attr("data-seat-row");
            const current = $(this).siblings("button");
            $.ajax({
                url: "/seat/loadSeat",
                type: "POST",
                cache: false,
                data: {
                    "seatRow": nowRow,
                    "seatColumn": nowColumn,
                    "showInfoId": showInfoId,
                },
                success: function (data) {
                    for(var i = 0; i < data.length; i++)
                    {
                        if (data[i].count == 1) {
                            current.removeClass("seat");
                            current.addClass("selectSeat");
                            current.attr("disabled",true);
                        }
                    }
                },
            });
        });
    });

    let checkRow = 0;
    let checkColumn = 0;

    // 좌석 클릭 시
    $("[data-column-row]").click(function () {
        let row = $(this).siblings("p").attr("data-seat-row");
        let column = $(this).parents("div").attr("data-seat-column");
        let writeRow = $(this).siblings("input");
        let writeColumn = $(this).parent().siblings("input");
        let crnObj = $(this);
        let maxPeople = adultNum + studentNum;

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
                    }
                    else {
                        if(checkRow == row && checkColumn == column)
                        {
                            crnObj.removeClass("selectSeat");
                            crnObj.addClass("seat");
                            cnt--;
                            checkRow = 0;
                            checkColumn = 0;
                            writeRow.val("0");
                            writeColumn.val("0");
                        }
                        else
                        {
                            if(maxPeople > cnt)
                            {
                                checkRow = row;
                                checkColumn = column;
                                crnObj.removeClass("seat");
                                crnObj.addClass("selectSeat");
                                cnt++;
                                writeRow.val(row);
                                writeColumn.val(column);
                            }

                        }
                    }
                },
            });

    });
    $("#purchaseBtn").click(function () {
        people = adultNum + studentNum;
        if (cnt == people) {
            $("[name='ticketingFrm']").submit();
        }
        else {
            alert("선택하신 인원수 만큼 좌석을 선택하십시오.");
        }

    });

});

