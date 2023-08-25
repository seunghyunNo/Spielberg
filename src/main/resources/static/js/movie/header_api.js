$(function () {
  const apiKey = "25930c0ce8107734d85ee82311f96b0d";
  const movieTitle = "범죄도시";

  getMovie(apiKey, movieTitle);
});

function getMovie(apiKey, movieTitle){
  fetch(`https://api.themoviedb.org/3/search/movie?api_key=${apiKey}&query=${movieTitle}&language=ko-KR`)
    .then((response) => response.json())
    .then((data) => {
      // 해당 movie 정보 가져오기
      const movie = data.results[0];

      // 해당 movie 정보에서 movie id 와 포스터 경로 가져오기
      const movieId = movie.id;
      const posterPath = movie.poster_path;

      // 포스터 경로 전달
      const posterUrl = `https://image.tmdb.org/t/p/w500${posterPath}`;

      // 이미지 포스터 url 전달
      var movieImg = document.getElementById("moviePoster");
      movieImg.src = posterUrl;
    })
    .catch((error) => {
      console.error("Error searching for movie:", error);
    });
}