<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>试卷详情</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/test.css"/>
</head>
<style>
    body{
        font-size: 20px!important;
    }
</style>
<body>
<form id="paperForm">
<!--标题-->
<div class="header">
    <input type="hidden" id="paperId" name="paperId" value="">
    <h1 id="paperTitle" class="title"></h1>
    <div class="account">
        <span id="userName"></span>
        <a id="exit">注销</a>
    </div>
</div>
<!--题目-->
<!--选择题-->
<div class="question">
    <div  class="select">
        <div class="title1"><i class="radius"></i>[选择题]</div>
        <div id="choice" class="">
        </div>
    </div>
    <div  class="select">
        <div class="title1"><i class="radius"></i>[多选题]</div>
        <div id="multi" class="">
        </div>
    </div>
    <!--判断题-->
    <div  class="select">
        <div class="title1"><i class="radius"></i>[判断题]</div>
        <div id="judge" class="">
        </div>
    </div>
</div>
<div class="submit">
    <a id="submit" href="javascript:void(0);">提交答案</a>
</div>
<div class="dialog" id="dialog">
    <h4>答题详情</h4>
    <ul id="testGrade" class="grade">
        <li id="totalScope">满分：100分</li>
        <li id="getScope">总得分：88分</li>
        <li id="getSelectScope">选择题得分：44分</li>
        <li id="getMultiScope">选择题得分：44分</li>
        <li id="getJudgeScope">判断题得分：44分</li>
    </ul>
    <a id="ensure"  href="javascript:void(0);">确定</a>
