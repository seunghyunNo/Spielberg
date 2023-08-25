$(function(){
	$("#movieList tr").click(function(){
		movie_id = $(this).children(".movieId").text()
		location.href= "/admin/movie/detail/"+movie_id;
	});
});