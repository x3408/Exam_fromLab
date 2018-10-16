<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!---bootstrap-modal模态框-->
<div class="modal fade" id="selectPaperView" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index: 1000">
    <div class="modal-dialog" style="width:850px;height: 900px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">修改试卷</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post"
                           action="${pageContext.request.contextPath }/exam/updatePaper" id="paperForm">
                    <input type="hidden" name="id" id="paperIdUpdate"/>
                    <%--<div class="form-group">
                        <label class="col-sm-4 control-label">试卷主题:<font style="color: red">*</font> </label>
                        <div class="col-sm-4">
                            <input path="paperTheme" id="paperTheme" name="paperTheme" class="form-control" onblur="this.value=this.value.trim()" />
                        </div>
                    </div>--%>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">试卷分数:<font style="color: red">*</font></label>
                        <div class="col-sm-4">
                            <input path="paperScore" id="paperScore" name="paperScore" class="form-control" onblur="this.value=this.value.trim()" />
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div class="col-sm-4 col-sm-offset-4">
                            <button class="btn btn-primary" type="submit">保存</button>
                            <a class="btn btn-white" href="${pageContext.request.contextPath }/exam/testPaperManage">返回</a>
                        </div>
                    </div>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<script type="text/javascript">

</script>