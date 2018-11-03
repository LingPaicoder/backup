<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="BASE" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
        <meta name="format-detection" content="telephone=no">
        <meta charset="UTF-8">

        <meta name="description" content="Violate Responsive Admin Template">
        <meta name="keywords" content="Super Admin, Admin, Template, Bootstrap">

        <title>登录/注册</title>
            
        <!-- CSS -->
        <link href="${BASE}/asset/css/bootstrap.min.css" rel="stylesheet">
        <link href="${BASE}/asset/css/form.css" rel="stylesheet">
        <link href="${BASE}/asset/css/style.css" rel="stylesheet">
        <link href="${BASE}/asset/css/animate.css" rel="stylesheet">
        <link href="${BASE}/asset/css/generics.css" rel="stylesheet">
    </head>
    <body id="skin-tectile">
        <section id="login">
            <header>
                <h1 style="text-align: center">知识点&nbsp;LPPoint</h1>
            </header>
        
            <div class="clearfix"></div>
            
            <!-- Login -->
            <form class="box tile animated active" id="box-login" style="left: 50%;margin-left: -250px;">
                <h2 class="m-t-0 m-b-15">登录</h2>
                <input type="hidden" id="href" name="href" value="${href}">
                <input type="text" class="login-control m-b-10" id="loginUsername" placeholder="用户名或邮箱地址">
                <input type="password" class="login-control" id="loginPassword" placeholder="密码">
                <div class="checkbox m-b-20">
                    <label>
                        <input type="checkbox">
                        记住我
                    </label>
                </div>
                <button type="button" class="btn btn-sm btn-alt" onclick="login()">登录</button>
                
                <small>
                    <a class="box-switcher" data-switch="box-register" href="">还没有账号?</a>
                </small>
            </form>
            
            <!-- Register -->
            <form class="box animated tile" id="box-register"  style="left: 50%;margin-left: -250px;">
                <h2 class="m-t-0 m-b-15">注册</h2>

                <p>请选择头像</p>
                <label class="radio-inline">
                    <span class="checkableBox checkableBox-radio">
                        <input type="radio" class="validate[required]" name="picPath" value="profile-pics/1.jpg">
                    </span>
                    <img width="37" src="${BASE}/asset/img/profile-pics/1.jpg" alt="">
                </label>
                <label class="radio-inline">
                    <span class="checkableBox checkableBox-radio">
                        <input type="radio" class="validate[required]" name="picPath" value="profile-pics/2.jpg"  checked="checked">
                    </span>
                    <img width="37" src="${BASE}/asset/img/profile-pics/2.jpg" alt="">
                </label>
                <label class="radio-inline">
                    <span class="checkableBox checkableBox-radio">
                        <input type="radio" class="validate[required]" name="picPath" value="profile-pics/3.jpg">
                    </span>
                    <img width="37" src="${BASE}/asset/img/profile-pics/3.jpg" alt="">
                </label>
                <label class="radio-inline">
                    <span class="checkableBox checkableBox-radio">
                        <input type="radio" class="validate[required]" name="picPath" value="profile-pics/4.jpg">
                    </span>
                    <img width="37" src="${BASE}/asset/img/profile-pics/4.jpg" alt="">
                </label>
                <label class="radio-inline">
                    <span class="checkableBox checkableBox-radio">
                        <input type="radio" class="validate[required]" name="picPath" value="profile-pics/5.jpg">
                    </span>
                    <img width="37" src="${BASE}/asset/img/profile-pics/5.jpg" alt="">
                </label>
                <label class="radio-inline">
                    <span class="checkableBox checkableBox-radio">
                        <input type="radio" class="validate[required]" name="picPath" value="profile-pics/6.jpg">
                    </span>
                    <img width="37" src="${BASE}/asset/img/profile-pics/6.jpg" alt="">
                </label>
                <br/>
                <br/>

                <input type="text" class="login-control m-b-10" id="realName" placeholder="真实姓名">
                <input type="text" class="login-control m-b-10" id="username" placeholder="用户名">
                <input type="email" class="login-control m-b-10" id="email" placeholder="邮箱地址">
                <input type="password" class="login-control m-b-10" id="password" placeholder="密码">
                <input type="password" class="login-control m-b-20" id="checkPassword" placeholder="确认密码">


                <button type="button" class="btn btn-sm btn-alt" onclick="register()">注册</button>

                <small><a class="box-switcher" data-switch="box-login" href="">已经有账号了?</a></small>
            </form>
            
        </section>
        
        <!-- Javascript Libraries -->
        <!-- jQuery -->
        <script src="${BASE}/asset/js/jquery.min.js"></script> <!-- jQuery Library -->
        
        <!-- Bootstrap -->
        <script src="${BASE}/asset/js/bootstrap.min.js"></script>
        
        <!--  Form Related -->
        <script src="${BASE}/asset/js/icheck.js"></script> <!-- Custom Checkbox + Radio -->
        
        <!-- All JS functions -->
        <script src="${BASE}/asset/js/functions.js"></script>

        <script>

            function login() {
                var password = $("#loginPassword").val();
                if(password == ""){
                    alert("密码不能为空！");
                }
                var username = $("#loginUsername").val();
                var href = $("#href").val();
                $.ajax({
                    type: "post",
                    dataType: "json",
                    async: "false",
                    url: "/login",
                    data: {
                        username : username,
                        href : href,
                        password : password
                    },
                    success: function (data){
                        var result = data.split(":");
                        if(2 == result.length){
                            if(1 == result[0]){
                                alert("成功");
                                location.href = result[1];
                            }else {
                                alert("失败");
                            }
                        }else {
                            alert("失败");
                        }
                    }
                })
            }

            function register(){
                var picPath = "profile-pics/2.jpg";
                var check = document.getElementsByName("picPath");
                for(var i=0;i<check.length;i++){
                    if(check[i].checked){
                       picPath = check[i].value;
                    }
                }
                var password = $("#password").val();
                var checkPassword = $("#checkPassword").val();
                if(password != checkPassword){
                    alert("两次密码输入不一致！");
                    clearPassword();
                    return;
                }
                if(password == ""){
                    alert("密码不能为空！");
                }
                var realName = $("#realName").val();
                var username = $("#username").val();
                var email = $("#email").val();
                $.ajax({
                    type: "post",
                    dataType: "json",
                    async: "false",
                    url: "/register",
                    data: {
                        picPath : picPath,
                        realName : realName,
                        username : username,
                        email : email,
                        password : password
                    },
                    success: function (data){
                        var result = data.split(":");
                        if(2 == result.length){
                            if(1 == result[0]){
                                alert("成功");
                                location.href = "/index";
                            }else {
                                alert(result[1]);
                            }
                        }else {
                            alert("失败");
                        }
                    }
                })
            }

            function clearPassword(){
                $("#password").val("");
                $("#checkPassword").val("");
            }
        </script>

    </body>
</html>
