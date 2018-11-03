<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="/security" %>
<%@ taglib prefix="page" uri="/page" %>
<c:set var="BASE" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
        <meta name="format-detection" content="telephone=no">
        <meta charset="UTF-8">

        <meta name="description" content="Violate Responsive Admin Template">
        <meta name="keywords" content="Super Admin, Admin, Template, Bootstrap">

        <title>留言</title>
            
        <!-- CSS -->
        <link href="${BASE}/asset/css/bootstrap.min.css" rel="stylesheet">
        <link href="${BASE}/asset/css/animate.min.css" rel="stylesheet">
        <link href="${BASE}/asset/css/font-awesome.min.css" rel="stylesheet">
        <link href="${BASE}/asset/css/form.css" rel="stylesheet">
        <link href="${BASE}/asset/css/calendar.css" rel="stylesheet">
        <link href="${BASE}/asset/css/style.css" rel="stylesheet">
        <link href="${BASE}/asset/css/icons.css" rel="stylesheet">
        <link href="${BASE}/asset/css/generics.css" rel="stylesheet">

    </head>
    <body id="skin-tectile">
    <div class="user-header" id="ban">
        <div class="container">

            <div class="animated" data-wow-delay=".5s" style="visibility: visible; animation-delay: 0.5s; animation-name: fadeInLeft;">
                <nav class="navbar" style="margin-bottom: 0;">

                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse nav-wil" id="bs-example-navbar-collapse-1">

                        <nav class="link-effect-7" id="link-effect-7">
                            <ul class="nav navbar-nav" style="float: left;">
                                <li class="active act"><a href="/visitor_index/index_view"><h4>灵派coder&nbsp;|&nbsp;知识点&nbsp;LPPoint</h4></a></li>
                            </ul>

                            <ul class="nav navbar-nav" style="float: right;">
                                <li><a href="/visitor_article/article_waterfall_view"><h4>最新</h4></a></li>
                                <li><a href="/visitor_article/article_waterfall_view?typeid=10"><h4>框架</h4></a></li>
                                <li><a href="/visitor_article/article_waterfall_view?typeid=11"><h4>项目</h4></a></li>
                                <li><a href="/visitor_message/message_view"><h4>留言</h4></a></li>
                                <li><a href="/visitor_about/about_view"><h4>关于</h4></a></li>
                                <li>
                                    <security:guest>
                                        <a href="/login_view"><h4>登录</h4></a>
                                    </security:guest>

                                    <security:user>
                                        <a href="#"><h4>欢迎：${currentUser.username}</h4></a>
                                    </security:user>

                                </li>
                                <li><input type="text" class="main-search" style="position: relative;top: 18px;margin-left: 20px;"></li>
                            </ul>
                        </nav>
                    </div>
                    <!-- /.navbar-collapse -->
                </nav>
            </div>
        </div>
    </div>
    <hr class="whiter">


        <div class="block-area" id="media-object">

            <div class="form-group m-b-15">
                <label>说点什么吧...</label>
                <textarea id="newMessageTextarea" class="input-sm validate[required] form-control" placeholder="..."></textarea>
            </div>

            <security:guest>
                <p>您尚未登录，请先登录,以发布留言</p>
                <button type="button" class="btn btn-alt m-r-5" onclick="javascript:location.href='/login_view?href=/visitor_message/message_view?currentPage=${pager.currentPage}'">登录</button>
            </security:guest>

            <security:user>
                <button type="button" class="btn btn-alt m-r-5" onclick="addNewMessage()">发布</button>
            </security:user>



            <br />
            <br />
            <hr class="whiter">
            <br />


            <input type="hidden" id="focusId" value="${focusid}">
                <%--第一级留言--%>
            <c:forEach var="messageBO" items="${messageBOList}" >
                <div class="media">
                    <a class="pull-left" href="#">
                        <img class="media-object" src="${BASE}/asset/img/${messageBO.picPath}" alt="">
                    </a>
                    <div class="media-body">
                        <h5 class="media-heading">${messageBO.realName}</h5>
                            ${messageBO.content}

                        <br />
                        <br />
                        <c:if test="${ messageBO.userId == currentUser.id }">
                             <button type="button" onclick="deleteMessage(${messageBO.id})" class="btn btn-xs btn-alt m-r-5">删除</button>
                        </c:if>
                        <button type="button" class="btn btn-xs btn-alt m-r-5" id="reply${messageBO.id}" onclick="showTextArea(${messageBO.id})">回复</button>
                        <br />
                        <br />

                        <div class="divForTextarea" id="div${messageBO.id}"></div>

                            <%--第二级留言--%>
                        <c:if test="${ (null != messageBO.subMessages) && (messageBO.subMessages.size() > 0) }">
                            <c:forEach var="messageBO2" items="${messageBO.subMessages}" >
                                <div class="media">
                                    <a class="pull-left" href="#">
                                        <img class="media-object" src="${BASE}/asset/img/${messageBO2.picPath}" alt="">
                                    </a>
                                    <div class="media-body">
                                        <h5 class="media-heading">${messageBO2.realName}</h5>
                                            ${messageBO2.content}

                                        <br />
                                        <br />
                                        <c:if test="${ messageBO2.userId == currentUser.id }">
                                            <button type="button" onclick="deleteMessage(${messageBO2.id})" class="btn btn-xs btn-alt m-r-5">删除</button>
                                        </c:if>
                                        <button type="button" class="btn btn-xs btn-alt m-r-5" id="reply${messageBO2.id}" onclick="showTextArea(${messageBO2.id})">回复</button>
                                        <br />
                                        <br />

                                        <div class="divForTextarea" id="div${messageBO2.id}"></div>

                                            <%--第三级留言--%>
                                        <c:if test="${ (null != messageBO2.subMessages) && (messageBO2.subMessages.size() > 0) }">
                                            <c:forEach var="messageBO3" items="${messageBO2.subMessages}" >
                                                <div class="media">
                                                    <a class="pull-left" href="#">
                                                        <img class="media-object" src="${BASE}/asset/img/${messageBO3.picPath}" alt="">
                                                    </a>
                                                    <div class="media-body">
                                                        <h5 class="media-heading">${messageBO3.realName}</h5>
                                                            ${messageBO3.content}

                                                        <br />
                                                        <br />
                                                        <c:if test="${ messageBO3.userId == currentUser.id }">
                                                            <button type="button" onclick="deleteMessage(${messageBO3.id})" class="btn btn-xs btn-alt m-r-5">删除</button>
                                                        </c:if>
                                                        <button type="button" class="btn btn-xs btn-alt m-r-5" id="reply${messageBO3.id}" onclick="showTextArea(${messageBO3.id})">回复</button>
                                                        <br />
                                                        <br />

                                                        <div class="divForTextarea" id="div${messageBO3.id}"></div>

                                                            <%--第四级留言--%>
                                                        <c:if test="${ (null != messageBO3.subMessages) && (messageBO3.subMessages.size() > 0) }">
                                                            <c:forEach var="messageBO4" items="${messageBO3.subMessages}" >
                                                                <div class="media">
                                                                    <a class="pull-left" href="#">
                                                                        <img class="media-object" src="${BASE}/asset/img/${messageBO4.picPath}" alt="">
                                                                    </a>
                                                                    <div class="media-body">
                                                                        <h5 class="media-heading">${messageBO4.realName}</h5>
                                                                            ${messageBO4.content}

                                                                        <br />
                                                                        <br />
                                                                        <c:if test="${ messageBO4.userId == currentUser.id }">
                                                                            <button type="button" onclick="deleteMessage(${messageBO4.id})" class="btn btn-xs btn-alt m-r-5">删除</button>
                                                                        </c:if>
                                                                        <button type="button" class="btn btn-xs btn-alt m-r-5" id="reply${messageBO4.id}" onclick="showTextArea(${messageBO4.id})">回复</button>
                                                                        <br />
                                                                        <br />

                                                                        <div class="divForTextarea" id="div${messageBO4.id}"></div>

                                                                            <%--第五级留言--%>
                                                                        <c:if test="${ (null != messageBO4.subMessages) && (messageBO4.subMessages.size() > 0) }">
                                                                            <c:forEach var="messageBO5" items="${messageBO4.subMessages}" >
                                                                                <div class="media">
                                                                                    <a class="pull-left" href="#">
                                                                                        <img class="media-object" src="${BASE}/asset/img/${messageBO5.picPath}" alt="">
                                                                                    </a>
                                                                                    <div class="media-body">
                                                                                        <h5 class="media-heading">${messageBO5.realName}</h5>
                                                                                            ${messageBO5.content}

                                                                                        <br />
                                                                                        <br />
                                                                                        <c:if test="${ messageBO5.userId == currentUser.id }">
                                                                                            <button type="button" onclick="deleteMessage(${messageBO5.id})" class="btn btn-xs btn-alt m-r-5">删除</button>
                                                                                        </c:if>
                                                                                        <br />
                                                                                        <br />

                                                                                        <div class="divForTextarea" id="div${messageBO5.id}"></div>

                                                                                    </div>
                                                                                </div>
                                                                            </c:forEach>
                                                                        </c:if>

                                                                    </div>
                                                                </div>
                                                            </c:forEach>
                                                        </c:if>

                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:if>

                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>

                    </div>
                </div>
            </c:forEach>


            <br/>
            <br/>
            <hr class="whiter">
            <br/>
            <br/>


            <page:page  value="${pager}"  url="/visitor_message/message_view"></page:page>


            <br/>
            <br/>


        </div>


        <!-- Javascript Libraries -->
        <!-- jQuery -->
        <script src="${BASE}/asset/js/jquery.min.js"></script> <!-- jQuery Library -->

        <!-- Bootstrap -->
        <script src="${BASE}/asset/js/bootstrap.min.js"></script>

        <!-- UX -->
        <script src="${BASE}/asset/js/scroll.min.js"></script> <!-- Custom Scrollbar -->

        <!-- Other -->
        <script src="${BASE}/asset/js/calendar.min.js"></script> <!-- Calendar -->
        <script src="${BASE}/asset/js/feeds.min.js"></script> <!-- News Feeds -->
        
        
        <!-- All JS functions -->
        <script src="${BASE}/asset/js/functions.js"></script>

        <script>

            $(document).ready(function() {
                var focusId = $("#focusId").val();
                if(focusId > 0){
                    $("#reply" + focusId).focus();
                }
            });



            function showTextArea(pMsgId) {
                $(".divForTextarea").html("");
                $("#div" + pMsgId).html(
                        '<textarea id="textarea' + pMsgId + '" class="input-sm validate[required] form-control" placeholder="..."></textarea>' +
                        '<br />' +
                        '<button class="btn btn-xs btn-alt m-r-5" onclick="addMessage(' + pMsgId + ')" >确定</button>'
                );
            }

            function deleteMessage (id){
                if(confirm("确认要删除么？")){
                    $.ajax({
                        type: "post",
                        dataType: "json",
                        async: "false",
                        url: "/visitor_message/message_delete",
                        data: {
                            id : id
                        },
                        success: function (data){
                            var result = data.split(":");
                            if(2 == result.length){
                                if(1 == result[0]){
                                    //alert("成功");
                                    location.href = "/visitor_message/message_view?focusid=" + result[1];
                                }else {
                                    alert("失败");
                                }
                            }else {
                                alert("失败");
                            }
                        }
                    })
                }
            }

            function addNewMessage() {
                var content = $("#newMessageTextarea").val();
                if(content.length <= 0){
                    alert("内容不能为空！");
                    return;
                }
                $.ajax({
                    type: "post",
                    dataType: "json",
                    async: "false",
                    url: "/visitor_message/message_add",
                    data: {
                        pMsgId : -1,
                        content : content
                    },
                    success: function (data){
                        var result = data.split(":");
                        if(2 == result.length){
                            if(1 == result[0]){
                                //alert("成功");
                                location.href = "/visitor_message/message_view?focusid=-1";
                            }else {
                                alert("失败");
                            }
                        }else {
                            alert("失败");
                        }
                    }
                })
            }

            function addMessage(pMsgId){
                var content = $("#textarea" + pMsgId).val();
                if(content.length <= 0){
                    alert("内容不能为空！");
                    return;
                }
                $.ajax({
                    type: "post",
                    dataType: "json",
                    async: "false",
                    url: "/visitor_message/message_add",
                    data: {
                        pMsgId : pMsgId,
                        content : content
                    },
                    success: function (data){
                        var result = data.split(":");
                        if(2 == result.length){
                            if(1 == result[0]){
                                //alert("成功");
                                location.href = "/visitor_message/message_view?focusid=" + pMsgId;
                            }else {
                                alert(result[1]);
                            }
                        }else {
                            alert("失败");
                        }
                    }
                })
            }
        </script>


    </body>
</html>

