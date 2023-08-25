$(function () {
  const apiKey = "25930c0ce8107734d85ee82311f96b0d";
  const movieTitle = "범죄도시";

  // fetch API 사용
  fetch(`https://api.themoviedb.org/3/search/movie?api_key=${apiKey}&query=${movieTitle}`)
    .then((response) => response.json())
    .then((data) => {
      // 해당 movie 정보 가져오기
      const movie = data.results[0];

      // 해당 movie 정보에서 movie id 와 포스터 경로 가져오기
      const movieId = movie.id;

      // 영화 트레일러 가져오기
      fetch(`https://api.themoviedb.org/3/movie/${movieId}/videos?api_key=${apiKey}`)
        .then((response) => response.json())
        .then((videosData) => {
          // 트레일러 찾기
          const trailer = videosData.results.find((video) => video.type === "Trailer");

          if (trailer) {
            // 트레일러 키 가져오기
            const trailerKey = trailer.key;

            // 트레일러 youtube 에서 가져오기
            const trailerUrl = `https://www.youtube.com/embed/${trailerKey}`;

            // 트레일러 html 에 넣기
            document.getElementById("trailer").src = trailerUrl;
          }
        })
        .catch((error) => {
          console.error("Error fetching videos:", error);
        });
    })
    .catch((error) => {
      console.error("Error searching for movie:", error);
    });
});
