.$(document).ready(function(){

    // 저장된 쿠키값을 id 칸에 넣어준다. 없으면 공백으로
    var key = getCookie("key");
    $("#userId").val(key);

    if($("#userId").val() !=""){    // id 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 id가 표시된 상태라면
        $("#idSaveCheck").attr("checked",true); // id 저장하기가 클릭 된 상태로 유지
    }

    $("#idSaveCheck").change(function(){    // 아이디 저장하기에 변화가 있을 때
        if($("#idSaveCheck").is(":checked")){   // id 저장하기 체크 했을 때
            setCookie("key",$("#userId").val(),7)   // 7일 동안 쿠키 보관
        }else{  // id 저장하기 체크를 풀 때
            deleteCookie("key");
        }
    })

    // id 저장하기를 체크한 상태에서 id를 입력했을 때도 쿠기 저장을 시켜줘야됨
    $("userId").keyup(function(){
        if($("#idSaveCheck").is(":checked")){   // id 저장하기가 체크된 상태일 때
            setCookie("key",$("#userId").val(),7);  // 7일동안 아이디 정보 보관
        }
    });

});

function deleteCookie(cookieName){
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate()-1);
    document.cookie = cookieName + "="+"; expires = "+expireDate.toGMTString();
}

function getCookie(cookieName){
    cookieName = cookieName + "=";
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cookieValue = '';

    if(start != -1){
        start += cookieName.length;
        var end = cookieData.indexOf(';',start);
        if(end == -1) end = cookieData.length;
        cookieValue = cookieData.substring(start,end);
    }
    return unescape(cookieValue);
}