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

        <title>新建文章</title>
            
        <!-- CSS -->
        <link href="${BASE}/asset/css/bootstrap.min.css" rel="stylesheet">
        <link href="${BASE}/asset/css/animate.min.css" rel="stylesheet">
        <link href="${BASE}/asset/css/font-awesome.min.css" rel="stylesheet">
        <link href="${BASE}/asset/css/form.css" rel="stylesheet">
        <link href="${BASE}/asset/css/calendar.css" rel="stylesheet">
        <link href="${BASE}/asset/css/media-player.css" rel="stylesheet">
        <link href="${BASE}/asset/css/style.css" rel="stylesheet">
        <link href="${BASE}/asset/css/icons.css" rel="stylesheet">
        <link href="${BASE}/asset/css/generics.css" rel="stylesheet">
    </head>
    <body id="skin-tectile">
        <header id="header" class="media">
            <a href="" id="menu-toggle"></a> 
            <a class="logo pull-left" href="/admin_article/article_list_view">知识点 LPPoint 1.0</a>
            
            <div class="media-body">
                <div class="media" id="top-menu">
                    <div class="pull-left tm-icon">
                        <a data-drawer="messages" class="drawer-toggle" href="">
                            <i class="sa-top-message"></i>
                            <i class="n-count animated">5</i>
                            <span>Messages</span>
                        </a>
                    </div>

                    <div id="time" class="pull-right">
                        <span id="hours"></span>
                        :
                        <span id="min"></span>
                        :
                        <span id="sec"></span>
                    </div>

                    <div class="media-body">
                        <input type="text" class="main-search">
                    </div>
                </div>
            </div>
        </header>
        
        <div class="clearfix"></div>
        
        <section id="main" class="p-relative" role="main">
            
            <!-- Sidebar -->
            <aside id="sidebar">
                
                <!-- Sidbar Widgets -->
                <div class="side-widgets overflow">
                    <!-- Profile Menu -->
                    <div class="text-center s-widget m-b-25 dropdown" id="profile-menu">
                        <a href="" data-toggle="dropdown">
                            <img class="profile-pic animated" src="${BASE}/asset/img/profile-pic.jpg" alt="">
                        </a>
                        <ul class="dropdown-menu profile-menu">
                            <li><a href="">My Profile</a> <i class="icon left">&#61903;</i><i class="icon right">&#61815;</i></li>
                            <li><a href="">Messages</a> <i class="icon left">&#61903;</i><i class="icon right">&#61815;</i></li>
                            <li><a href="">Settings</a> <i class="icon left">&#61903;</i><i class="icon right">&#61815;</i></li>
                            <li><a href="">Sign Out</a> <i class="icon left">&#61903;</i><i class="icon right">&#61815;</i></li>
                        </ul>
                        <h4 class="m-0">灵派coder</h4>
                        @LingPaicoder
                    </div>
                    
                    <!-- Calendar -->
                    <div class="s-widget m-b-25">
                        <div id="sidebar-calendar"></div>
                    </div>
                    
                    <!-- Feeds -->
                    <div class="s-widget m-b-25">
                        <h2 class="tile-title">
                           
                        </h2>
                        
                        <div class="s-widget-body">
                            <div id="news-feed"></div>
                        </div>
                    </div>
                    
                    <!-- Projects -->
                    <div class="s-widget m-b-25">
                        <h2 class="tile-title">
                            Projects on going
                        </h2>
                        
                        <div class="s-widget-body">
                            <div class="side-border">
                                <small>LPPoint</small>
                                <div class="progress progress-small">
                                     <a href="#" data-toggle="tooltip" title="" class="progress-bar tooltips progress-bar-danger" style="width: 60%;" data-original-title="60%">
                                          <span class="sr-only">60% Complete</span>
                                     </a>
                                </div>
                            </div>
                            <div class="side-border">
                                <small>LPAgile</small>
                                <div class="progress progress-small">
                                     <a href="#" data-toggle="tooltip" title="" class="tooltips progress-bar progress-bar-info" style="width: 43%;" data-original-title="43%">
                                          <span class="sr-only">43% Complete</span>
                                     </a>
                                </div>
                            </div>
                            <div class="side-border">
                                <small>LPServer</small>
                                <div class="progress progress-small">
                                     <a href="#" data-toggle="tooltip" title="" class="tooltips progress-bar progress-bar-warning" style="width: 81%;" data-original-title="81%">
                                          <span class="sr-only">81% Complete</span>
                                     </a>
                                </div>
                            </div>
                            <div class="side-border">
                                <small>LPThink</small>
                                <div class="progress progress-small">
                                     <a href="#" data-toggle="tooltip" title="" class="tooltips progress-bar progress-bar-success" style="width: 10%;" data-original-title="10%">
                                          <span class="sr-only">10% Complete</span>
                                     </a>
                                </div>
                            </div>
                            <div class="side-border">
                                <small>LPPower</small>
                                <div class="progress progress-small">
                                     <a href="#" data-toggle="tooltip" title="" class="tooltips progress-bar progress-bar-success" style="width: 95%;" data-original-title="95%">
                                          <span class="sr-only">95% Complete</span>
                                     </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <!-- Side Menu -->
                <ul class="list-unstyled side-menu">
                    <li>
                        <a class="sa-side-home" href="index.html">
                            <span class="menu-item">回到主页</span>
                        </a> 
                    </li>
                    <li class="dropdown active">
                        <a class="sa-side-page" href="">
                            <span class="menu-item">我的文章</span>
                        </a>
                    </li>
                    <li class="dropdown">
                        <a class="sa-side-form" href="">
                            <span class="menu-item">草稿管理</span>
                        </a>
                    </li>
                    <li>
                        <a class="sa-side-widget" href="content-widgets.html">
                            <span class="menu-item">粉丝管理</span>
                        </a>
                    </li>
                    <li>
                        <a class="sa-side-chart" href="charts.html">
                            <span class="menu-item">图表分析</span>
                        </a>
                    </li>
                    <!-- <li>
                        <a class="sa-side-delete" href="dustbin.html">
                            <span class="menu-item">垃圾箱</span>
                        </a>
                    </li> -->
                </ul>
            </aside>
        
            <section id="content" class="container">
            
                <!-- Messages Drawer -->
                <div id="messages" class="tile drawer animated">
                    <div class="listview narrow">
                        <div class="media">
                            <a href="">Send a New Message</a>
                            <span class="drawer-close">&times;</span>
                            
                        </div>
                        <div class="overflow" style="height: 254px">
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${BASE}/asset/img/profile-pics/1.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Nadin Jackson - 2 Hours ago</small><br>
                                    <a class="t-overflow" href="">Mauris consectetur urna nec tempor adipiscing. Proin sit amet nisi ligula. Sed eu adipiscing lectus</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${BASE}/asset/img/profile-pics/2.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">David Villa - 5 Hours ago</small><br>
                                    <a class="t-overflow" href="">Suspendisse in purus ut nibh placerat Cras pulvinar euismod nunc quis gravida. Suspendisse pharetra</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${BASE}/asset/img/profile-pics/3.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Harris worgon - On 15/12/2013</small><br>
                                    <a class="t-overflow" href="">Maecenas venenatis enim condimentum ultrices fringilla. Nulla eget libero rhoncus, bibendum diam eleifend, vulputate mi. Fusce non nibh pulvinar, ornare turpis id</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${BASE}/asset/img/profile-pics/4.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Mitch Bradberry - On 14/12/2013</small><br>
                                    <a class="t-overflow" href="">Phasellus interdum felis enim, eu bibendum ipsum tristique vitae. Phasellus feugiat massa orci, sed viverra felis aliquet quis. Curabitur vel blandit odio. Vestibulum sagittis quis sem sit amet tristique.</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${BASE}/asset/img/profile-pics/1.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Nadin Jackson - On 15/12/2013</small><br>
                                    <a class="t-overflow" href="">Ipsum wintoo consectetur urna nec tempor adipiscing. Proin sit amet nisi ligula. Sed eu adipiscing lectus</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${BASE}/asset/img/profile-pics/2.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">David Villa - On 16/12/2013</small><br>
                                    <a class="t-overflow" href="">Suspendisse in purus ut nibh placerat Cras pulvinar euismod nunc quis gravida. Suspendisse pharetra</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${BASE}/asset/img/profile-pics/3.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Harris worgon - On 17/12/2013</small><br>
                                    <a class="t-overflow" href="">Maecenas venenatis enim condimentum ultrices fringilla. Nulla eget libero rhoncus, bibendum diam eleifend, vulputate mi. Fusce non nibh pulvinar, ornare turpis id</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${BASE}/asset/img/profile-pics/4.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Mitch Bradberry - On 18/12/2013</small><br>
                                    <a class="t-overflow" href="">Phasellus interdum felis enim, eu bibendum ipsum tristique vitae. Phasellus feugiat massa orci, sed viverra felis aliquet quis. Curabitur vel blandit odio. Vestibulum sagittis quis sem sit amet tristique.</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${BASE}/asset/img/profile-pics/5.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Wendy Mitchell - On 19/12/2013</small><br>
                                    <a class="t-overflow" href="">Integer a eros dapibus, vehicula quam accumsan, tincidunt purus</a>
                                </div>
                            </div>
                        </div>
                        <div class="media text-center whiter l-100">
                            <a href=""><small>VIEW ALL</small></a>
                        </div>
                    </div>
                </div>
                
                <!-- Notification Drawer -->
                <div id="notifications" class="tile drawer animated">
                    <div class="listview news narrow">
                        <div class="media">
                            <a href="">Notification Settings</a>
                            <span class="drawer-close">&times;</span>
                        </div>
                        <div class="overflow" style="height: 254px">
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${BASE}/asset/img/profile-pics/1.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Nadin Jackson - 2 Hours ago</small><br>
                                    <a class="news-title" href="">Mauris consectetur urna nec tempor adipiscing. Proin sit amet nisi ligula. Sed eu adipiscing lectus</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${BASE}/asset/img/profile-pics/2.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">David Villa - 5 Hours ago</small><br>
                                    <a class="news-title" href="">Suspendisse in purus ut nibh placerat Cras pulvinar euismod nunc quis gravida. Suspendisse pharetra</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${BASE}/asset/img/profile-pics/3.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Harris worgon - On 15/12/2013</small><br>
                                    <a class="news-title" href="">Maecenas venenatis enim condimentum ultrices fringilla. Nulla eget libero rhoncus, bibendum diam eleifend, vulputate mi. Fusce non nibh pulvinar, ornare turpis id</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${BASE}/asset/img/profile-pics/4.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Mitch Bradberry - On 14/12/2013</small><br>
                                    <a class="news-title" href="">Phasellus interdum felis enim, eu bibendum ipsum tristique vitae. Phasellus feugiat massa orci, sed viverra felis aliquet quis. Curabitur vel blandit odio. Vestibulum sagittis quis sem sit amet tristique.</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${BASE}/asset/img/profile-pics/1.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">Nadin Jackson - On 15/12/2013</small><br>
                                    <a class="news-title" href="">Ipsum wintoo consectetur urna nec tempor adipiscing. Proin sit amet nisi ligula. Sed eu adipiscing lectus</a>
                                </div>
                            </div>
                            <div class="media">
                                <div class="pull-left">
                                    <img width="40" src="${BASE}/asset/img/profile-pics/2.jpg" alt="">
                                </div>
                                <div class="media-body">
                                    <small class="text-muted">David Villa - On 16/12/2013</small><br>
                                    <a class="news-title" href="">Suspendisse in purus ut nibh placerat Cras pulvinar euismod nunc quis gravida. Suspendisse pharetra</a>
                                </div>
                            </div>
                        </div>
                        <div class="media text-center whiter l-100">
                            <a href="">VIEW ALL</a>
                        </div>
                    </div>
                </div>
                
                
                <!-- Breadcrumb -->
                <ol class="breadcrumb hidden-xs">
                    <li><a href="#">Home</a></li>
                    <li><a href="#">Typography</a></li>
                    <li class="active">Data</li>
                </ol>
                
                <h4 class="page-title">新建文章</h4>
                                
                <div class="block-area">
                    <div class="row m-container">

                        <div class="col-md-12">

                            <!-- 新建文章 -->

                            <div class="tile">
                                <h2 class="tile-title">文章封面</h2>
                                <form role="form" class="p-15">
                                    <div class="form-group m-b-15">
                                        <label>请选择图片</label>
                                        <div class="fileupload fileupload-new" data-provides="fileupload">
                                            <div class="fileupload-preview thumbnail form-control" name="coverImg" id="coverImg"></div>
                        
                                            <div>
                                                <span class="btn btn-file btn-alt btn-sm">
                                                    <span class="fileupload-new">选择图片</span>
                                                    <span class="fileupload-exists">修改</span>
                                                    <input type="file" />
                                                </span>
                                                <a href="#" class="btn fileupload-exists btn-sm" data-dismiss="fileupload">删除</a>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>

                            <div class="tile">
                                <h2 class="tile-title">文章内容</h2>

                                <form role="form" class="p-15">
                                
                                    <div class="form-group m-b-15">
                                        <label>标题</label>
                                        <input type="text" class="form-control input-sm" name="title" id="title">
                                    </div>
                                    
                                    <div class="form-group m-b-15">
                                        <label>摘要</label>
                                        <input type="text" class="form-control input-sm" name="summary" id="summary">
                                    </div>

                                    <div class="form-group m-b-15">
                                        <label>类别</label>
                                        <input type="text" class="form-control input-sm" name="types" id="types" placeholder="格式如：1,2">
                                    </div>
                                    
                                    <div class="form-group m-b-15">
                                        <label>Post Content</label>
                                        <div class="wysiwye-editor" name="articleContent" id="articleContent"></div>
                                    </div>
                    
                                    <button type="button" class="btn btn-sm btn-alt" onclick="empty()">清空</button>
                                </form>
                            </div>

                            <button type="button" class="btn btn-lg btn-alt" onclick="addArticle()">提交</button>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <button type="button" class="btn btn-lg btn-alt">存入草稿</button>

                        </div>

                        <div class="clearfix"></div>

                        
                        <br/>
                        <br/>
                        <br/>
                        <br/>
                        <br/>

                    </div>

                </div>
            </section>
        </section>
        
        <!-- Javascript Libraries -->
        <!-- jQuery -->
        <script src="${BASE}/asset/js/jquery.min.js"></script> <!-- jQuery Library -->
        <script src="${BASE}/asset/js/jquery-ui.min.js"></script> <!-- jQuery UI -->

        <!-- Bootstrap -->
        <script src="${BASE}/asset/js/bootstrap.min.js"></script>
        
        <!-- Form Related -->
        <script src="${BASE}/asset/js/select.min.js"></script> <!-- Custom Select -->
        <script src="${BASE}/asset/js/icheck.js"></script> <!-- Custom Checkbox + Radio -->
        
        <!-- Text Editor -->
        <script src="${BASE}/asset/js/editor.min.js"></script> <!-- WYSIWYG Editor -->
        
        <!-- Media -->
        <script src="${BASE}/asset/js/media-player.min.js"></script> <!-- Video Player -->
        
        <!-- UX -->
        <script src="${BASE}/asset/js/scroll.min.js"></script> <!-- Custom Scrollbar -->
        
        <!-- Other -->
        <script src="${BASE}/asset/js/calendar.min.js"></script> <!-- Calendar -->
        <script src="${BASE}/asset/js/feeds.min.js"></script> <!-- News Feeds -->
        
        <script src="${BASE}/asset/js/fileupload.min.js"></script> <!-- File Upload -->
        
        <!-- All JS functions -->
        <script src="${BASE}/asset/js/functions.js"></script>
        
        <script type="text/javascript">
            $(document).ready(function() {
                
                //Calendar
                (function(){
                    var date = new Date();
                    var d = date.getDate();
                    var m = date.getMonth();
                    var y = date.getFullYear();
                    $('#calendar').fullCalendar({
                        header: {
                             center: '',
                             right: 'prev, next',
                             left: 'title'
                        },
    
                        selectable: true,
                        selectHelper: true,
                        editable: true,
                        events: [
                            {
                                title: 'Hangout with friends',
                                start: new Date(y, m, 1),
                                end: new Date(y, m, 2)
                            },
                            {
                                title: 'Meeting with client',
                                start: new Date(y, m, 10),
                                allDay: true
                            },
                            {
                                title: 'Repeat Event',
                                start: new Date(y, m, 18),
                                allDay: true
                            },
                            {
                                title: 'Semester Exam',
                                start: new Date(y, m, 20),
                                end: new Date(y, m, 23)
                            },
                            {
                                title: 'Soccor match',
                                start: new Date(y, m, 5),
                                end: new Date(y, m, 6)
                            },
                            {
                                title: 'Coffee time',
                                start: new Date(y, m, 21),
                            },
                            {
                                title: 'Job Interview',
                                start: new Date(y, m, 5),
                            }
                        ],
                         
                        //On Day Select
                        select: function(start, end, allDay) {
                            $('#addNew-event').modal('show');   
                            $('#addNew-event input:text').val('');
                            $('#getStart').val(start);
                            $('#getEnd').val(end);
                        },
                         
                        eventResize: function(event,dayDelta,minuteDelta,revertFunc) {
                            $('#editEvent').modal('show');
    
                            var info =
                                "The end date of " + event.title + "has been moved " +
                                dayDelta + " days and " +
                                minuteDelta + " minutes."
                            ;
                            
                            $('#eventInfo').html(info);
                    
                    
                            $('#editEvent #editCancel').click(function(){
                                 revertFunc();
                            }) 
                        }
                    });
                    
                    $('body').on('click', '#addEvent', function(){
                         var eventForm =  $(this).closest('.modal').find('.form-validation');
                         eventForm.validationEngine('validate');
                         
                         if (!(eventForm).find('.formErrorContent')[0]) {
                              
                              //Event Name
                              var eventName = $('#eventName').val();
    
                              //Render Event
                              $('#calendar').fullCalendar('renderEvent',{
                                   title: eventName,
                                   start: $('#getStart').val(),
                                   end:  $('#getEnd').val(),
                                   allDay: true,
                              },true ); //Stick the event
                              
                              $('#addNew-event form')[0].reset()
                              $('#addNew-event').modal('hide');
                         } 
                    });
                    
                    //Calendar views
                    $('body').on('click', '.calendar-actions > li > a', function(e){
                        e.preventDefault();
                        var dataView = $(this).attr('data-view');
                        $('#calendar').fullCalendar('changeView', dataView);
                        
                        //Custom scrollbar
                        var overflowRegular, overflowInvisible = false;
                        overflowRegular = $('.overflow').niceScroll();     
                    });      
                })();
            });

            function empty(){
                if(confirm("确认清空么？")){
                    $("#title").val("");
                    $("#summary").val("");
                    $(".note-editable").html("");
                }
            }
            
            function addArticle(){
                var coverImg = $("#coverImg").html();
                var title = $("#title").val();
                var summary = $("#summary").val();
                var types = $("#types").val();
                var articleContent = $(".note-editable").html();
                $.ajax({
                    type: "post",
                    dataType: "json",
                    async: "false",
                    url: "/admin_article/article_add",
                    data: {
                        coverImg : coverImg,
                        title : title,
                        summary : summary,
                        types : types,
                        articleContent : articleContent
                    },
                    success: function (data){
                        if(data){
                            alert("成功");
                            location.href = "/admin_article/article_list_view";
                        }else {
                            alert("失败");
                        }
                    }
                })
            }
            
       </script>
    </body>
</html>

