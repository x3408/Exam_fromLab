<%--
  Created by IntelliJ IDEA.
  User: pc10
  Date: 2017/11/1
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="http://localhost:8080/exam/testPaper/submit" method="post" id="paperForm">
    <input name="paperId" type="text" id="paperId"/><br/>
    <input type="text" value="输入选择问题id和答案"/>
    <input name="list[0].questionId"  type="text"/>
    <input name="list[0].questionAnswer"/><br/>
    <input type="text" value="输入选择问题id和答案"/>
    <input name="list[1].questionId"  type="text"/>
    <input name="list[1].questionAnswer"/><br/>
    <input type="text" value="输入判断问题id和答案"/>
    <input name="list[2].questionId" type="text"/>
    <input name="list[2].questionAnswer"/><br/>
    <input type="text" value="输入判断问题id和答案"/>
    <input name="list[3].questionId" id="questionId3" type="text"/>
    <input name="list[3].questionAnswer"/><br/>
    <input type="text" value="输入判断问题id和答案"/>
    <input name="list[4].questionId" id="questionId4" type="text"/>
    <input name="list[4].questionAnswer"/><br/>
    <input type="submit" value="测试"/>
</form>
</body>
</html>
