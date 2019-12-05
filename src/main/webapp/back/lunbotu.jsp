
<%@page isELIgnored="false" pageEncoding="UTF-8" %>
<!doctype html>
<script type="text/javascript">

    <%--function f1(){--%>
    <%--    $.ajax({--%>
    <%--        url:"${pageContext.request.contextPath}/pic/export",--%>
    <%--        datatype:"json",--%>
    <%--        type:"post",--%>
    <%--        success:function (data) {--%>
    <%--            // alert("导出成功");--%>
    <%--        },--%>
    <%--        error:function (data) {--%>
    <%--            alert("导出失败！");--%>
    <%--        }--%>
    <%--    })--%>

    <%--}--%>
    $(function () {
    $('#id1').jqGrid({
        styleUI:"Bootstrap",
        url:"${pageContext.request.contextPath}/pic/findAll1",
        datatype:"json",
        colNames:["ID","标题","状态","描述","创建时间","图片"],
        colModel:[
            {name:"id",search:false,align: "center"},
            {name:"name",editable:true,align: "center"},
            {name:"status",editable: true,edittype:"select",align: "center",editoptions:{value:"1:展示;2:冻结"},
                formatter:function (data) {
                    if(data==1){
                        return "展示";
                    }else {
                        return "冻结";
                    }
                }
            },
            {name:"introduction",editable:true,search: false,align: "center"},
            {name:"create_time",align: "center"},
            {name:"path",editable:true,search:false,
                edittype:"file",
                align:"center",
                editoptions:{enctype:"multipart/form-data"},
                formatter:function (value) {
                    return "<img src='"+value+"' style='width: 80px;'/>";
                }
            },
        ],
        pager:"#pager",
        rowNum:2,
        rowList:[2,5,10,20],
        viewrecords:true,
        mtype:"post",
        caption:"图片列表",
        cellEdit:true,
        editurl:"${pageContext.request.contextPath}/pic/allmethod",
        autowidth:true,
        multiselect:true,
    }).navGrid("#pager",//当前分页工具栏
        {add:true,edit:true,del:true,search:true,refresh:true},//开启编辑时的操作
        {closeAfterEdit: true,editCaption: "编辑图片信息",reloadAfterSubmit:true,//对编辑面板的配置对象
            afterSubmit:function (response) {
                var picId = response.responseJSON.picId;
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/pic/upload1",
                    datatype:"json",
                    type:"post",
                    data:{pid:picId},
                    fileElementId:"path",
                    success:function (data) {
                        $('#id1').trigger("reloadGrid");//刷新表格
                    }
                })
            },
        },
        {closeAfterAdd: true,addCaption: "图片添加",reloadAfterSubmit:true, //添加
            afterSubmit:function (response) {
                var picId = response.responseJSON.picId;
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/pic/upload",
                    datatype:"json",
                    type:"post",
                    data:{pid:picId},
                    fileElementId:"path",
                    success:function (data) {
                        $('#id1').trigger("reloadGrid");
                    }
                })
            }
        },
        {},//删除
        {sopt:['eq','ne','cn']}, //配置搜索条件如何;

         <%--{--%>
         <%--    afterSubmit:function (response) {--%>
         <%--        var picId = response.responseJSON.picId;--%>
         <%--        $.ajaxFileUpload({--%>
         <%--            url:"${pageContext.request.contextPath}/pic/upload",--%>
         <%--            datatype:"json",--%>
         <%--            type:"post",--%>
         <%--            data:{pid:picId},--%>
         <%--            fileElementId:"path",--%>
         <%--            success:function (data) {--%>
         <%--                $('#id1').trigger()("reloadGrid");--%>
         <%--            }--%>
         <%--        })--%>
         <%--    }--%>
         <%--}--%>
        );
    });
</script>
<div class="container-fluid">
<div class="col-lg-10">
<h3>轮播图管理</h3>
<hr>
    <div>
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">轮播图信息</a></li>
            <li role="presentation"><a href="${pageContext.request.contextPath}/pic/export">导出轮播图信息</a></li>
            <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">导出轮播图模板</a></li>
            <li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">导入轮播图信息</a></li>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
            <div class="col-lg-10">
                <table id="id1"></table>
                <div id="pager" style="height: 50px"></div>
            </div>
        </div>

    </div>
</div>
</div>


