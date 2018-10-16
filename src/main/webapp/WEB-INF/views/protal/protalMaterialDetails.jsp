<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>资料详情</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/test.css"/>
</head>
<style>
    body {
        font-size: 20px !important;
    }

</style>
<style type="text/css">
    li {
        display: inline;
    }
    td{
        padding-left:25px;
    }
    .table{
        margin-top:200px;
    }
</style>
<body>
<%-- <!--标题-->
 <div class="header">
     <h1 id="paperTitle" class="title"></h1>
     <div class="account">
         <span id="userName">DEMO</span>
         <a id="exit">注销</a>
     </div>
 </div>--%>
<h2 align="center" style="margin-left: 50px;">食品安全管理资料列表页面</h2>
    <div>
<center>
    <table class="table" width="500" height="200" >
        <thead>
        <tr>
            <th><font style="vertical-align: inherit;"><font
                    style="vertical-align: inherit;">时间</font></font></th>
            <th><font style="vertical-align: inherit;"><font
                    style="vertical-align: inherit;">资料主题</font></font></th>
            <th><font style="vertical-align: inherit;"><font
                    style="vertical-align: inherit;">资料备注</font></font></th>
            <th><font style="vertical-align: inherit;"><font
                    style="vertical-align: inherit;">操作</font></font></th>
        </tr>
        </thead>

        <tbody id="detail">


        </tbody>
    </table>
    <ul id="bottom">

    </ul>
</center>
    </div>
</body>
<script src="${pageContext.request.contextPath }/resources/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/update/bootstrap.min.js"></script>
<script type="text/javascript">
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }

    $(document).ready(function () {
        //获取url中参数名为goPage的值
        var goPage = getQueryString("goPage");
        $.ajax({
            url: "${pageContext.request.contextPath }/protal/selectMaterialList",
            type: "POST",
            data: {
                goPage: goPage
            },
            success: function (data) {
                var page = JSON.parse(data);
//                alert(page.content[0].price);
//                alert(page.size);
                for (var i = 0; i < page.content.length; i++) {
                    var trObj = "<tr>";
                    var count = page.size * goPage + i + 1;
                    var hrefObj = page.content[i].coaUrl;
                    hrefObj = encodeURI(hrefObj);
                    trObj += "<td width='25px'>" + fmtDate(page.content[i].createDate) + "</td>";
                    trObj += "<td width='15px'>" + page.content[i].materialTheme + "</td>";
                    trObj += "<td width='15px'>" + page.content[i].materialDescription + "</td>";
                    trObj += "<td width='25px'>" + "<a href='" + page.content[i].materialUrl + " '>查看</a>" + "</td>";
                    $("#detail").append(trObj);
                }


                var liObj2 = "<li>" + "显示" + page.numberOfElements + "条" + "，共 " + page.totalElements + "条" + "</li>";
                $("#left").append(liObj2);

                var liObj = "<li>" + "<a href='${pageContext.request.contextPath}/protal/protalMaterialDetails?goPage=0'>" + "首页" + "</a>" + "</li>";
                if (page.totalPages <= 10) {
                    for (var i = 1; i <= page.totalPages; i++) {
                        var numa = i - 1;
                        liObj += "<li>" + "<a href='${pageContext.request.contextPath}/protal/protalMaterialDetails?goPage=" + numa + "'>" + i + "</a>" + "</li>";
                    }
                } else {
                    var currentPage = page.number + 1;
                    if (currentPage - 4 <= 0) {
                        for (var j = 1; j < 10; j++) {
                            var numb = j - 1;
                            liObj += "<li>" + "<a href='${pageContext.request.contextPath}/protal/protalMaterialDetails?goPage=" + numb + "'>" + j + "</a>" + "</li>";
                        }
                    }
                    if (currentPage + 5 >= page.totalPages) {
                        for (var k = page.totalPages - 9; k < page.totalPages; k++) {
                            var numc = k - 1;
                            liObj += "<li>" + "<a href='${pageContext.request.contextPath}/protal/protalMaterialDetails?goPage=" + numc + "'>" + k + "</a>" + "</li>";
                        }
                    }
                    if (currentPage - 4 > 0 && currentPage + 5 < page.totalPages) {
                        for (var l = currentPage - 4; l < currentPage + 5; l++) {
                            var numd = l - 1;
                            liObj += "<li>" + "<a href='${pageContext.request.contextPath}/protal/protalMaterialDetails?goPage=" + numd + "'>" + l + "</a>" + "</li>";
                        }
                    }

                }
                var end = page.totalPages - 1;
                liObj += "<li>" + "<a href='${pageContext.request.contextPath}/protal/protalMaterialDetails?goPage=" + end + "'>" + "尾页" + "</a>" + "</li>";
                $("#bottom").append(liObj);
            }
        });
    });


    function fmtDate(inputTime) {
        var date = new Date(inputTime);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        h = h < 10 ? ('0' + h) : h;
        var minute = date.getMinutes();
        var second = date.getSeconds();
        minute = minute < 10 ? ('0' + minute) : minute;
        second = second < 10 ? ('0' + second) : second;
        return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
    }
</script>

</html>