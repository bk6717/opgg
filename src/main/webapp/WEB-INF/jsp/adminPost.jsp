<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<meta charset="UTF-8">
<title>게시글 관리</title>
</head>
<body>
<div class="container">
  <ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link " href="/test/jsp/user">유저관리</a>
    </li>
    <li class="nav-item">
      <a class="nav-link active" href="/test/jsp/post">게시글 관리</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="/test/jsp/reply">댓글 관리</a>
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
      </tr>
    </thead>
     <c:forEach var="post" items = "${posts}">
    <tbody>
      <tr>
        <td>${post.title}</td>
        <td>${post.content}</td>
        <td>${post.user.username}</td>
        <td>${post.createDate }</td>
        <td><button class="btn btn-danger">탈퇴</button></td>
      </tr>
    </tbody>
    </c:forEach>
  </table>
</div>
</body>
</html>