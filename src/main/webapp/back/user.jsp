
<%@page isELIgnored="false" pageEncoding="UTF-8" %>
<!doctype html>
<script type="text/javascript">

    $(function () {
        $('#id1').jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/user/findAll",
            datatype:"json",
            colNames:["ID","姓名","性别","电话","地址","状态","昵称","创建时间","最后登录时间","头像"],
            colModel:[
                {name:"id",search:false,align: "center"},
                {name:"name",editable:true,align: "center"},
                {name:"sex",editable:true,align: "center"},
                {name:"phone",editable:true,search: false,align: "center"},
                {name:"address",editable:true,align: "center"},
                {name:"status",editable:true,align: "center"},
                {name:"nickName",editable:true,align: "center"},
                {name:"createTime",editable:true,align: "center"},
                {name:"lastloginTime",editable:true,align: "center"},
                {name:"avatar",editable:true,search:false,
                    edittype:"file",
                    align:"center",
                    editoptions:{enctype:"multipart/form-data"},
                    formatter:function (value) {
                        return "<img src='"+value+"' style='width: 80px;'/>";
                    }
                },
            ],
            pager:"#page1",
            rowNum:2,
            rowList:[2,5,10,20],
            viewrecords:true,
            mtype:"post",
            caption:"上师列表",
            cellEdit:true,
            editurl:"${pageContext.request.contextPath}/user/allmethod",
            autowidth:true,
            multiselect:true,
        }).navGrid('#page1',//当前分页工具栏
            {add:true,edit:true,del:true,search:true,refresh:true},//开启编辑时的操作
            {closeAfterEdit: true,editCaption: "编辑图片信息",reloadAfterSubmit:true,//对编辑面板的配置对象
                afterSubmit:function (response) {
                    var picId = response.responseJSON.picId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/user/upload1",
                        datatype:"json",
                        type:"post",
                        data:{pid:picId},
                        fileElementId:"avatar",
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
                        url:"${pageContext.request.contextPath}/user/upload",
                        datatype:"json",
                        type:"post",
                        data:{pid:picId},
                        fileElementId:"avatar",
                        success:function (data) {
                            $('#id1').trigger("reloadGrid");
                        }
                    })
                }
            },
            {},//删除
            {sopt:['eq','ne','cn']}, //配置搜索条件如何;

        );
    });
</script>
<div class="container-fluid">
    <div class="col-lg-10">
        <div class="page-header">
            <h2><strong>用户管理</strong></h2>
        </div>
        <ul class="nav nav-tabs">
            <li class="active"><a>用户信息管理</a></li>
            <li><a>用户注册趋势图</a></li>
            <li><a>用户注册分布</a></li>

        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
            <table id="id1"></table>
            <div id="page1" style="height: 50px"></div>
        </div>

    </div>
</div>