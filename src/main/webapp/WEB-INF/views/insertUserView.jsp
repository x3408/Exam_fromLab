<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script src="${pageContext.request.contextPath }/resources/scorejs/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/scorejs/plugins/validate/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/scorejs/validate.expand.js"></script>
<!---bootstrap-modal模态框-->
<div class="modal fade" id="selectUserView" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="z-index: 1000">
    <div class="modal-dialog" style="width:850px;height: 900px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">添加用户</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post"
                      action="${pageContext.request.contextPath }/user/saveUser" id="userForm">
                    <%-- onSubmit="return checkIdCard();">--%>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">身份证:<font style="color: red">*</font> </label>
                        <div class="col-sm-4">
                            <input path="userName" id="userName" name="userName"
                                   class="form-control isIdCardNo checkIdCardExist"
                                   onblur="this.value=this.value.trim()"/><%-- onchange="checkDuplicateUser()"/>--%>
                            <span id="IdCard" style="color: red"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">单位名称:<font style="color: red">*</font> </label>
                        <div class="col-sm-4">
                            <input path="userName" id="companyName" name="companyName" class="form-control"
                                   onblur="this.value=this.value.trim()"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">姓名:<font style="color: red">*</font></label>
                        <div class="col-sm-4">
                            <input path="name" id="name" name="name" class="form-control"
                                   onblur="this.value=this.value.trim()"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">状态:<font style="color: red">*</font></label>
                        <div class="col-sm-4">
                            <select path="state" id="state" class="form-control" name="state">
                                <option value="1" selected="true">正常</option>
                                <option value="2">注销</option>
                                <option value="3">冻结</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户类型:<font style="color: red">*</font></label>
                        <div class="col-sm-4">
                            <select path="isAdmin" id="isAdmin" class="form-control" name="isAdmin">
                                <option value="0" selected="true">普通用户</option>
                                <option value="1">管理员</option>
                                <option value="2">查阅人员</option>
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
    $(document).ready(function () {
        // 身份证格式验证
        jQuery.validator.addMethod("isIdCardNo", function (value, element) {
            var flag = IdCardValidate(value);
            return this.optional(element) || IdCardValidate(value);
        }, "请正确输入您的身份证号码");

        // 身份证号码验证有无重复
        jQuery.validator.addMethod("checkIdCardExist", function (value, element) {
            var length = value.length;
            var isExist = checkIdCardExist();
            console.info(isExist);
            return this.optional(element) || isExist;
        }, "该身份证已注册");

        function checkIdCardExist() {
            var flag = false;
            $.ajax({
                url: "${pageContext.request.contextPath}/user/selectDuplicateUser?userName=" + $("#userName").val(),
                type: "GET",
                dataType: "json",
                async: false,
                cache: false,
                contentType: "application/json",
                success: function (data) {
                    if (!data.success) {
                        flag = false;
                    } else {
                        flag = true;
                    }
                },
            });
            return flag;
        }

        $("#userForm").validate({
            rules: {
                userName: {
                    required: true,
                    maxlength: 50
                },
                companyName: {
                    required: true,
                    maxlength: 50
                },
                name: {
                    required: true,
                    maxlength: 11
                },
                state: {
                    required: true,
                    maxlength: 50
                },
                isAdmin: {
                    required: true,
                    maxlength: 50
                }
            },
            messages: {
                userName: {
                    required: "必填",
                    maxlength: "不得超过50位"
                },
                companyName: {
                    required: "必填",
                    maxlength: "不得超过50位"
                },
                name: {
                    required: "必填",
                    maxlength: "不得超过11位"
                },
                state: {
                    required: "必填",
                    maxlength: "不得超过50位"
                },
                isAdmin: {
                    required: "必填",
                    maxlength: "不得超过50位"
                }
            }
        });
    });

    /*function checkIdCard() {
//       校验身份证格式
        var aCity = {
            11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古",
            21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海", 32: "江苏", 33: "浙江",
            34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北",
            43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川"
            , 52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海",
            64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外"
        };
        var iSum = 0;
        var info = "";
        var sId = $("#userName").val();
        alert(sId);
        var checkIdCard=$("#IdCard").html();
//        alert(sId);
        if (!/^\d{17}(\d|x)$/i.test(sId)) {
            $("#IdCard").html('你输入的身份证长度或格式错误');
            return false;
        }
        sId = sId.replace(/x$/i, "a");
        if (aCity[parseInt(sId.substr(0, 2))] == null) {
            $("#IdCard").html('你的身份证地区非法');
            return false;
        }
        sBirthday = sId.substr(6, 4) + "-" + Number(sId.substr(10, 2)) + "-" + Number(sId.substr(12, 2));
        var d = new Date(sBirthday.replace(/-/g, "/"));
        if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) {
            $("#IdCard").html('身份证上的出生日期非法');
            return false;
        }
        for (var i = 17; i >= 0; i--) iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11);
        if (iSum % 11 != 1) {
            $("#IdCard").html('你输入的身份证号非法');
            return false;
        }
        alert(checkIdCard);
        if(checkIdCard=="已有重复的用户"){
            return false;
        }
        return true;
    }*/
    /*function checkDuplicateUser(){
        var sId = $("#userName").val();
//        alert(sId);
        $.ajax({
            url: "${pageContext.request.contextPath}/user/selectDuplicateUser?userName=" + sId,
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                if (!data.success) {
                    alert('false');
                    $("#IdCard").html(data.msg);
                }
            },
            error: function (msg) {
                alert("出错了！");
            }
        });
    }*/


</script>