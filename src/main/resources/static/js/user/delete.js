function checkForm() {
  let answer = confirm("Do you want to delete?");
  if (!answer) {
    alert("cancel");
  }
  return answer;
}