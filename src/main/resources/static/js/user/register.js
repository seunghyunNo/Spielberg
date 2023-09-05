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

    $('#phoneCheckBtn').click(function(){
        // 입력한 핸드폰 번호
        const phoneNum = $('#phoneNum').val().trim();

        var phoneRegex=/^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;

        if(!phoneNum){
            alert("핸드폰 번호를 입력해주세요")
            $("#phoneNum").focus();
            return;
        }else if(!phoneRegex.text(phoneNum)){
            alert("유효한 핸드폰번호를 입력해주세요");
            phoneNumInput.focus;
            return;
        }
    })

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
                }
            },
            error:function(xhr,status,error){
                            console.error(error);
            }
        })

    })
})