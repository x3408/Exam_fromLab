<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!---bootstrap-modal模态框-->
<div class="modal fade" id="selectMaterialView" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index: 1000">
    <div class="modal-dialog" style="width:850px;height: 900px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">添加资料</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post"
                           action="${pageContext.request.contextPath }/user/saveMaterial">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">资料主题<font style="color: red">*</font></label>
                        <div class="col-sm-4">
                            <input id="materialTheme" class="form-control" onblur="this.value=this.value.trim()" name="materialTheme" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">资料备注<font style="color: red">*</font></label>
                        <div class="col-sm-4">
                            <input id="materialDescription" class="form-control" onblur="this.value=this.value.trim()" name="materialDescription" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">资料路径<font style="color: red">*</font></label>
                        <div class="col-sm-4">
                            <input id="input_file_path"  type="text" name="materialUrl"
                                        class="text" maxlength="200" readonly="readonly"/>
                            <input id="input_file_backimg" type="file" name="file" onchange="uploadFile(this)"
                                   style="display: none"/>
                            <button class="btn btn-primary" type="button" onclick="btn_upload();">上传</button>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group" >
                        <div class="col-sm-4 col-sm-offset-4">
                            <button class="btn btn-primary" type="submit"  id="show1">保存</button>
                            <a class="btn btn-white" href="">返回</a>
                        </div>
                    </div>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<script type="text/javascript">
    function uploadFile(obj) {
//        alert(2);
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath }/system/uploadFile",
            secureuri: false,// 一般设置为false
            fileElementId: "input_file_backimg",// 文件上传input的id
            dataType: 'json',// 返回值类型 一般设置为json
            data: {},
            success: function (json) // 服务器成功响应处理函数
            {
//                alert(json);
                if (json.success) {
//                    $("#img_back").attr("src", json.obj);
                    $("#input_file_path").val(json.obj);//访问路径
                }

            },
            error: function (json)// 服务器响应失败处理函数
            {
                console.log(json.msg);
            }
        });
    }
    function btn_upload() {
        $('#input_file_backimg').click();
    }



</script>
