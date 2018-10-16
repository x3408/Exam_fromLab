<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!---bootstrap-modal模态框-->
<div class="modal fade" id="selectUpdateApplyView" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index: 1000">
    <div class="modal-dialog" style="width:850px;height: 900px;">
        <div class="modal-content">
            <div class="modal-body">
                <form class="form-horizontal" method="post"
                           action="${pageContext.request.contextPath }/user/updateApply" id="applyForm">
                    <input type="hidden" name="id" id="applyIdUpdate"/>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">状态:<font style="color: red">*</font></label>
                        <div class="col-sm-4">
                            <select path="state" id="stateUpdate" class="form-control" name="state">
                                <option value="0" selected="true">待审批</option>
                                <option value="1">已审批</option>
                              <%--  <option value="2">已过期</option>--%>
                            </select>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div class="col-sm-4 col-sm-offset-4">
                            <button class="btn btn-primary" type="submit">保存</button>
                            <a class="btn btn-white" href="${pageContext.request.contextPath }/user/applyManage">返回</a>
                        </div>
                    </div>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<script type="text/javascript">

</script>