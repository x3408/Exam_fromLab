<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/update/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/update/font-awesome/css/font-awesome.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/update/animate.css" >
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/update/style.css" >
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/update/myStyle.css">
</head>

<body class="gray-bg">
    <div>
        <h1 class="logo-name text-center">巢湖市食品安全作业人员考试系统</h1>
    </div>
    <div class="middle-box text-center loginscreen animated fadeInDown">
        <div>

            <h2>欢迎来到考试管理系统!</h2>
            <form class="m-t"  method="post" action="${pageContext.request.contextPath }/j_spring_security_check" id="loginForm">
                <div class="loginWrap" id="eventForm">
                    <div class="form-group">
                        <input type="text"  class="form-control"    name="username" id="username" placeholder="用户名" required="">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" name="password" id="password" placeholder="密码" required="">
                    </div>
                    <button type="button" class="btn btn-primary block full-width m-b" onclick="checkLogin();" id="eventClick">登录</button>
                    <p class="text-muted text-center col-red"><small>${msg}</small></p>
                </div>
            </form>

        </div>
    </div>

<!-- Mainly scripts -->
<%--<script src="${pageContext.request.contextPath}/resources/js/update/jquery-3.1.1.min.js "></script>--%>
    <script src="${pageContext.request.contextPath }/resources/js/jquery-1.11.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/update/bootstrap.min.js"></script>


<script>

    $(function(){
        $('#eventForm').find('input').on('keyup', function(event) {/* 增加回车提交功能 */
            if (event.keyCode == '13' && $("#username").val() != '' && $("#password").val() != '') {
                checkLogin();
            }
        });
    });
    function checkLogin() {
        $.ajax({
            url : '${pageContext.request.contextPath }/ldap',
            cache : false,
            type : "post",
            dataType : "json",
            data : $("#loginForm").serialize(),
            success : function(r) {
               // alert(r.success);
                if(r.success&&r.code=='02'){
                    $('#password').val('');
                }else{
                    if(r.code=='-1'){
                        $('#msg').html("用户名或密码错误");
                        return;
                    }
                }
                $('#loginForm').submit();
            }
        });

    }
</script>
<script type="text/javascript">
    $(function(){
        distime();
    })

    function distime(){
        var time = new Date( ); //获得当前时间
        var year = time.getFullYear( );
        $('#show').html("江苏风云科技服务有限公司 © 2016-"+year);

    }
</script>

</body>

</html>