$(function(){
  getReview();

  // 댓글 점수 달기
  $(".star").click(function() {
    const score = parseInt($(this).attr("score"));
    $('#score').val(score);
    $(".score-star").addClass("fa-regular").removeClass("fa-solid");

    // 현재 선택한 별과 그 이전 별들을 색칠
    $(this).find(".fa-star").addClass("fa-solid").removeClass("fa-regular");
    $(this).prevAll(".star").find(".fa-star").addClass("fa-solid").removeClass("fa-regular");

    // 현재 선택한 별 다음 별들은 색칠 해제
    $(this).nextAll(".star").find(".fa-star").addClass("fa-regular").removeClass("fa-solid");

  });

  $('#submitScore').click(function(){
    const score = $("#score").val();
    const content = $("#textBox").val().trim().replaceAll('\n','<br>');

    const data = {
      "movieId": movieId,
      "score": score,
      "content": content,
      "userId": userId
    };

    $.ajax({
      url: "/movie/review/write",
      type: "POST",
      data: data,
      cache: false,
      success: function(data, status){
        if(status !== "success"){
          alert(data.status);
          return;
        }
        if(data===1){
          alert("댓글이 등록되었습니다");
          getReview();
          return;
        } else{
          alert("댓글이 등록에 실패하였습니다");
          return;
        }
      }
    })
  });

  // 추천 눌렀을때
  $(document).on('click', '.fa-thumbs-up', function() {
      const reviewId = $(this).attr('thumb-reviewId');

      findRecommend(userId, reviewId);
      getReview();
  });

  // 삭제 버튼 클릭 시 삭제 동작 수행
  $(document).on('click', '.delete', function() {
      const reviewId = $(this).attr('delete-reviewId');
      let answer = confirm("리뷰를 삭제하시겠습니까?");
      if (answer) {
          deleteReview(reviewId);
      }
  });

  // 스포일러 신고 버튼 클릭 시 신고 동작 수행
  $(document).on('click', '.report_spoiler', function() {
      const reviewId = $(this).attr('spoil-reviewId');
      findReport(userId, reviewId, "SPOILER");
  });

  // 욕.비방 신고 버튼 클릭 시 신고 동작 수행
  $(document).on('click', '.report_badword', function() {
      const reviewId = $(this).attr('badword-reviewId');
      findReport(userId, reviewId, "BADWORD");
  });
})

function getReview(){
  fetch("/movie/getReview/" + movieId)
  .then(response => {
    if (response.ok) {
      return response.json();
    } else {
      throw new Error('Network response was not ok.');
    }
  })
  .then(data => {
    buildReview(data);
  })
  .catch(error => {
    console.error('Fetch error:', error);
  });
}

function buildReview(data){
  const result = [];

  data.forEach(element => {
    let username = element.user.username;
    let score = element.score;
    const fullStar = `<i class="fa-solid fa-star"></i>`
    const emptyStar = `<i class="fa-regular fa-star"></i>`
    let screenScore = `${fullStar.repeat(score)}${emptyStar.repeat(5 - score)}`;
    let content = element.content;
    let reviewId = element.id;
    let recommendCount = element.recommendCount;

    let date = new Date(element.createdAt);
    let createTime = getTimeAgo(date);

    const delBtn = (userId !== element.user.id) ? ''
      : `<li><button class="dropdown-item delete" delete-reviewId=${reviewId}>삭제</button></li>`;

    const reportBtn = (userId === element.user.id) ? ''
      : `<li><button class="dropdown-item report_spoiler" spoil-reviewId=${reviewId}>스포일러 신고</button></li>
         <li><button class="dropdown-item report_badword" badword-reviewId=${reviewId}>욕.비방 신고</button></li>`;

    const row = `
        <div class="d-flex flex-column justify-content-center align-items-center col-md-1">
          <i class="fa-regular fa-circle-user fa-2xl"></i>
          <div class="mt-3">${username}</div>
        </div>
        <div class="col-md-11 d-flex justify-content-center card p-5 mt-3">
          <table class="card-body">
            <tr class="row">
              <td class="col-md-2 d-flex justify-content-center align-items-center">
                <div>${screenScore}</div>
              </td>
              <td class="col-md-8 d-flex align-items-center"><span>${content}</span></td>
              <td class="col-md-1 d-flex justify-content-center align-items-center">
                <span class="text-xs m-2">${recommendCount}</span>
                <i class="fa-regular fa-thumbs-up fa-xl" thumb-reviewId=${reviewId}></i>
              </td>
              <td class="col-md-1 d-flex justify-content-center align-items-center">
                <div class="dropdown">
                  <button class="btn dropdown-toggle" type="button" data-bs-toggle="dropdown" title="메뉴">
                    <i class="fa-solid fa-bars"></i>
                  </button>
                  <ul class="dropdown-menu">
                    ${delBtn}
                    ${reportBtn}
                  </ul>
                </div>
              </td>
            </tr>
          </table>
        </div>
        <div class="col-md-12 d-flex justify-content-end">
          <span class="text-xs">${createTime}</span>
        </div>
    `;
    result.push(row);
  })

  $("#reviewList").html(result.join("\n"));

}

