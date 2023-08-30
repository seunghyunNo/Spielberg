$(document).ready(function(){
    // 아이디 검사
    $("#username").on('keyup',function(){

           // validation 으로 error message 초기화
        if(username){
            $("#error").html;
        }

        // 아이디 글자 수
        if(username.length < 6){
            $('#username_exist').html("아이디는 6글자 이상으로 해주세요");
            $('#username_exist').css('color',"#f00");
            return;
        }

        // 아이디 중복 확인

        $.ajax({
            url: '/user/register/usernameCheck',
            type: 'POST',
            data :{username: username},
            dataType:'json',
            success:function(response){
                if(response == 1){
                    $('#username_exist').html('이미 존재하는 아이디입니다');
                    $('#username_exist').css('color','#f00');
                }else{
                    $('#username_exist').html('사용 할 수 있는 아이디입니다');
                    $('#user_exist').css('color','#f00');
                }
            },
            error:function(xhr,status,error){
                console.error(error);
            }
        });
    });

    // 비밀번호 확인란 일치 여부 확인
    $("#re_password").on('keyup',function(){
        let password = $('#password').val();
        let re_password = $('#re_password').val();

        if(password !== re_password){
            $('#password_check').html('비밀번호가 일치하지 않습니다');
            $('#password_check').css('color','#f00');
        }else{
            $('#password_check').html('');
        }
    });

   // 비밀번호 확인란이 작성된 상태에서 비밀번호란 고칠 경우
    $('#password').on('keyup',function(){
        let password = $('#password').val();
        let re_password = $('#re_password').val();

        if(re_password){
            if(password !== re_password){
                $('#password_check').html('비밀번호가 일치하지 않습니다');
                $('#password_check').css('color','#f00');
            }else{
                $('#password_check').html('');
            }
        }
    });


    $('#emailCheckBtn').click(function(){

        // 입력한 이메일
        const email= $('#email').val().trim();

        // 검증
        var emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

        if(!email){
            alert("이메일을 입력해주세요");
            $("#email").focus();
            return;
        }else if(!emailRegex.text(email)){
            alert("유효한 이메일을 입력해주세요");
            emailInput.focus();
            return;
        }

        // 메일 중복 확인

        $.ajax({
            url:'/user/register/mailCheck',
            type:'POST',
            data:{email,email},
            dataType:'json',
            success:function(response){
                if(response==1){
                    $("#email_exist").html('이미 존재하는 이메일입니다');
                    $("#email_exist").css('color','#f00');
                    $("#email").focus();
                    return;

                }else{
                    // 알림창
                    alert('인증번호를 보냈습니다');
                    $("#eamil_exist").html('');

                    // parameter 전달
                    const data={'email':email};

                    // 메일검증
                    $.ajax({
                        url:'/register/authEmail',
                        type:'POST',
                        data:JSON.stringify(data),  // 데이터를 JSON 형식으로 변환
                        contentType:'application/json', // 요청의 컨텐츠 타입을 JSON으로
                        dataType:'text',
                        success:function(response){
                            var inputElement=$("#emailCodeInput");
                            inputElement.val(response);

                            $('#emailCodeCheckBtn').unbind('click').click(emailCodeCheckBtnHandler);
                        },
                        error:function(xhr,status,error){
                            console.error(error);
                        }
                    });
                    $('#emailCodeCheck').show();
                }
            },
            error:function(xhr,status,error){
                            console.error(error);
            }
        })

    })

    // emailCodeCheckBtn 클릭 이벤트 함수

    function emailCodeCheckBtnHandler(){
           var emailCodeInput = $('#emailCodeInput').val()    // 인증번호 입력 값 가져오기
           var code = $('#code').val();     // 확인 버튼을 클릭할 때 입력한 인증번호 가져오기

           if(emailCodeInput === code){
            $('#emailCodeCheck').hide();
            $('#email').prop('readonly',true);
            $('#email_exist').html('인증에 성공하였습니다');
            $('#email_exist').css('color','#00f');
           }else{
            $('#email_exist').html("인증번호가 맞지 않습니다");
            $('#email_exist').css("color",'#f00');
           }
    }

    // 폼 제출 이벤트
    $('form').submit(function(event){
        // 이벤트 기본 동작 막기
        event.preventDefault();

        // 입력한 메일
        var emailCodeInput = $('#emailCodeInput').val();    //인증번호 입력값 가져오기
        var code = $('#code').val();        // 확인버튼을 클릭할 때 인증번호 가져오기

        if(!code|| emailCodeInput !== code){
            $('#email_exist').html('이메일 인증에 실패하였습니다');
            $('#email_exist').css('color','#f00');
            return;
        }

        // 유효성 검사 통과
        $(this).unbind('submit').submit();
    })


})