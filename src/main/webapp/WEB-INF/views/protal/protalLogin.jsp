<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: pc10
  Date: 2017/10/30
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>考生登录页面</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/ProtalLogin.css"/>
</head>
<style>
    body {
        font-size: 20px !important;
    }
</style>
<body>
<div class="header">
    <h1 id="paperTitle" class="">
        食品安全作业人员考试系统
    </h1>
</div>
<div class="main">
    <form id="loginForm">
        <div class="dialog">
            <div class="title">
                登录账户
            </div>
            <div class="content">
                <div class="input">
                    <input class="i1" type="text" name="userName" id="userName" value="" placeholder="身份证号码"/>
                    <div class="tip"></div>
                </div>
                <div class="input ">
                    <input class="i2" type="password" name="passWord" id="passWord" value="" placeholder="密码"/>
                    <div class="tip"></div>
                </div>
                <div class="footer">
                    <a id="sendAccount" class="sendAccount" href="javascript:void(0);" style="display: none">
                        <div class="foot">登录</div>
                    </a>
                </div>
            </div>
            <input name="slectId" type="radio" value="0" id="parotalExam"/>考试
            <input name="slectId" type="radio" value="1" id="protalMaterial"/>资料
        </div>
    </form>
</div>
<!-- Mainly scripts -->
<script src="${pageContext.request.contextPath }/resources/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/indexLogin.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/update/bootstrap.min.js"></script>

<script>
    $('#sendAccount').click(function () {
        $.ajax({
            cache: true,
            type: "POST",
            url: "${pageContext.request.contextPath}/protal/checkProtalLogin",
            data: $('#loginForm').serialize(),
            async: false,
            error: function (request) {
                alert("Connection error");
            },
            success: function (data) {
                if (data.success) {
                    if (document.getElementById("parotalExam").checked) {
                        window.open("${pageContext.request.contextPath}/protal/protalPaperDetails", "_self");
                    }else if(document.getElementById("protalMaterial").checked){
                        window.open(("${pageContext.request.contextPath}/protal/protalMaterialDetails"),"_self");
                    }else{
                        window.open("${pageContext.request.contextPath}/protal/protalPaperDetails", "_self");
                    }
                } else {
                    alert("请将账号密码填写完整");
                }
            }
        });
    });
</script>

</body>
</html>
