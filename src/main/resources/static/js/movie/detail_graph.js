$(function () {
  $("#reviewTag").removeClass("active");
  $("#movieDetail").addClass("active");
  $("#trailerTag").removeClass("active");

  // 일별 관객수
  var audiPerDayData = JSON.parse(document.getElementById("audiPerDay").getAttribute("dataAudiPerDay"));
  var labels = []; // 최근 5일에 해당하는 라벨

  var today = new Date(); // 오늘 날짜
  for (var i = 5; i >= 1; i--) {
    var date = new Date(today);
    date.setDate(today.getDate() - i); // 오늘로부터 -1일, -2일, ... 로 계산

    var day = date.getDate();
    var month = date.getMonth() + 1; // 월은 0부터 시작하므로 1을 더해줌

    labels.push(month + "월 " + day + "일"); // 라벨에 추가
  }

  var ageCtx = document.getElementById("perDayChart").getContext("2d");
  var ageChartConfig = {
    type: "bar",
    data: {
      labels: labels,
      datasets: [
        {
          label: "최근 5일간 일별 관객수",
          data: audiPerDayData,
          borderWidth: 1,
          backgroundColor: "rgba(235, 224, 255, 1)",
          borderColor: "rgba(235, 224, 255, 1)",
        },
      ],
    },
    options: {
      scales: {
       xAxes: [{
         gridLines: { //A축 gridLines 지우는 옵션
           display: false,
           drawBorder: false,
         },
         ticks: {
           fontSize: 13,
           fontColor: 'black'
         }
       }],
       yAxes: [{
         gridLines: { //Y축 gridLines 지우는 옵션
           drawBorder: false,
           display: false,
         },
         ticks: {
           beginAtZero: true,
           fontSize: 10,
           fontColor: 'lightgrey',
           maxTicksLimit: 5,
           padding: 25,
         }
       }]
     },
      // 위에 라벨 없애기
      legend: {
        display: false,
      },
    },
  };
  var ageChart = new Chart(ageCtx, ageChartConfig);

  // 성별별 예매율 그래프 생성
  var genderData = JSON.parse(document.getElementById("genderRateData").getAttribute("dataGenderRate"));
  var totalGender = genderData[0] + genderData[1];
  var genderRateData =  [ Math.floor(genderData[0] / totalGender * 100) , Math.floor(genderData[1] / totalGender * 100)]
  var genderCtx = document.getElementById("genderChart").getContext("2d");
  var genderPieChart = new Chart(genderCtx, {
    type: "doughnut",
    data: {
      labels: ["남성", "여성"],
      datasets: [
        {
          data: genderRateData,
          backgroundColor: ["rgba(215, 236, 251, 1)", "rgba(255, 224, 230, 1)"],
          borderColor: ["rgba(215, 236, 251, 1)", "rgba(255, 224, 230, 1)"],
        },
      ],
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          position: "bottom",
        },
      },
      // 위에 라벨 없애기
      legend: {
        display: false,
      },
    },
  });

  // 연령별 예매율 그래프 생성
  var ageData = JSON.parse(document.getElementById("ageRateData").getAttribute("dataAgeRate"));
  var totalAge = ageData[0] + ageData[1] + ageData[2]+ ageData[3]+ ageData[4]
  var ageRateData = [ Math.floor(ageData[0] / totalAge * 100),
                      Math.floor(ageData[1] / totalAge * 100),
                      Math.floor(ageData[2] / totalAge * 100),
                      Math.floor(ageData[3] / totalAge * 100),
                      Math.floor(ageData[4] / totalAge * 100) ]

  var ageCtx = document.getElementById("ageChart").getContext("2d");
  var ageChartConfig = {
    type: "bar",
    data: {
      labels: ["10대", "20대", "30대", "40대", "50대"],
      datasets: [
        {
          label: "연령별 예매 분포",
          data: ageRateData,
          borderWidth: 1,
          backgroundColor: "rgba(255, 245, 221, 1)",
          borderColor: "rgba(255, 245, 221, 1)",
        },
      ],
    },
    options: {
      scales: {
        xAxes: [{
          gridLines: { //A축 gridLines 지우는 옵션
            display: false,
            drawBorder: false,
          },
          ticks: {
            fontSize: 13,
            fontColor: 'black'
          }
        }],
        yAxes: [{
          gridLines: { //Y축 gridLines 지우는 옵션
            drawBorder: false,
            display: false,
          },
          ticks: {
            beginAtZero: true,
            fontSize: 10,
            fontColor: 'lightgrey',
            maxTicksLimit: 5,
            padding: 25,
          }
        }]
      },
      // 위에 라벨 없애기
      legend: {
        display: false,
      },
    },
  };
  var ageChart = new Chart(ageCtx, ageChartConfig);
});
