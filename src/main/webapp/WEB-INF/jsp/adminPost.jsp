<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<meta charset="UTF-8">
<title>게시글 관리</title>
</head>
<body>
<div class="container">
  <ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link " href="/admin/user">유저관리</a>
    </li>
    <li class="nav-item">
      <a class="nav-link active" href="/admin/post">게시글 관리</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="/admin/reply">댓글 관리</a>
    </li>
  </ul>
</div>
<div class="container">

<form>
  <div class="input-group">
    <input  type="text" class="form-control" id ="search" placeholder="게시글 제목을 입력해주세요">
    <div class="input-group-btn">
      <button class="btn btn-default btn-search" type="button">
      	검색
      </button>
    </div>
  </div>
</form>

  <table class="table">
    <thead>
  
      <tr>
      	
        <th>제목</th>
        <th>내용</th>
        <th>작성자</th>
        <th>작성시간</th>  
        <th>게시글 삭제 </th> 
      </tr>
    </thead>
     <tbody id ="tbody">
     <c:forEach var="post" items = "${posts}">
   
      <tr>
    
        <td>${post.title}</td>
        <td>${post.content}</td>
        <td>${post.user.username}</td>
        <td>${post.createDate }</td>
        <td><button class="${post.id } btn btn-danger btn-delete">삭제</button></td>
      </tr>
   
    </c:forEach>
     </tbody>
  </table>
</div>
</body>
<script>
let index = {
		init : function() {
			
			$(".btn-delete").on("click", ()=>{
				this.deleteById();
			});
			
			$(".form-control").on("keypress",()=>{
				if(event.keyCode == 13){
					this.searchUser();
					return false;
				}
			});
				
			$(".btn-search").on("click",()=>{
				this.searchUser();
			});	
		
		},
		
		searchUser: function(){
			console.log(event.target);
			
			let data = {
			 title : $("#search").val()
			}
			
			
			
			$.ajax({
				
				type : "post",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				url : "/admin/post/search",
				dataType : "json"
				
			}).done(function(result){
				$("#tbody").empty();
				
				for(var post of result){
					
					var post = 	" <tr>\r\n" + 
					"    \r\n" + 
					"        <td>"+post.title+"</td>\r\n" + 
					"        <td>"+post.content+"</td>\r\n" + 
					"        <td>"+post.user.username+"</td>\r\n" + 
					"        <td>"+post.createDate+"</td>\r\n" + 
					"        <td><button class=\""+post.id+"btn btn-danger btn-delete\">삭제</button></td>\r\n" + 
					"      </tr>\r\n" + 
					"   ";
					
					$("#tbody").append(post);
					
				}
			
			
				
				console.log(result);
		
			}).fail((error)=>{
				console.log(error);
			});
		},
		
		deleteById: function() {
			let id = event.target.className.split(" ")[0];
			console.log(id);
			
			$.ajax({
				type:"delete",
				url: "/post/delete/"+id,
				dataType: "text"
			}).done((resp)=>{
				alert("삭제성공");
				location.href = "/admin/post";
			}).fail((error)=>{
				alert("삭제실패");
				console.log(error);
			});
		}
		
}
index.init();
</script>

</html>