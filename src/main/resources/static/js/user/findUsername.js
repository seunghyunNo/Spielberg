$(document).ready(function(){
    $('#findIdButton').click(function(){

        const name= $("#name").val().trim();

        if(name.value.length<1){
            alert("이름을 입력해주세요")
            return;
        }


        const birth=$("#birth").val().trim();
        if(birth.value.length!=6){
            alert("생년월일을 입력해주세요")
            return;
        }

        const email = $("#email").val().trim();

        val emailRegex=/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
        if(!email){
            alert("이메일을 입력해주세요")
            $("#email").focus();
            return;
        }else if(!emailRegex.test(email)){
            alert("이메일을 다시 한 번 확인해주세요");
            $("#email").focus();
            return;
        }

        $.ajax({
            url: '/user/findUsername',
            type: 'POST',
            data: {email,email},{name,name},{birth,birth}
            dataType:'text',
            success:function(response){

                if(!response){
                    $('#username_exist').html('아이디가 없습니다');
                    $('#username_exist').html('border border-3 p-3');
                }else{

                    $('#username_exist').html('아이디는'+$("#username")+'입니다');
                    
                    $('#username_exist').addClass('border border-3 p-3');
                }
            },
            error:function(xhr,status,error){
                console.error(error);
            }
        })
    })
})