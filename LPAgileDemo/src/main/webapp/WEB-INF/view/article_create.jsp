<%--
  Created by IntelliJ IDEA.
  User: lrp
  Date: 17-3-5
  Time: 下午3:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="BASE" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>文章管理_新增文章</title>
</head>
<body>


<form  id="customer_create">
    <table>
        <tr>
            <td>标题：</td>
            <td><input type="text" id="title" name="title"></td>
        </tr>
        <tr>
            <td>摘要：</td>
            <td><input type="text" id="summary" name="summary"></td>
        </tr>
        <tr>
            <td>内容：</td>
            <td><input type="text" id="content" name="content"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" id="submit" ></td>
        </tr>
    </table>
</form>

<script src="${BASE}/asset/lib/jquery/jquery.min.js"></script>
<script src="${BASE}/asset/lib/jquery-form/jquery.form.min.js"></script>
<script>
    $(function() {
        $('#customer_create').ajaxForm({
            type: 'put',
            url: '${BASE}/customer_create',
            success: function(data) {
                if (data) {
                    location.href = '${BASE}/article';
                }
            }
        });
    });
</script>

</body>
</html>
