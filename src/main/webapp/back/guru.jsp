
<%@page isELIgnored="false" pageEncoding="UTF-8" %>
<!doctype html>
<script type="text/javascript">

    $(function () {
        $('#table').jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/guru/findAll",
            datatype:"json",
            colNames:["ID","姓名","法号","头像"],
            colModel:[
                {name:"id",search:false,align: "center"},
                {name:"name",editable:true,align: "center"},
                {name:"fahao",editable:true,search: false,align: "center"},
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
            editurl:"${pageContext.request.contextPath}/guru/allmethod",
            autowidth:true,
            multiselect:true,
        }).navGrid('#page1',//当前分页工具栏
            {add:true,edit:true,del:true,search:true,refresh:true},//开启编辑时的操作
            {closeAfterEdit: true,editCaption: "编辑图片信息",reloadAfterSubmit:true,//对编辑面板的配置对象
                afterSubmit:function (response) {
                    var picId = response.responseJSON.picId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/guru/upload1",
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
                        url:"${pageContext.request.contextPath}/guru/upload",
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
<div class="col-lg-10">
    <table id="table" style="height: 50px"></table>
    <div style="height: 50px" id="page1"></div>
</div>