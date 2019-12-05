<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>




<script>
        // var goEasy = new GoEasy({
        //     host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        //     appkey: "BS-c30176e0d0ba4970a7db99016925d671", //替换为您的应用appkey
        // });
        // goEasy.subscribe({
        //     channel: "cmfz", //替换为您自己的channel
        //     onMessage: function (message) {
        //         // 手动将 字符串类型转换为 Json类型
        //         var data = JSON.parse(message.content);
        //         alert("Channel:" + message.channel + " content:" + message.content);
        //     }
        // });


    </script>


<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div class="col-lg-10">
<div id="main" style="width: 600px;height:400px;">
</div>
</div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '男女比列'
        },
        tooltip: {},
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["1天","7天","30天","1年"]
        },
        yAxis: {},
        series: [],
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    // Ajax异步数据回显
    $.get("${pageContext.request.contextPath}/user/findSex",function (data) {
        console.log(data)
        myChart.setOption({
            series:[
                {
                    name: '男',
                    type: 'bar',
                    data: [data.man_oneDay,data.man_oneWeek,data.man_oneMonth,data.man_oneYear],
                },{
                    name: '女',
                    type: 'bar',
                    data: [data.woman_oneDay,data.woman_oneWeek,data.woman_oneMonth,data.woman_oneYear],
                }
            ]
        })
    },"json")
</script>