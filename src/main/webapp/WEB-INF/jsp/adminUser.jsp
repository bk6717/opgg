<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>유저 관리</title>
</head>
<body>

<div class="container">
   <ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link active" href="/admin/user">유저관리</a>
    </li>
    <li class="nav-item">
      <a class="nav-link " href="/admin/post">게시글 관리</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="/admin/reply">댓글 관리</a>
    </li>
     
  </ul>
</div>


<div class="container">

<form>
  <div class="input-group">
    <input  type="text" class="form-control" id ="search" placeholder="이메일을 입력해주세요">
    <div class="input-group-btn">
      <button class="btn btn-default btn-search" type="button" >
        <i class="glyphicon glyphicon-search"></i>
      </button>
    </div>
  </div>
</form>

  <table class="table">
    <thead>

      <tr>
        <th>이름</th>
        <th>Email</th>
        <th>생성 날짜</th>
        <th>권한</th>
      </tr>
    </thead>
    <c:forEach var="user" items = "${users}">
    <tbody>
      <tr>
        <td>${user.username}</td>
        <td>${user.email}</td>
        <td>${user.createDate}</td>
        <td><div class="form-group">
 			 <select class="${user.id} form-control roles" >
			 <option selected disabled hidden>${user.roles }</option>
   			 <option value="ROLE_USER">ROLE_USER</option>
  			 <option value="ROLE_ADMIN">ROLE_ADMIN</option>
 			 </select>
		</div>
	   </td>
        <td><button class="${user.id } btn btn-danger btn-delete">탈퇴</button></td>
      </tr>
    </tbody>
    </c:forEach>
    
  </table>
</div>
</body>
<script>
let index = {
		init : function() {
			
			$(".btn-search").on("click",()=>{
				this.searchUser();
				
			});
			
			//유저 권한
			$(".roles").on("change", ()=>{
				this.updateRole();
			});
			
			//유저 삭제하기
			$(".btn-delete").on("click", ()=>{
				this.deleteById();
			});
			
			
		},
		
		searchUser: function(){
			console.log(event.target);
			
			let data = {
			 email : $("#search").val()
			}
			
			alert(data.email);
			
			$.ajax({
				
				type : "post",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				url : "/admin/user/search",
				dataType : "json"
				
			}).done(function(result){
				$("table").empty();
				
				for(var user of result){
					
					var user ="<thead>\r\n" + 
					"\r\n" + 
					"      <tr>\r\n" + 
					"        <th>이름</th>\r\n" + 
					"        <th>Email</th>\r\n" + 
					"        <th>생성 날짜</th>\r\n" + 
					"        <th>권한</th>\r\n" + 
					"      </tr>\r\n" + 
					"    </thead>\r\n" + 
					"    <tbody>\r\n" + 
					"      <tr>\r\n" + 
					"        <td>"+user.username+"</td>\r\n" + 
					"        <td>"+user.email+"</td>\r\n" + 
					"        <td>"+user.createDate+"</td>\r\n" + 
					"        <td><div class=\"form-group\">\r\n" + 
					" 			 <select class=\""+user.id+" form-control roles\" >\r\n" + 
					"			 <option selected disabled hidden>"+user.roles+"</option>\r\n" + 
					"   			 <option value=\"ROLE_USER\">ROLE_USER</option>\r\n" + 
					"  			 <option value=\"ROLE_ADMIN\">ROLE_ADMIN</option>\r\n" + 
					" 			 </select>\r\n" + 
					"		</div>\r\n" + 
					"	   </td>\r\n" + 
					"        <td><button class=\""+user.id+"btn btn-danger btn-delete\">탈퇴</button></td>\r\n" + 
					"      </tr>\r\n" + 
					"    </tbody>\r\n";
				}
				
				$("table").append(user);
				console.log(result);
		
			}).fail((error)=>{
				console.log(error);
			});
		},
		
		//유저 권한
		updateRole: function(){
			
			console.log(event.target.value);
			
			let data = {
					role: event.target.value
			}
			
			$.ajax({
				type:"put",
				url: "user/updateRole/"+event.target.className.split(" ")[0],
				contentType : "application/json; charset=utf-8",
				data: JSON.stringify(data),
				dataType: "text"
			}).done((resp)=>{
				alert("변경하였습니다.");
				location.href = "/admin/user";
			}).fail((error)=>{
				alert("변경에 실패하셧습니다");
				console.log(error);
			});
		},
		
		//유저삭제 로직 
		deleteById: function() {
			let id = event.target.className.split(" ")[0]; // 공백으로 나누어서 제일 첫번째에 있는것
			console.log(id);
			
			$.ajax({
				type:"delete",
				url: "user/delete/"+id,
				dataType: "text"
			}).done((resp)=>{
				alert("삭제에 성공하셧습니다.");
				location.href = "/admin/user";
			}).fail((error)=>{
				alert("삭제실패");
				console.log(error);
			});
		}
		
}
index.init();
</script>
</html>
