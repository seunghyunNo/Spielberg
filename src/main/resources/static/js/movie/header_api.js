$(function () {

  getMovie(apiKey, movieTitle);

  // 관객수 변환 함수
  formatNumber("#audiCnt");
  formatNumber("#audiNum");

  // 예매하기 나이 검증
  $("#reserve").click(function(event){
    if(birth){
      ticketReserve(event, birth);
    } else {
      event.preventDefault();
      const birthConfirmed = confirm(`로그인이 필요한 서비스입니다.\n로그인 페이지로 이동하시겠습니까?`);
      if(birthConfirmed){
        location.href = "/user/login";
      }
    }
  })

});

function getMovie(apiKey, movieTitle){
  fetch(`https://api.themoviedb.org/3/search/movie?api_key=${apiKey}&query=${movieTitle}&language=ko-KR`)
    .then((response) => response.json())
    .then((data) => {
      // 해당 movie 정보 가져오기
      const movie = data.results[0];
      let posterUrl = "https://www.freeiconspng.com/thumbs/no-image-icon/no-image-icon-1.jpg";

      // NoImage 포스터 url 전달
      var movieImg = document.getElementById("moviePoster");
      movieImg.src = posterUrl;

      // 해당 movie 정보에서 movie id 와 포스터 경로 가져오기
      const movieId = movie.id;
      const posterPath = movie.poster_path;

      // 포스터 경로 전달
      posterUrl = `https://image.tmdb.org/t/p/w500${posterPath}`;

      // 이미지 포스터 url 전달
      var movieImg = document.getElementById("moviePoster");
      movieImg.src = posterUrl;

    })
    .catch((error) => {
      console.error("Error searching for movie:", error);
    });
}

function formatNumber(id){
  let element = $(id);
  // 숫자이외의 모든문자 제거
  let number = parseInt(element.text().replace(/\D/g, ''), 10);

  if(number >= 10000){
    trimNumber = number.slice(0, -4);
    let formatNumber = parseInt(trimmedNumber, 10).toLocaleString();
    element.text(formattedNumber  + " 만");
  } else {
    let formattedNumber = number.toLocaleString();
    element.text(formattedNumber);
  }
}

function ticketReserve(event, birth){
  const grade = $("#grade").text();
  // 숫자빼고 삭제
  const intGrade = parseInt(grade.match(/\d+/)[0]);
  const age = getAge(birth);

  if(intGrade > age){
    alert(`${intGrade}세 이상만 볼 수 있습니다.`);
    event.preventDefault();
  }
}

function getAge(birth){
  // 현재 날짜 가져오기
  const currentDate = new Date();

  // Java에서 넘어온 날짜를 JavaScript Date 객체로 변환
  const parsedDateBirth = new Date(birth);

  // 나이 계산
  let age = currentDate.getFullYear() - parsedDateBirth.getFullYear();

  // 생일이 아직 지나지 않았다면 나이를 1 감소시킵니다.
  if (
    currentDate.getMonth() < parsedDateBirth.getMonth() ||
    (currentDate.getMonth() === parsedDateBirth.getMonth() &&
      currentDate.getDate() < parsedDateBirth.getDate())
  ) {
    age--;
  }

  return age;
}

