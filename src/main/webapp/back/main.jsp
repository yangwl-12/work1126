<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    request.setAttribute("path",request.getContextPath());
%>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/boot/css/back.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jqgrid/css/jquery-ui.css">
    <script src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="${pageContext.request.contextPath}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/ajaxfileupload.js"></script>
    <script src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <script charset="UTF-8" src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/echarts/china.js" charset="UTF-8"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <title>持名法舟后台管理系统</title>
    <script>
        KindEditor.ready(function (K) {
            window.editor = K.create('#editor_id1',{
                width:'100%',
                uploadJson:"${pageContext.request.contextPath}/article/uploadImg",
                allowFileManager:true,
                fileManagerJson:"${pageContext.request.contextPath}/article/showAllImgs",
                afterBlur:function () {
                    this.sync();
                }
            });
        });


    </script>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">持名法舟后台管理系统</a>
        </div>
        <div>
            <!--向右对齐-->
            <ul class="nav navbar-nav navbar-right">
                <li><a>欢迎:<span style="color: red">${admin.name}</span></a></li>
                <li><a href="${pageContext.request.contextPath}/admin/safeout">退出登录</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-2">
            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseOne">
                                <h3>用户管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/user.jsp');">用户信息管理</a></li>
                                <li><a  href="javascript:$('#content').load('${pageContext.request.contextPath}/back/echarts.jsp');">用户注册趋势</a></li>
                                <li><a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/map.jsp');">用户注册分布</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo">
                                <h3>轮播图管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/lunbotu.jsp');">轮播图信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree">
                                <h3>上师管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/guru.jsp');">上师信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour">
                               <h3>文章管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/article.jsp');">文章列表</a></li>
                            </ul>
                            <ul class="nav nav-pills nav-stacked">
                                <li><a>文章搜索</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFive">
                                <h3>专辑管理</h3>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li><a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/album.jsp');">专辑信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="content">
        <div class="col-xs-10">
            <div class="container-fluid " >
                <div class="jumbotron">
                    <h2>欢迎使用持名法舟后台管理系统！</h2>
                </div>

            <div id="myCarousel" class="carousel slide">
                <!-- 轮播（Carousel）指标 -->
                <ol class="carousel-indicators">
                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                    <li data-target="#myCarousel" data-slide-to="1"></li>
                    <li data-target="#myCarousel" data-slide-to="2"></li>
                </ol>
                <!-- 轮播（Carousel）项目 -->
                <div class="carousel-inner">
                    <div class="item active">
                        <img src="${path}/img/2019.jpg" alt="First slide">
                        <div class="carousel-caption">标题 1</div>
                    </div>
                    <div class="item">
                        <img src="${path}/img/微信图片_20180716115911.jpg" alt="Second slide">
                        <div class="carousel-caption">标题 2</div>
                    </div>
                    <div class="item">
                        <img src="${path}/img/微信图片_20190603161551.jpg" alt="Third slide">
                        <div class="carousel-caption">标题 3</div>
                    </div>
                </div>
                <!-- 轮播（Carousel）导航 -->
                <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
            </div>
        </div>
        </div>
    </div>
</div>
<div class="panel-footer">
    <h4 style="text-align: center">百知教育 @baizhiedu.com.cn</h4>
</div>

<%--模态框--%>
<div class="modal fade"  role="dialog" id="mymodal1">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">文章信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" enctype="multipart/form-data" id="myform1">
                    <input name="id" id="idd1" type="hidden">
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-6">
                            <input type="text" name="title" value="" class="form-control" id="name1" placeholder="标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">封面上传</label>
                        <div class="col-sm-6">
                            <input type="file" name="articleImg" id="inputfile1">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">所属上师</label>
                        <div class="col-sm-6">
                            <select class="form-control" name="guruId" id="guruList1">

                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-6">
                            <select  name="status" id="statusId">
                                    <option value="1" selected>展示</option>
                                    <option value="2">冻结</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-6 control-label">内容</label>
                        <div class="col-sm-10">
                        <textarea id="editor_id1" name="content">

                        </textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer" id="modal_foot">
<%--                <button type="button" class="btn btn-primary" onclick="sub()">提交</button>--%>
<%--                <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>--%>

            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>

</html>