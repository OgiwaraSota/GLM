<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style.css">
<title>GLM</title>
</head>
<body>
<%@ include file = "header.jsp" %>

	<h2>学習させたい文章を入力してください</h2>
	<form action="learn" method="post">
	    <label for="inputText"></label>
	    <textarea id="inputText" name="text" required></textarea>
	    <button type="submit">学習</button>
	</form>
	

</body>
</html>