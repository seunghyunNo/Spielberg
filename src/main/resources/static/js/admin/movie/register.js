const api_key = "9675e3dbd314fe9ddffb04b28f28d4db";
const poster_apiKey = "25930c0ce8107734d85ee82311f96b0d";
$(function(){
	let movieCd =""
	movieCd += $("[name='movieCode']").val();
	
	if(movieCd.length == 0 || movieCd.trim().length == 0){
		console.log("none movieCd");
	}else{
		alert(movieCd);
		fetch(`http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=${api_key}&movieCd=${movieCd}`)
			.then(response => response.json())
			.then(data => data.movieInfoResult.movieInfo)
			.then(data => {
				let directors = "";
				let cnt=0;
				data.directors.forEach(element => {
					if(cnt > 0){
						directors += ",";
					}
					directors += element.peopleNm;
					cnt=1;
				});
				cnt =0;
				let actors = "";
				data.actors.forEach(element =>{
					if(cnt>0){
						actors +=",";
					}
					actors += element.peopleNm;
					cnt=1;
				});
				
				cnt=0;
				let genres="";
				data.genres.forEach(element =>{
					if(cnt>0){
						genres +=",";
					}
					genres += element.genreNm;
					cnt=1;
				});

				let openDate = ""
				openDate = data.openDt;

				$("[name='title']").val(data.movieNm);
				$("[name='directors']").val(directors);
				$("[name='actors']").val(actors);
				$("[name='showTime']").val(data.showTm);
				$("[name='openDate']").val(openDate.substring(4,6) + "/"+ openDate.substring(6,8) + "/" + openDate.substring(0,4));
				$("[name='genre']").val(genres);
				$("[name='rateGrade']").val(data["audits"][0]["watchGradeNm"]);
			});
		let movieTitle = $('#inputMovieTitle').val();
		fetch(`https://api.themoviedb.org/3/search/movie?api_key=${poster_apiKey}&query=${movieTitle}&language=ko-KR`)
			.then(response => response.json())
			.then(data => {

				const movie = data.results[0];

				const posterPath = movie.poster_path;


				// 포스터 경로 전달
				const posterUrl = `https://image.tmdb.org/t/p/w500${posterPath}`;

				$("[name='img']").val(posterUrl);
			})
			.catch((error) => {
				console.error("Error searching for movie:", error);
			});
	}

});