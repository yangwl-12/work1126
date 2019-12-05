<%@page pageEncoding="UTF-8" isELIgnored="false" %>


<script>
    // 1. 获取上师信息 在表单回显
    $.get("${pageContext.request.contextPath}/guru/findAll",function (data) {
        var option = "<option value='0'>通用文章</option>";
        data.forEach(function (guru) {
            option += "<option value='"+guru.id+"'>"+guru.fahao+"</option>"
            // if (guru.id=="1"){
            //     option += "<option selected value='"+guru.id+"'>"+guru.fahao+"</option>"
            // }
        });
        $("#guruList1").html(option);
    },"json");
    //2.异步提交数据和上传文件
    function sub() {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/article/upload",
            datatype:"json",
            type:"post",
            fileElementId:"inputfile1",
            data:{id:$('#idd1').val(),title:$('#name1').val(),content:$('#editor_id1').val(),guruId:$('#guruList1').val(),status:$('#statusId').val()},

            success:function (data) {
                $('#id1').trigger("reloadGrid");//刷新表格
                alert("添加成功！");
            }
        })
    }
    //更新文章
    function updateArticle() {
        $.ajaxFileUpload({
            url:"${pageContext.request.contextPath}/article/update",
            datatype:"json",
            type:"post",
            fileElementId:"inputfile1",
            // ajaxFileUpload 不支持序列化数据上传id=111&&title="XXX"
            //                只支持 Json格式上传数据
            // 解决方案 : 1.更改 ajaxFileUpload 源码 2. 手动封装Json格式
            data:{id:$('#idd1').val(),title:$('#name1').val(),content:$('#editor_id1').val(),guruId:$('#guruList1').val(),status:$('#statusId').val()},
            success:function (data) {
                $('#id1').trigger("reloadGrid");//刷新表格
                alert("更新成功！");
            }
        })
    }

    // 打开模态框
    function addArticle() {
        // 清除表单内数据
        $("#myform1")[0].reset();
        // kindeditor 提供的数据回显方法  通过"" 将内容设置为空串
        KindEditor.html("#editor_id1", "");
        $("#modal_foot").html("<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>"+
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"sub()\" data-dismiss=\"modal\">添加1</button>")
        $("#mymodal1").modal("show");
       // KindEditor.html("#editor_id", "");
    };
    // 编辑文章
    function showModel(id) {

        // 返回指定行的数据，返回数据类型为name:value，name为colModel中的名称，value为所在行的列的值，如果根据rowid找不到则返回空。在编辑模式下不能用此方法来获取数据，它得到的并不是编辑后的值
        var data = $("#id1").jqGrid("getRowData",id);
        console.log(data);
        $("#name1").val(data.title);
        $("#statusId").val(data.status);
        $('#guruList1').val(data.guruId);
        $("#idd1").val(data.id);
        // KindEditor 中的赋值方法 参数1: kindeditor文本框 的id
        KindEditor.html("#editor_id1",data.content);
        $("#modal_foot").html("<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>"+
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"updateArticle()\" data-dismiss=\"modal\">修改1</button>")
        $("#mymodal1").modal("show");
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
    }

    $(function () {
        $('#id1').jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/article/findAll",
            datatype:"json",
            colNames:["id","标题","封面","内容","状态","创建时间","所属上师","操作"],
            colModel:[
                {name:"id",hidden: true},
                {name:"title",editable:true,align: "center"},
                {name:"cover",align:"center",
                    formatter:function (value) {
                        return "<img src='"+value+"' style='width: 80px;'/>";
                    }
                },
                {name:"content",editable:true,align: "center"},
                {name:"status",editable: true,edittype:"select",align: "center",editoptions:{value:"1:展示;2:冻结"},
                    formatter:function (data) {
                        if(data==1){
                            return "展示";
                        }else {
                            return "冻结";
                        }
                    }
                },
                {name:"create_time",align: "center"},
                {name:"guruId",align: "center",hidden:true},
                {name:"options",formatter:function (cellvalue,options,rowObject) {
                        console.log(rowObject)
                        return  "<a onclick=showModel('"+rowObject.id+"')><span class='glyphicon glyphicon-list' >更新</span></a>";
                    }}

            ],
            pager:"#pager",
            rowNum:2,
            rowList:[2,5,10,20],
            viewrecords:true,
            mtype:"post",
            caption:"文章列表",
            cellEdit:true,
            editurl:"${pageContext.request.contextPath}/article/allmethod",
            autowidth:true,
            multiselect:true,
        }).navGrid("#pager",//当前分页工具栏
            {add:false,edit:false,del:true,search:true,refresh:true},//开启编辑时的操作
            {closeAfterEdit: true,editCaption: "编辑文章信息",reloadAfterSubmit:true,//对编辑面板的配置对象
                <%--afterSubmit:function (response) {--%>
                <%--    var picId = response.responseJSON.picId;--%>
                <%--    $.ajaxFileUpload({--%>
                <%--        url:"${pageContext.request.contextPath}/pic/upload1",--%>
                <%--        datatype:"json",--%>
                <%--        type:"post",--%>
                <%--        data:{pid:picId},--%>
                <%--        fileElementId:"path",--%>
                <%--        success:function (data) {--%>
                <%--            $('#id1').trigger("reloadGrid");//刷新表格--%>
                <%--        }--%>
                <%--    })--%>
                <%--},--%>
            },
            {closeAfterAdd: true,addCaption: "文章添加",reloadAfterSubmit:true, //添加
                <%--afterSubmit:function (response) {--%>
                <%--    var picId = response.responseJSON.picId;--%>
                <%--    $.ajaxFileUpload({--%>
                <%--        url:"${pageContext.request.contextPath}/pic/upload",--%>
                <%--        datatype:"json",--%>
                <%--        type:"post",--%>
                <%--        data:{pid:picId},--%>
                <%--        fileElementId:"path",--%>
                <%--        success:function (data) {--%>
                <%--            $('#id1').trigger("reloadGrid");--%>
                <%--        }--%>
                <%--    })--%>
                <%--}--%>
            },
            {},//删除
            {sopt:['eq','ne','cn']}, //配置搜索条件如何;

        );
    });

</script>


<div class="container-fluid">
    <div class="col-lg-10">
        <div class="page-header">
            <h2><strong>文章管理</strong></h2>
        </div>
        <ul class="nav nav-tabs">
            <li class="active"><a>文章列表</a></li>
            <li><a onclick="addArticle()">添加文章</a></li>
        </ul>
            <!-- Tab panes -->
            <div class="tab-content">
                    <table id="id1"></table>
                    <div id="pager" style="height: 50px"></div>
            </div>

    </div>
</div>



