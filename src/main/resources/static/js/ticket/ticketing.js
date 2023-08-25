$(function(){
    $("[data-column-row]").click(function(){
        alert("버튼눌림")
        let row = $(this).siblings("span").attr("data-seat-row");
        let column = $(this).parent().siblings("span").attr("data-seat-column");
        let crnObj = $(this);
        let check = typeof(column);
        alert(check);
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
                    crnObj.removeClass("seat");
                    crnObj.addClass("selectSeat");
                    // delete
                }
                else
                {
                    crnObj.removeClass("selectSeat");
                    crnObj.addClass("seat");
                    // save
                }
            },
        });

    });

});

function saveSeat(seatColumn,seatRow)
{

}

function deleteSeat(seatColumn,seatRow)
{

}
