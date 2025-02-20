<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Set, java.util.Map, java.util.HashSet, java.util.Map.Entry, java.util.List, java.util.ArrayList, java.util.Comparator, model.Letter"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style.css">
<title>GLM - 学習状況</title>
</head>
<body>
<%@ include file="header.jsp" %>

<h2>今の学習状況</h2>

	<form action="clear" method="post">
	    <button type="submit">学習履歴を削除</button>
	</form>

<%
    Set<Letter> letters = (Set<Letter>) session.getAttribute("letters");
    if (letters == null || letters.isEmpty()) {
%>
        <p>まだ学習されていません。</p>
<%
    } else {
        // Set<Letter> を List<Letter> に変換
        List<Letter> letterList = new ArrayList<>(letters);
        
        // 総数順に並び替え（降順）
        letterList.sort(Comparator.comparingInt(Letter::getSum).reversed());

        int rowNum = 1; // 行番号
        int maxRows = 100; // 最大行数
%>
        <table border="1">
            <tr>
                <th>総数順位</th>
                <th>文字</th>
                <th>総数</th>
                <th>次の文字 (頻度)</th>
            </tr>
<%
        for (Letter letter : letterList) {
            /* if (rowNum > maxRows) {
                break; // 行番号が100を超えたら終了
            } */
            Map<Letter, Integer> distr = letter.getDistr();
%>
            <tr>
                <td><%= rowNum %></td>
                <td><%= letter.getLetter() %></td>
                <td><%= letter.getSum() %></td>
                <td>
<%
                    boolean first = true;
                    for (Entry<Letter, Integer> entry : distr.entrySet()) {
                        if (!first) { out.print(" "); }
                        out.print(entry.getKey().getLetter() + "(" + entry.getValue() + ")");
                        first = false;
                    }
%>
                </td>
            </tr>
<%
            rowNum++; // 行番号を増やす
        }
%>
        </table>
<%
    }
%>

</body>
</html>
