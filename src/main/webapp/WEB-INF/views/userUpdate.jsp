<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- /section:basics/sidebar -->
<script src="${pageContext.request.contextPath }/resources/scorejs/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/scorejs/plugins/validate/jquery.validate.min.js"></script>
<div class="main">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>修改用户信息</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <form:form class="form-horizontal" commandName="user" method="post"
                               action="${pageContext.request.contextPath }/user/updateUser" id="userForm">
                        <form:hidden path="id"></form:hidden>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">用户名<font style="color: red">*</font> </label>
                            <div class="col-sm-4">
                                <form:input path="userName" id="userName" class="form-control" onblur="this.value=this.value.trim()" />
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label">姓名<font style="color: red">*</font></label>
                            <div class="col-sm-4">
                                <form:input path="name" id="name" class="form-control" onblur="this.value=this.value.trim()" />
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-4 control-label">状态<font style="color: red">*</font></label>
                            <div class="col-sm-4">
                                <form:select path="state" id="state" class="form-control">
                                    <form:option value=""></form:option>
                                </form:select>
                            </div>
                        </div>


                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-4">
                                <button class="btn btn-primary" type="submit">保存</button>
                                <a class="btn btn-white" href="${pageContext.request.contextPath }/user/userManage">返回</a>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script>
    $(document).ready(function(){
        // 手机号码验证
        jQuery.validator.addMethod("isMobile", function(value, element) {
            var length = value.length;
            var mobile = /^1\d{10}$/;
            return this.optional(element) || (length == 11 && mobile.test(value));
        }, "请正确填写您的手机号码");

        // 手机号码验证
        jQuery.validator.addMethod("checkMobilePhoneExist", function(value, element) {
            var length = value.length;
            var isExist = checkMobilePhoneExist();
            return this.optional(element) || isExist;
        }, "该手机号码已注册");

        function checkMobilePhoneExist() {
            var flag;
//            alert("id:"+$("#id").val());
            $.ajax({
                url: "${pageContext.request.contextPath}/user/checkMobilePhoneExist?tel="+$("#tel").val()+"&id="+$("#id").val(),
                type: "GET",
                dataType: "json",
                async:false,
                cache: false,
                contentType: "application/json",
                success: function (data) {
                    if (!data.success) {
                        //alert(data.msg);
                        flag = false;
                    } else {
                        flag = true;
                    }
                },
                error: function (msg) {
                    //alert("出错了！");
                }
            });
            return flag;
        }

        $("#userForm").validate({
            rules: {
                userName: {
                    required: true,
                    maxlength: 20
                },
                name: {
                    required: true,
                    maxlength: 50
                },
                tel: {
                    required: true,
                    maxlength: 11
                },
                email: {
                    email:true
                }
            },
            messages: {
                userName: {
                    required: "必填",
                    maxlength: "不得超过20位"
                },
                name: {
                    required: "必填",
                    maxlength: "不得超过50位"
                },
                tel: {
                    required: "必填",
                    maxlength: "不得超过11位"
                },
                email:{
                    email:"Email格式不正确"
                },
            }
        });

        $.ajax({
            url: "${pageContext.request.contextPath}/system/selectMenuDataByType?type=USER_STATE",
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                var selectObj = '<select class="form-control" style="width: 100px;" id="state" path="state" name="state">';
                for (var i in data) {
                    var detailList = data;
                    if (detailList[i]['code'] == ${user.state}) {
                        selectObj += '<option value=' + detailList[i]['code'] + ' selected=selected>' + detailList[i]['value'] + '</option>';
                    } else {
                        selectObj += '<option value=' + detailList[i]['code'] + '>' + detailList[i]['value'] + '</option>';
                    }
                }
                selectObj += '</select>';
                $('#state').html(selectObj);
            },
            error: function (msg) {
                alert("出错了！");
            }
        });
    });
</script>
