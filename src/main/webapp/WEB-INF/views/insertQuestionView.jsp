<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!---bootstrap-modal模态框-->
<div class="modal fade" id="selectQuestionView" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index: 1000">
    <div class="modal-dialog" style="width:850px;height: 900px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">添加试题</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post"
                           action="${pageContext.request.contextPath }/exam/question/saveQuestion" id="questionForm">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">试题主题<font style="color: red">*</font></label>
                        <div class="col-sm-4">
                            <input id="questionTheme" class="form-control" onblur="this.value=this.value.trim()" name="questionTheme" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">试题类型<font style="color: red">*</font> </label>
                        <div class="col-sm-4">
                            <select id="questionType" class="form-control" name="questionType">
                                <option value="0" selected="true">选择题</option>;
                                <option value="1"   >判断题</option>;
                                <option value="2"   >多选题</option>;
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">试题题目<font style="color: red">*</font></label>
                        <div class="col-sm-4">
                            <input id="questionTitle" class="form-control" onblur="this.value=this.value.trim()" name="questionTitle" />
                        </div>
                    </div>
                    <div class="form-group" id="alternativeOptionVisible">
                        <label class="col-sm-4 control-label">试题选项<font style="color: red">*</font></label>
                        <div class="col-sm-4 form-horizonal">
                            <div class=" form-group">
                                <label class="control-label col-lg-2 " >A</label>
                                <div class="col-lg-10">
                                <input id="alternativeOption1" class="form-control " onblur="this.value=this.value.trim()" name="alternativeOption1"/>
                                </div>
                            </div>
                            <div class=" form-group">
                                <label class="control-label col-lg-2">B</label>
                                <div class="col-lg-10">
                                <input id="alternativeOption2" class="form-control" onblur="this.value=this.value.trim()" name="alternativeOption2"/>
                                </div>
                            </div>
                            <div class=" form-group">
                                <label class="control-label col-lg-2">C</label>
                                <div class="col-lg-10">
                                <input id="alternativeOption3" class="form-control" onblur="this.value=this.value.trim()" name="alternativeOption3"/>
                                </div>
                            </div>
                            <div class=" form-group">
                                <label class="control-label col-lg-2">D</label>
                                <div class="col-lg-10">
                                <input id="alternativeOption4" class="form-control" onblur="this.value=this.value.trim()" name="alternativeOption4"/>
                                </div>
                            </div>

                            <%--<button class="btn btn-primary m-t control-label" type="button" id="fnClickAdd">添加</button>--%>
                       </div>

                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">正确答案<font style="color: red">*</font> </label>
                        <div class="col-sm-4" id="choiceAnswer">
                            <%--<input id="rightAnswer" class="form-control" onblur="this.value=this.value.trim()" name="rightAnswer"/>--%>
                            <input type="radio" name="rightAnswer" value="A"/>A&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="rightAnswer" value="B">B&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="rightAnswer" value="C"/>C&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="rightAnswer" value="D">D
                        </div>
                        <div class="col-sm-4" id="judgeAnswer">
                            <%--<input id="rightAnswer" class="form-control" onblur="this.value=this.value.trim()" name="rightAnswer"/>--%>
                            <input type="radio" name="rightAnswer" value="T"/>√&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="rightAnswer" value="F">×
                        </div>
                    </div>
                    <div class="form-group">
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group" >
                        <div class="col-sm-4 col-sm-offset-4">
                            <button class="btn btn-primary" type="submit"  id="show1">保存</button>
                            <a class="btn btn-white" href="${pageContext.request.contextPath }/exam/questionManage">返回</a>
                        </div>
                    </div>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<script type="text/javascript">
        /*隐藏选项*/
        window.onload=function  () {
            var hideS=document.getElementById("questionType");
            var select=document.getElementById("alternativeOptionVisible");
            var choiceAnswer=document.getElementById("choiceAnswer");
            var judgeAnswer=document.getElementById("judgeAnswer");
            judgeAnswer.style.display="none";
            hideS.onchange=function () {
               var value=this.value;
               if(value=="1"){
                   select.style.display="none";
                   choiceAnswer.style.display="none";
                   judgeAnswer.style.display="block";
               } else if(value=="2"){
                   $("#choiceAnswer input").attr("type","checkbox");
                   select.style.display="block";
                   choiceAnswer.style.display="block";
                   judgeAnswer.style.display="none";
               } else {
                   $("#choiceAnswer input").attr("type","radio");
                   select.style.display="block";
                   choiceAnswer.style.display="block";
                   judgeAnswer.style.display="none";
               }
            }

        }

</script>
<script>
    $(document).ready(function(){
        var num = 1;
        /*搜索*/
        $('#fnClickAdd').click( function () {
            var idBefore = "#"+"alternativeOption" + num;
            num = num +1;
            var idAfter = "alternativeOption" + num;
            alert(idBefore+"+++++++"+idAfter)
            var alternativeOptionObj = '<input id="'+idAfter+'" class="form-control" onblur="this.value=this.value.trim()"/>';
            $("#"+idAfter).html(alternativeOptionObj);
        } );
    });
</script>