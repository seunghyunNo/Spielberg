$(function () {

  getMovieId(apiKey, movieTitle);
  $("#showMoreButton").hide();
  $("#closeButton").hide();

});

// 영화 ID 및 기본정보 가져오기
function getMovieId(apiKey, movieTitle){
  fetch(`https://api.themoviedb.org/3/search/movie?api_key=${apiKey}&query=${movieTitle}&language=ko-KR`)
  .then((response) => response.json())
  .then((data) => {
    // 해당 movie 정보 가져오기
    const movie = data.results[0];

    // 해당 movie 정보에서 movie id 와 포스터 경로 가져오기
    const movieId = movie.id;
    // movie 줄거리 가져오기
    const overview = movie.overview;

    // movie 줄거리 넣기
    $("#movieOverview").text(overview);

    getMovieDetail(apiKey, movieId);

    getMovieCredits(apiKey, movieId)
      .then((directors) => {
        const directorsObject = directors.map((director) => {
            return {
              name: director.name,
              profile_path: director.profile_path
            }
        })
        addDirectors(directorsObject);
      });

  })
  .catch((error) => {
    console.error("Error fetching movie details:", error);
  })
}

// 영화 출연진 정보 가져오기
function getMovieDetail(apiKey, movieId){
  fetch(`https://api.themoviedb.org/3/movie/${movieId}/credits?api_key=${apiKey}`)
    .then((response) => response.json())
    .then((creditsData) => {
      const castContainer = $("#cast");
      const cast = creditsData.cast;
      const actorLimit = 5;

      //출연진 추가 함수
      function addActors(startIndex, endIndex) {
        for (let i = startIndex; i < endIndex; i++) {
          const actor = cast[i];

          if(actor.profile_path){
            const actorBox = $("<div>").addClass("actor col-md-2 m-3");
            const actorImage = $("<img>").attr("src", `https://image.tmdb.org/t/p/w185${actor.profile_path}`);
            const actorName = $("<div>").text(actor.name).addClass("d-flex justify-content-center");

            actorBox.append(actorImage, actorName);
            $("#cast").append(actorBox);
          }
        }
      }

      $("#showMoreButton").show();
      addActors(0, actorLimit);

      // 더 보기 버튼 클릭 시
      $("#showMoreButton").click(function(){
        addActors(actorLimit, cast.length);
        $("#showMoreButton").hide();
        $("#closeButton").show();
      });

      $("#closeButton").click(function(){
        if(cast.length > actorLimit){
          $(".actor:gt(" + (actorLimit - 1) + ")").remove();
          $("#showMoreButton").show();
          $("#closeButton").hide();
        }
      });
    })
    .catch((error) => {
      console.error("Error fetching credits:", error);
    })
}

// 감독정보 가져오기
function getMovieCredits(apiKey, movieId) {
  return fetch(`https://api.themoviedb.org/3/movie/${movieId}/credits?api_key=${apiKey}`)
    .then((response) => response.json())
    .then((creditsData) => {
      const directors = creditsData.crew.filter((person) => person.job === "Director");
      return directors;
    })
    .catch((error) => {
      console.error("Error fetching movie credits:", error);
    });
}

// 감독 추가하기
function addDirectors(directors) {
  const directorContainer = $("#director");

  directors.forEach((director) => {

    if(director){
      const directorBox = $("<div>").addClass("col-md-2 m-3")
      const directorImage = $("<img>").attr("src", `https://image.tmdb.org/t/p/w185${director.profile_path}`).css({width: "185px", height: "278px"});
      const directorName = $("<div>").text(director.name).addClass("d-flex justify-content-center");

      directorBox.append(directorImage, directorName,);
      directorContainer.append(directorBox); // 괄호와 쉼표 확인
    }
  });
}