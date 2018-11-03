<%--
  Created by IntelliJ IDEA.
  User: lrp
  Date: 17-2-13
  Time: 下午11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="BASE" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>文章管理</title>
</head>
<body>

<h1>文章列表</h1>

<a>创建文章</a>

<table>
    <tr>
        <th>id</th>
        <th>标题</th>
        <th>摘要</th>
        <th>内容</th>
        <th>操作</th>
    </tr>
    <c:forEach var="article" items="${articleList}">
        <tr>
            <td>${article.id}</td>
            <td>${article.title}</td>
            <td>${article.summary}</td>
            <td>${article.content}</td>
            <td>
                <a href="${BASE}/article_edit?id=${article.id}">编辑</a>
                <a href="${BASE}/article_delete?id=${article.id}">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
