function checkForm() {
  let answer = confirm("회원탈퇴를 하시겠습니까?");
  if (!answer) {
    alert("취소합니다");
  }
  return answer;
}