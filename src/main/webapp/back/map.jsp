<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
    <script type="text/javascript">
        $(function () {
            var goEasy = new GoEasy({
                host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
                appkey: "BC-63186ec9498d4f11bfd348f8a8e2773f", //替换为您的应用appkey
            });
            goEasy.subscribe({
                channel:"ywl",
                onMessage:function (message) {
                    var c = JSON.parse(message.content);
                    console.table(c);
                    myChart.setOption({
                        series:[{
                            name: '男',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: c.map
                        },{
                            name: '女',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: c.map
                        }]
                    })

                }
            })

            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('userMap'));
            var option = {
                title: {
                    text: '用户分布图',
                    subtext: '纯属虚构',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['男','女']
                },
                visualMap: {
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],           // 文本，默认为数值文本
                    calculable: true
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: [

                ]
            };
            // 使用刚指定的配置项和数据显示图表。

            $.get("${pageContext.request.contextPath}/user/showUsersLocation",function (data) {
                myChart.setOption({
                    series:[{
                        name: '男',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: data
                    },{
                        name: '女',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: data
                    }]
                })
            },"json")
            myChart.setOption(option);
        })

    </script>



<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div class="col-lg-10">
<div id="userMap" style="width: 600px;height:400px;"></div>
</div>
