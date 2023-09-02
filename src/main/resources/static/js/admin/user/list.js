$(function(){
	getUsers();

	$("#selectStatus").change(function(){
		getUsers($("#selectStatus option:selected").val());
	});
})

function getUsers(value){
	if(value){
		fetch("/adminRest/userList?userStatus=" + value)
		.then(response => response.json())
		.then(data => {
			buildUsers(data);
		});
	}else{
		fetch("/adminRest/userList")
		.then(response => response.json())
		.then(data => {
			buildUsers(data);
		});
	}
}

function buildUsers(data){
	$("#userTable tbody").empty();
	data.forEach(user => {
		let btnOption1 = (user.status == "ACTIVE") ? "" : `<td><button class="btn btn-outline-success" data-value="ACTIVE" data-user-id="${user.id}">활성</button></td>`;
		let btnOption2 = (user.status == "DEACTIVE") ? "" : `<td><button class="btn btn-outline-warning" data-value="DEACTIVE" data-user-id="${user.id}">정지</button></td>`;
		let btnOption3 = (user.status == "RESIGN") ? "" : `<td><button class="btn btn-outline-danger" data-value="RESIGN" data-user-id="${user.id}">탈퇴</button></td>`;
		let btnGroup = btnOption1 + btnOption2 + btnOption3;
		$('#userTable tbody').append(`
		<tr>
			<td>${user.id}</td>
			<td>${user.username}</td>
			<td>${user.name}</td>
			<td>${user.email}</td>
			<td>${user.phoneNum}</td>
			<td>${user.birthday}</td>
			<td>${user.gender}</td>
			<td>${user.status}</td>
			${btnGroup}
		</tr>
		`);
	});
	$("[data-value]").click(function(){
		fetch(`/adminRest/setUserStatus?userId=${$(this).attr("data-user-id")}&status=${$(this).attr("data-value")}`)
		.then(response => response.json())
		.then(data => {
			if (data.count == 1){
				alert("Work successfully");
			}else{
				alert("Failed");
			}
			getUsers();
		});
		
	});
}