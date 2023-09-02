$(function () {
  $("#reviewTag").removeClass("active");
  $("#movieDetail").removeClass("active");
  $("#trailerTag").addClass("active");
  getMovieDetail(apiKey, movieTitle);
  $("#showMoreButton").hide();
  $("#closeButton").hide();
});

function getMovieDetail(apiKey, movieTitle){
  fetch(`https://api.themoviedb.org/3/search/movie?api_key=${apiKey}&query=${movieTitle}`)
    .then((response) => response.json())
    .then((data) => {
      // 해당 movie 정보 가져오기
      const movie = data.results[0];

      // 해당 movie 정보에서 movie id 와 포스터 경로 가져오기
      const movieId = movie.id;

      getVideo(movieId, apiKey);
      getStillCut(movieId, apiKey);
    })
    .catch((error) => {
      console.error("Error searching for movie:", error);
    });
}

// 영화 트레일러 가져오기
function getVideo(movieId, apiKey){
  fetch(`https://api.themoviedb.org/3/movie/${movieId}/videos?api_key=${apiKey}&language=ko-KR`)
    .then((response) => response.json())
    .then((videosData) => {

      const videos = videosData.results.filter((video) => video.iso_639_1 === "ko");
      const videoContainer = $(".videos");
      const prevBtn = $(".prev-btn");
      const nextBtn = $(".next-btn");

      videos.reverse();
      let currentIndex = 0;

      // 비디오 추가 함수
      function addVideo(video) {
        const videoKey = video.key;
        const videoName = video.name;

        const videoUrl = `https://www.youtube.com/embed/${videoKey}`;
        const videoElement = `
          <div class="video d-flex flex-column align-items-center">
            <iframe class="text-center" width="560" height="315" src="${videoUrl}" frameborder="0" allowfullscreen></iframe>
            <h5 class="text-center mt-3"><${videoName}></h5>
          </div>
        `;

        videoContainer.append(videoElement);
      }
      // 비디오 초기화 및 첫 번째 비디오 추가
      videoContainer.empty();
      addVideo(videos[currentIndex]);

      // 이전 버튼 클릭 이벤트
      prevBtn.click(function () {
        currentIndex = (currentIndex - 1 + videos.length) % videos.length;
        videoContainer.empty();
        addVideo(videos[currentIndex]);
      });

      // 다음 버튼 클릭 이벤트
      nextBtn.click(function () {
        currentIndex = (currentIndex + 1) % videos.length;
        videoContainer.empty();
        addVideo(videos[currentIndex]);
      });

    })
    .catch((error) => {
      console.error("Error fetching videos:", error);
    });
}

function getStillCut(movieId, apiKey){
  fetch(`https://api.themoviedb.org/3/movie/${movieId}/images?api_key=${apiKey}`)
    .then((response) => response.json())
    .then((data) => {

      // 이미지 정보 가져오기
      const images = data.backdrops;
      const imageLimit = 2;

      function addImages(startIndex, endIndex){
        for(let i = startIndex; i < endIndex; i++){
          const image = images[i];

          if(image.file_path){
            const imageBox = $("<div>").addClass("stillCutImage col-md-5 m-3");
            const StillCutImage = $("<img>").attr("src", `https://image.tmdb.org/t/p/w500${image.file_path}`);
            imageBox.append(StillCutImage);
            $("#stillCut").append(imageBox);
          }
        }
      }

      $("#showMoreButton").show();
      addImages(0, imageLimit);

      $("#showMoreButton").click(function() {
        addImages(imageLimit, images.length);
        $("#showMoreButton").hide();
        $("#closeButton").show();
      });

      $("#closeButton").click(function(){
        if(images.length > imageLimit){
          $(".stillCutImage:gt(" + (imageLimit - 1) + ")").remove();
          $("#showMoreButton").show();
          $("#closeButton").hide();
        }
      });

    })
    .catch((error) => {
      console.error("Error fetching img:", error);
    });
}