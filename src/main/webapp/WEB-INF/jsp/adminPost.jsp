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
     <c:forEach var="post" items = "${posts}">
    <tbody>
      <tr>
    
        <td>${post.title}</td>
        <td>${post.content}</td>
        <td>${post.user.username}</td>
        <td>${post.createDate }</td>
        <td><button class="${post.id } btn btn-danger btn-delete">삭제</button></td>
      </tr>
    </tbody>
    </c:forEach>
  </table>
</div>
</body>
<script>
let index = {
		init : function() {
			$(".btn-delete").on("click", ()=>{
				this.deleteById();
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