// 시간 변환 메소드
function getTimeAgo(previousTime) {
  const currentTime = new Date();
  const timeDifference = Math.floor((currentTime - previousTime) / 1000);
  const minuteAgo = Math.floor(timeDifference / 60);
  const hourAgo = Math.floor(minuteAgo / 60);
  const dayAgo = Math.floor(hourAgo / 24);
  const monthAgo = Math.floor(dayAgo / 30);
  const yearAgo = Math.floor(monthAgo / 12);

  if (timeDifference < 60) {
    return "방금 전";
  } else if (minuteAgo < 60) {
    return `${minuteAgo}분 전`;
  } else if (hourAgo < 24) {
    return `${hourAgo}시간 전`;
  } else if (dayAgo < 7) {
    return `${dayAgo}일 전`;
  } else if(7 <= dayAgo < 30){
    return `${(Math.floor(dayAgo / 7))}주 전`;
  } else if (monthAgo < 12) {
    return `${monthAgo}달 전`;
  } else {
    return `${yearAgo}년 전`;
  }
}

// 추천 메소드
function findRecommend(userId, reviewId){

  const data = {
    "userId": userId,
    "reviewId": reviewId
  }

  $.ajax({
    url: "/movie/review/findRecommend",
    type: "POST",
    data: data,
    cache: false,
    success: function(data, status){
      if(status == "success"){
        if(data === 1){
          deleteRecommend(userId, reviewId);
        } else{
          addRecommend(userId, reviewId);
        }
      }
    }
  })
}

// 추천취소 메소드
function deleteRecommend(userId, reviewId){
  const data = {
    "userId": userId,
    "reviewId": reviewId
  }

  $.ajax({
    url: "/movie/review/deleteRecommend",
    type: "POST",
    data: data,
    cache: false,
    success: function(data, status){
      if(status == "success"){
        alert("리뷰 추천을 취소하셨습니다");
        getReview();
      }
    }
  })
}

// 추천 올리기 메소드
function addRecommend(userId, reviewId){
  const data = {
    "userId": userId,
    "reviewId": reviewId
  }

  $.ajax({
    url: "/movie/review/addRecommend",
    type: "POST",
    data: data,
    cache: false,
    success: function(data, status){
      if(status == "success"){
        alert("리뷰 추천을 하셨습니다");
        getReview();
      }
    }
  })
}

// 신고찾기
function findReport(userId, reviewId, reportType){
  const data = {
    "userId": userId,
    "reviewId": reviewId
  }

  $.ajax({
    url: "/movie/review/findReport",
    type: "POST",
    data: data,
    cache: false,
    success: function(data, status){
      if(status == "success"){
        if(data > 0){
          alert("이 리뷰에 대한 신고를 이미 하셨습니다.");
        } else{
          addReport(userId, reviewId, reportType);
        }
      }
    }
  })
}

// 신고
function addReport(userId, reviewId, reportType) {
  const data = {
    "userId": userId,
    "reviewId": reviewId,
    "reportType": reportType
  };

  $.ajax({
    url: "/movie/review/addReport",
    type: "POST",
    data: data,
    cache: false,
    success: function(data, status) {
      if (status === "success") {
        console.log(data);
        if(data.reportType === "SPOILER"){
          alert("스포일러성 리뷰를 신고하셨습니다.");
        } else {
          alert("욕.비방성 리뷰를 신고하셨습니다.");
        }
      }
    }
  });
}

// 리뷰 삭제
function deleteReview(reviewId){
  const data = {
    "reviewId": reviewId
  }

  $.ajax({
    url: "/movie/review/deleteReview",
    type: "POST",
    data: data,
    cache: false,
    success: function(data, status){
      if(status == "success"){
        if(data === 0){
          alert("리뷰를 삭제하셨습니다.");
          getReview();
        } else{
          alert("삭제할 리뷰가 없습니다.");
        }
      }
    }
  })
}

