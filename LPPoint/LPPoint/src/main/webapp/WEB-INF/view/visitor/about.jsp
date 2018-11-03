<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="/security" %>
<c:set var="BASE" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
        <meta name="format-detection" content="telephone=no">
        <meta charset="UTF-8">

        <meta name="description" content="Violate Responsive Admin Template">
        <meta name="keywords" content="Super Admin, Admin, Template, Bootstrap">

        <title>关于</title>
            
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
            <br />
            <div class="alert alert-success">
                先静而后它，专注于做事。
            </div>

            <hr class="whiter">
            <h4 class="page-title">About</h4>
            <br/>
            <p>灵派coder，毕业于燕山大学，就职于58到家，目前专注于Web开发。</p>
            <br/>

            <hr class="whiter">
            <h4 class="page-title">Contact</h4>
            <br/>
            <p>邮箱：15010431469@163.com</p>
            <br/>
            <p>微信：lrp151101</p>
            <br/>
            <p>公众号：灵派coder</p>
            <br/>

            <div style="width:100%;height: auto">
                <img src="${BASE}/asset/img/lingpaicoder.jpg" style="margin: 0 auto;display:block">
                <div style="text-align: center">
                    <br/>
                    扫描关注微信公众号交流
                    <br/>
                    让一切变得简单、合理、易于理解。
                </div>
            </div>
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
        
    </body>
</html>

