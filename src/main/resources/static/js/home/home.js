$(function() {
       var opendate = new Date($('#movieopdate').text());
       var today = new Date();

       if (today < opendate) {
            $('.movieopen').text("상영예정작");
       } else {
            $('.movieopen').text("상영중");
       }

       $('#moviepostercnt1').attr('class','carousel-item active');

});