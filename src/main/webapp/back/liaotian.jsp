
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script>
        $(function () {
            var goEasy = new GoEasy({
                host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
                appkey: "BC-63186ec9498d4f11bfd348f8a8e2773f", //替换为您的应用appkey
                onConnected: function () {
                    alert("上线一人");
                },
                onDisconnected: function () {
                    alert("下线一人");
                }

            });
            goEasy.subscribe({
                channel: "ywl",
                onMessage: function (message) {
                    var p ='<p>'+message.content+'</p>';
                    $('#heihei').append(p);
                    //$('html, body').animate({scrollTop: $('#heihei').height()}, 50);
                    if(message.content!=null){
                        //var he=$('#heihei').height;
                        $('#heihei')[0].scrollTop=$('#heihei')[0].scrollHeight;
                    }
                },
            });
           /* goEasy.subscribePresence({
                channel: "shuijiao",
                onPresence: function(presenceEvents){
                    console.log("Presence events: ", JSON.stringify(presenceEvents));
                }
            });*/


            $('#btn').click(function () {
                var ss=$('#mes').val();
                //消息发送
                goEasy.publish({
                    channel: "ywl", //替换为您自己的channel
                    message: ss //替换为您想要发送的消息内容
                })


            })
        })


    </script>
</head>
<body>
<div>
    <div style="overflow:scroll;width: 400px; height: 500px" id="heihei"></div>
    <input type="text" id="mes">&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" id="btn">发送</button>
</div>

</body>

