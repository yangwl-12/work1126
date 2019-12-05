<%@page pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $(function () {
            // 创建父级JqGrid表格
            $("#table").jqGrid(
                {
                    url :"${pageContext.request.contextPath}/album/findAllf",
                    datatype : "json",
                    colNames : [ '标题', '星级','作者','播音员','专辑简介','章节数','发行时间','插图' ],
                    colModel : [
                        {name : 'title',align:"center",editable:true},
                        {name : 'stars',align: "center",editable:true},
                        {name : 'author',align: "center",editable:true},
                        {name : 'boyin',align: "center",editable:true},
                        {name : 'introduction',align: "center",editable:true},
                        {name : 'episode',align: "center",editable:true},
                        {name : 'releasetime',align: "center",editable:true},
                        {name : 'cover',align: "center",editable:true,search:false,
                            edittype:'file',
                            editoptions:{enctype:"multipart/form-data"},
                            formatter:function (value) {
                                return "<img src='"+value+"' style='width: 80px;'/>";
                            }
                        },
                    ],
                    rowNum : 2,
                    rowList : [ 4, 10, 20, 30 ],
                    pager : '#page',
                    editurl:"${pageContext.request.contextPath}/album/allmethod",
                    sortname : 'id',
                    viewrecords : true,
                    sortorder : "desc",
                    height:500,
                    multiselect : true,
                    // 开启多级表格支持
                    subGrid : true,
                    caption : "专辑章节信息",
                    autowidth:true,
                    styleUI:"Bootstrap",
                    // 重写创建子表格方法
                    subGridRowExpanded : function(subgrid_id, row_id) {
                        addTable(subgrid_id,row_id);
                    },
                    // 删除表格方法
                    subGridRowColapsed : function(subgrid_id, row_id) {

                    }
                });
            $("#table").jqGrid('navGrid', '#page', {
                add : true, edit : true, del : true
            },
                {closeAfterEdit: true,editCaption: "编辑图片信息",reloadAfterSubmit:true,//对编辑面板的配置对象
                    afterSubmit:function (response) {
                        var aId = response.responseJSON.albumId;
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/album/upload122",
                            datatype:"json",
                            type:"post",
                            data:{pid:aId},
                            fileElementId:"cover",
                            success:function (data) {
                                $('#table').trigger("reloadGrid");//刷新表格
                            }
                        })
                    },
                },
                {closeAfterAdd: true,addCaption: "图片添加",reloadAfterSubmit:true, //添加
                    afterSubmit:function (response) {
                        var aId = response.responseJSON.albumId;
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/album/upload",
                            datatype:"json",
                            type:"post",
                            data:{pid:aId},
                            fileElementId:"cover",
                            success:function (data) {
                                $('#table').trigger("reloadGrid");
                            }
                        })
                    }
                },
                {},//删除
                {sopt:['eq','ne','cn']}, //配置搜索条件如何;

            );
        })
        // subgrid_id 下方空间的id  row_id 当前行id数据
        function addTable(subgrid_id,row_id) {
            console.log(row_id)
            // 声明子表格|工具栏id
            var subgridTable = subgrid_id + "table";
            var subgridPage = subgrid_id + "page";
            // 根据下方空间id 创建表格及工具栏
            $("#"+subgrid_id).html("<table id='"+subgridTable+"'></table><div style='height: 50px' id='"+subgridPage+"'></div>")
            // 子表格JqGrid声明
            $("#"+subgridTable).jqGrid({
                url : "${pageContext.request.contextPath}/chapter/findAll?fid="+row_id,
                datatype : "json",
                colNames : [ '标题', '大小','时长','上传时间','音频','操作' ],
                colModel : [
                    {name : "name",align:"center"},
                    {name : "chapterSize",align:"center"},
                    {name : "chapter_time",align:"center"},
                    {name : "fabushjian",align:"center"},
                    {name : "path",align:"center",editable:true,
                        edittype: "file",
                        editoptions: {enctype:"multipart/form-data"}
                    },
                    //{name:"albumId",align:"center"},
                    {name : "options",align:"center",width:400,formatter:function (cellvalue,options,rowObject) {
                            var path = rowObject.path;
                            // return '<span class="glyphicon glyphicon-play-circle" onclick="downm('+path+')"></span>&nbsp;&nbsp;&nbsp;&nbsp;' +
                            //     '<span class="glyphicon glyphicon-arrow-down"onclick="playing('+path+')"></span>';
                            return "<audio controls loop preload='auto'>\n"+
                                "<source src='"+path+"' type='audio/mpeg'>\n"+
                                "<source src='"+path+"' type='audio/ogg'>\n"+
                                "</audio>"
                                ;

                    }},
                ],
                rowNum : 2,
                pager : "#"+subgridPage,
                sortname : 'num',
                sortorder : "asc",
                height : '100%',
                styleUI:"Bootstrap",
                editurl: "${pageContext.request.contextPath}/chapter/allmethod?fid="+row_id,
                multiselect : true,
                autowidth:true
            });
            $("#" + subgridTable).jqGrid('navGrid',
                "#" + subgridPage, {
                    edit : true,
                    add : true,
                    del : true
                },
                {closeAfterEdit: true,editCaption: "编辑图片信息",reloadAfterSubmit:true,//对编辑面板的配置对象
                    afterSubmit:function (response) {
                        var aId = response.responseJSON.chapterId;
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/chapter/upload122",
                            datatype:"json",
                            type:"post",
                            data:{pid:aId},
                            fileElementId:"path",
                            success:function (data) {
                                $('#table').trigger("reloadGrid");//刷新表格
                            }
                        })
                    },
                },
                {closeAfterAdd: true,addCaption: "音频添加", //添加
                    afterSubmit:function (response) {
                        var aId = response.responseJSON.chapterId;
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/chapter/upload",
                            datatype:"json",
                            type:"post",
                            data:{pid:aId},
                            fileElementId:"path",
                            success:function (data) {
                                $('#table').trigger("reloadGrid");
                            }
                        })
                    }
                },
                {},//删除
                {sopt:['eq','ne','cn']}, //配置搜索条件如何;

                );
        }
    });

    function downm(id) {
        alert("down"+id);

    };

    function playing(id) {
        alert("playing"+id);

    };


</script>
<div class="col-lg-10">
<table id="table" style="height: 50px"></table>
<div style="height: 50px" id="page"></div>
</div>