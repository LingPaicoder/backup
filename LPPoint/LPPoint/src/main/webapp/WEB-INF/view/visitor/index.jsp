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

        <title>主页</title>
            
        <!-- CSS -->
        <link href="${BASE}/asset/css/bootstrap.min.css" rel="stylesheet">
        <link href="${BASE}/asset/css/animate.min.css" rel="stylesheet">
        <link href="${BASE}/asset/css/font-awesome.min.css" rel="stylesheet">
        <link href="${BASE}/asset/css/form.css" rel="stylesheet">
        <link href="${BASE}/asset/css/calendar.css" rel="stylesheet">
        <link href="${BASE}/asset/css/style.css" rel="stylesheet">
        <link href="${BASE}/asset/css/icons.css" rel="stylesheet">
        <link href="${BASE}/asset/css/generics.css" rel="stylesheet">
        <link href="${BASE}/asset/css/jsmind.css" rel="stylesheet">

        <style type="text/css">

            #jsmind_container{
                width:100%;
                height:700px;
            }
            #jsmind_container > div {
                overflow: hidden;
            }

        </style>

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

        <div id="jsmind_container"></div>


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

        <!-- jsMind -->
        <script src="${BASE}/asset/js/jsmind/jsmind.js"></script>
        <script src="${BASE}/asset/js/jsmind/jsmind.draggable.js"></script>
        <script src="${BASE}/asset/js/jsmind/jsmind.screenshot.js"></script>

        <script>

            var mind = {
                /* 元数据，定义思维导图的名称、作者、版本等信息 */
                "meta":{
                    "name":"example",
                    "author":"hizzgdev@163.com",
                    "version":"0.2"
                },
                /* 数据格式声明 */
                "format":"node_array",
                /* 数据内容 */
                "data":[
                    {"id":"1","isroot":true, "background-image":"${BASE}/asset/img/profile-pic.jpg", "width": "100", "height": "100"},
                    ${typeStr}
                ]
            };

            var options = {
                container:'jsmind_container',
                editable:false,
                theme:'lppoint'
            };

            var jm = new jsMind(options);
            // 让 jm 显示这个 mind 即可
            jm.show(mind);

        </script>

        <script>
            var tempId = 1;
            window.setInterval(checkSelected, 500);
            function checkSelected()
            {

                if(null != jm.get_selected_node() && 1 != jm.get_selected_node().id && tempId != jm.get_selected_node().id){
                    $.ajax({
                        type: "post",
                        dataType: "json",
                        async: "false",
                        url: "/visitor_index/get_article_num",
                        data: {
                            typeid : jm.get_selected_node().id
                        },
                        success: function (data){
                            if(data > 0){
                                if(true == confirm("是否查看" + "\"" +jm.get_selected_node().topic + "\"" + "下的内容？")){
                                    tempId = 1;
                                    location.href = "/visitor_article/article_waterfall_view?typeid=" + jm.get_selected_node().id ;
                                }else {
                                    tempId = jm.get_selected_node().id;
                                }
                            }else {
                                tempId = jm.get_selected_node().id;
                                alert("\"" + jm.get_selected_node().topic + "\"" + "下暂无内容，敬请期待！");
                            }
                        }
                    })
                }
            }
        </script>

    </body>
</html>

