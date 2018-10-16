<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!---bootstrap-modal模态框-->
<div class="modal fade" id="selectUpdateUserView" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index: 1000">
    <div class="modal-dialog" style="width:850px;height: 900px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">更新用户</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post"
                           action="${pageContext.request.contextPath }/user/updateUser" id="questionForm">
                    <input type="hidden" name="id" id="userIdUpdate"/>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">身份证:<font style="color: red">*</font> </label>
                        <div class="col-sm-4">
                            <input path="userName" id="userNameUpdate" name="userName" class="form-control" onblur="this.value=this.value.trim()" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">姓名:<font style="color: red">*</font></label>
                        <div class="col-sm-4">
                            <input path="name" id="nameUpdate" name="name" class="form-control" onblur="this.value=this.value.trim()" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">状态:<font style="color: red">*</font></label>
                        <div class="col-sm-4">
                            <select path="state" id="stateUpdate" class="form-control" name="state">
                                <option value="1" selected="true">正常</option>
                                <option value="2">注销</option>
                                <option value="3">冻结</option>
                            </select>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div class="col-sm-4 col-sm-offset-4">
                            <button class="btn btn-primary" type="submit">保存</button>
                            <a class="btn btn-white" href="${pageContext.request.contextPath }/user/userManage">返回</a>
                        </div>
                    </div>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<script type="text/javascript">

</script>