const api_key = "9675e3dbd314fe9ddffb04b28f28d4db";
let totalPage = 0;
let curPage=1;
let itemPerPage=10;
let startPage=1;
let endPage= startPage + itemPerPage - 1;
$(function(){
	fetch(`http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=${api_key}`)
		.then((response) => response.json())
		.then((data) => data.movieListResult.totCnt)
		.then(data => {
			totalPage = Math.ceil(data/itemPerPage); // 여기서 totalPage값 초기화 됨
			$("#totalPage").text(totalPage);
		});
	
	loadList(curPage, itemPerPage);
	loadPagination(curPage, startPage, endPage);

	$("#single-right").click(function(){
		curPage += 1;
		loadList();
		
		startPage = (parseInt((curPage-1)/itemPerPage) * itemPerPage)+1;
		endPage = startPage + itemPerPage - 1;
		if (endPage > totalPage) {endPage = totalPage;}
		loadPagination();
	});

	$("#single-left").click(function(){
		curPage -= 1;
		loadList();

		startPage = (parseInt((curPage-1)/itemPerPage) * itemPerPage)+1;
		endPage = startPage + itemPerPage - 1;
		if (endPage > totalPage) {endPage = totalPage;}
		loadPagination();
	});

	$("#double-right").click(function(){
		startPage += itemPerPage;
		endPage += itemPerPage;
		curPage = startPage;
		loadList();
		loadPagination();
	})

	$("#double-left").click(function(){
		startPage -= itemPerPage;
		endPage -= itemPerPage;
		curPage = endPage;
		loadList();
		loadPagination();
	})

});

function loadPagination(){
	$("ul.pagination").children(".page-num").remove();
	if (startPage == 1) {
		$("#double-left").hide();
		$("#double-right").show();
	} else if(endPage == totalPage){
		$("#double-left").show();
		$("#double-right").hide();
	} else {
		$("#double-left").show();
		$("#double-right").show();
	}
	if (curPage == 1) {
		$("#single-left").hide();
		$("#single-right").show();
	} else if (curPage == totalPage){
		$("#single-left").show();
		$("#single-right").hide();
	} else {
		$("#single-left").show();
		$("#single-right").show();
	}
	for(let i=startPage; i<=endPage; i++){
		let liClass = "page-item";
		if (i==curPage){
			liClass += " active"
		}
		$("#single-right").before(`
		<li class="${liClass} page-num" >
			<span class="page-link">${i}</span>
		</li>
		`);
	}
	numClick();
}
function numClick(){
	$(".page-num").click(function(){
		curPage = $(this).children().text();
		loadList();
		loadPagination();
	});
}

function loadList(){
	$("#movieList tbody").empty();
	fetch(`http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=${api_key}&curPage=${curPage}&itemPerPage=${itemPerPage}`)
		.then((response) => response.json())
		.then((data) => data.movieListResult)
		.then((data) => {
			$("#sourceBy").text(data.source);
			$("#movieCnt").text(data.totCnt);
			$("#curPage").text(curPage);
			return data.movieList;
		})
		.then((movieList) => {
			movieList.forEach(element => {
				$("#movieList tbody").append(buildHTML(element));
			})
			clickTr();
		});	
}

function buildHTML(movie){
	let directors = "";
	let cnt= 0;
	movie.directors.forEach(element => {
		if(cnt > 0){
			directors += ", ";
		}
		directors += element.peopleNm;
		cnt=1;
	});
	return `
		<tr>
			<td class="movieCd">${movie.movieCd}</td>
			<td>${movie.movieNm}</td>
			<td>${movie.openDt}</td>
			<td>${movie.prdtStatNm}</td>
			<td>${movie.genreAlt}</td>
			<td>${directors}</td>
		</tr>
	`;
}

function clickTr(){
	$("#movieList tr").click(function(){
		let movieCode = $(this).children(".movieCd").text();
		var frm = $("[name='movieForm']");
		$("[name='movieCode']").val(movieCode);
		frm.attr("method", "GET");
		frm.attr("action", "../register");
		frm.submit();
	});
}