</div>
</form>
<!-- Mainly scripts -->
<script src="${pageContext.request.contextPath }/resources/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/update/bootstrap.min.js"></script>
<script>
    //保存题目正确答案
    var rightAnswer=new Array();
    $(document).ready(function(){
        $.ajax({
            <%--url: "${pageContext.request.contextPath}/exam/testPaper/producePlus",--%>
            url: "${pageContext.request.contextPath}/exam/testPaper/produceBySql",
            type: "GET",
            dataType: "json",
            cache : false,
            contentType: "application/json",
            success: function (data) {
                if(data.success) {
                    var quesions = data.obj.questions;
                    var selectNum = 0;      //选择题
                    var multiNum = 0;       //多选题
                    var judgeNum = 0;
                    var total = 0;
                    var judgeQuestionObj='';
                    var multiQuestionObj='';
                    var selectQuestionObj='';
                    $("#paperId").val(data.obj.id);
                    var temp=data.obj.paperTilte;
                    var paperTitle=document.getElementById("paperTitle");
                    paperTitle.innerText=temp;
                    $("#userName").html(data.obj.user.userName);
                    $.each(quesions, function (index, item) {
                        //循环获取数据
                        if(quesions[index].questionType==0){
                            selectNum = selectNum + 1;
                            selectQuestionObj +='<div class="problem selectIndex">' +
                                '<input  type="hidden" name="list[' + total + '].questionId"  value="'+quesions[index].id+'" />' +
                                '<input  type="hidden" name="list[' + total + '].questionAnswer"  id="select'+selectNum+'" />' +
                                '<span class="tip"></span>'+
                                '<h4 class="questionTitle">'+selectNum+  '. '+quesions[index].questionTitle+'</h4>' +
                                '<ul class="answer">' +
                                '<li>A、<input type="radio" name="select'+selectNum+'" id="" value="A" onclick="judgeScope(this)"/><span>'+quesions[index].alternativeOption1+'</span></li>' +
                                '<li>B、<input type="radio" name="select'+selectNum+'" id="" value="B" onclick="judgeScope(this)"/><span>'+quesions[index].alternativeOption2+'</span></li>' +
                                '<li>C、<input type="radio" name="select'+selectNum+'" id="" value="C" onclick="judgeScope(this)"/><span>'+quesions[index].alternativeOption3+'</span></li>' +
                                '<li>D、<input type="radio" name="select'+selectNum+'" id="" value="D" onclick="judgeScope(this)"/><span>'+quesions[index].alternativeOption4+'</span></li></ul></div>';
                                total += 1;
                        } else if (quesions[index].questionType==2) {
                            multiNum = multiNum + 1;
                            multiQuestionObj +='<div class="problem selectIndex">' +
                                '<input  type="hidden" name="list[' + total + '].questionId"  value="'+quesions[index].id+'" />' +
                                '<input  type="hidden" name="list[' + total + '].questionAnswer"  id="multi'+multiNum+'" class="test"/>' +
                                '<span class="tip"></span>'+
                                '<h4 class="questionTitle">'+multiNum+  '. '+quesions[index].questionTitle+'</h4>' +
                                '<ul class="answer">' +
                                '<li>A、<input type="checkbox" name="multi'+multiNum+'" id="" value="A" onclick="changeScope(this)"/><span>'+quesions[index].alternativeOption1+'</span></li>' +
                                '<li>B、<input type="checkbox" name="multi'+multiNum+'" id="" value="B" onclick="changeScope(this)"/><span>'+quesions[index].alternativeOption2+'</span></li>' +
                                '<li>C、<input type="checkbox" name="multi'+multiNum+'" id="" value="C" onclick="changeScope(this)"/><span>'+quesions[index].alternativeOption3+'</span></li>' +
                                '<li>D、<input type="checkbox" name="multi'+multiNum+'" id="" value="D" onclick="changeScope(this)"/><span>'+quesions[index].alternativeOption4+'</span></li></ul></div>';
                            total += 1;
                        } else{
                            judgeNum = judgeNum + 1;
                            judgeQuestionObj+='<div class="problem replay judgeIndex ">' +
                                '<input  type="hidden" name="list[' + total + '].questionId"  value="'+quesions[index].id+'" />' +
                                '<input  type="hidden" name="list[' + total + '].questionAnswer"  id="judge'+judgeNum+'"/>' +
                                '<span class="tip"></span>' +
                                '<h4 class="judgeAnswer">'+judgeNum+ '. '+quesions[index].questionTitle+'</h4>' +
                                '<input type="radio" name="judge'+judgeNum+'" id="" value="F" onclick="judgeScope(this)"/>×'+
                                '<input type="radio" name="judge'+judgeNum+'" id="" value="T" onclick="judgeScope(this)"/>'+'√'+'</div>';
                            total += 1;
                        }
                        $('#choice').html(selectQuestionObj);
                        $('#multi').html(multiQuestionObj);
                        $('#judge').html(judgeQuestionObj);

                        //添加答案数据
                        var tip=document.getElementsByClassName("tip");
                        for(var i=0;i<tip.length;i++){
                            rightAnswer[i]=data.obj.questions[i].rightAnswer;
                        }
                    });

                  /*  for(var question in quesions){
                        alert(""+question.questionType);
                    }*/
                }else{
                    alert(data.msg);
                    $('#submit').hide();
                }
            },
            error: function (msg) {
                alert("出错了！");
            }
        });
    });

    $('#submit').click( function () {

        $.ajax({
            cache: true,
            type: "POST",
            url:"${pageContext.request.contextPath}/exam/testPaper/submit",
            data:$('#paperForm').serialize(),//
            async: false,
            error: function(request) {
                alert("Connection error");
            },
            success: function(data) {
                if(data.success){
                    /*$("#totalScope").val("满分："+data.obj.baseSum+"分");
                    $("#getScope").val("总得分："+data.obj.paperScore+"分");
                    $("#getSelectScope").val("选择题得分："+data.obj.choiceScore+"分");
                    $("#getJudgeScope").val("判断题得分："+data.obj.packScore+"分");*/

                    var dialog=document.getElementsByClassName("dialog")[0];
                    var ensure=document.getElementById("ensure");
                    var grade=document.getElementById("testGrade");
                    var gradeList=grade.getElementsByTagName("li");

                    gradeList[0].innerText="满分："+data.obj.baseSum+"分";
                    gradeList[1].innerText="总得分："+data.obj.paperScore+"分";
                    gradeList[2].innerText="选择题得分："+data.obj.choiceScore+"分";
                    gradeList[3].innerText="多选题得分："+data.obj.multiScore+"分";
                    gradeList[4].innerText="判断题得分："+data.obj.packScore+"分";
                    dialog.style.display="block";
                    dialog.classList.toggle("scale");
                    dialog.style.display="block";
                    dialog.classList.toggle("scale");
                }else{
                    alert("试卷提交失败，请联系监考老师");
                    <%--window.open("${pageContext.request.contextPath}/protal/protalLogin","_self");--%>
                }
            }
        });
    } );
    $("#ensure").click(function(){
        var dialog=document.getElementsByClassName("dialog")[0];
        dialog.style.display="none";
        dialog.classList.toggle("scale");
        $('#submit').hide();
        var tip=document.getElementsByClassName("tip");
        var answer;
        for(var i=0;i<tip.length;i++){
            answer=document.getElementsByName("list["+i+"].questionAnswer")[0].value;
//            alert(answer);
            if(rightAnswer[i]=='T'&&rightAnswer[i]!=answer){
                tip[i].innerText="答案应为√";
            }else if(rightAnswer[i]=='F'&&rightAnswer[i]!=answer){
                tip[i].innerText="答案应为×";
            }else {
                if(rightAnswer[i]!=answer){
                    tip[i].innerText="答案应为"+rightAnswer[i];
                }
            }
            tip[i].style.display="block";
        }
      /*  $("#submit").css("background","#aaaaaa");
        $("#submit").attr('disabled',true);*/
    })
    function judgeScope(obj){
        var anwserInputId = $(obj).attr("name");
        $("#"+anwserInputId).val($(obj).val());
    }
    function changeScope(obj) {
        var anwserInputId = $(obj).attr("name");
        var checkboxs = document.getElementsByName(anwserInputId);
        var bb="";
        for(var i=0;i<checkboxs.length;i++){
            if(checkboxs[i].checked){
                bb = bb+","+checkboxs[i].value;
            }
        }
        $("#"+anwserInputId).val(bb.substring(1,bb.length));
    }

    $("#exit").click(function(){
        $.ajax({
            cache: true,
            type: "POST",
            url:"${pageContext.request.contextPath}/protal/exitProtalLogin",
//            data:,
            async: false,
            error: function(request) {
                alert("Connection error");
            },
            success: function(data) {
                if(data.success){
                    window.open("${pageContext.request.contextPath}/protal/protalLogin","_self");
                }else{
                    alert("退出失败,请联系管理员");
                }
            }
        });
    });
</script>
</body>
</html>