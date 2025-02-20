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

	<form action="generate" method="post">
		<button type="submit">生成</button>
	</form>
	<hr>
	
<%
    String errorMsg = (String) session.getAttribute("errorMsg");
    String text = (String) session.getAttribute("text");
    if (errorMsg != null) {
%>
        <p class="errorMsg"><%= errorMsg %></p>
<%
    } else if (text != null && !text.isEmpty()) {
%>
        <p class="generatedText"><%= text %></p>
<%
    } else {
%>
        <p class="generatedText">テキストが生成されていません。</p>
<%
    }
%>
    		

</body>
</html>