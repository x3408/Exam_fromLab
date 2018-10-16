<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${pageContext.request.contextPath }/resources/datetimepicker/bootstrap-datetimepicker.min.css"
      rel="stylesheet" media="screen">
<form id="userManagingForm">
<div class="main">
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <p>
                        <div class="pull-left">
                            <strong class="bold pull-left" style="line-height: 30px;height: 30px">考试时间：</strong>
                            <div id="div_paper_startTimeAsString" class="input-group date form_datetime pull-left"
                                 data-date-format="yyyy-mm-dd" th:data-date="" style="width: 190px;">
                                <input id="exam_startTime" class="form-control userStartTimeValidate"
                                       name="exam_startTime" size="16" type="text" style=""/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-remove"></span>
                                </span>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-th"></span>
                                </span>
                            </div>
                            <strong class="bold control-label pull-left"
                                    style="line-height: 30px;height: 30px">&nbsp;&nbsp;&nbsp;~
                                &nbsp;&nbsp;&nbsp;</strong>
                            <div id="div_paper_endTimeAsString" class="input-group date form_datetime pull-left"
                                 data-date-format="yyyy-mm-dd" th:data-date="" style="width: 190px;">
                                <input id="exam_endTime" class="form-control "
                                       name="exam_endTime" size="16" type="text" style=""/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-remove"></span>
                                </span>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-th"></span>
                                </span>
                            </div>
                        </div>
                        <div class="pull-left">
                            用户信息：<input id="searchUserInfo" class="myInput"/>
                            状态：<label id="searchStateObject">状态：</label>
                        </div>
                        <div class="pull-right">
                            <button class="btn btn-success" type="button" id="fnClickSearch"><i
                                    class="fa fa-search"></i> <span class="bold">查询</span></button>
                            <button class="btn btn-warning" type="button" id="fnClickClear"><i
                                    class="fa fa-circle-o"></i> <span class="bold">置空</span></button>
                        </div>
                        </p>
                        <div id="crudButton">
                            <%--<button class="btn btn-info" type="button" onclick="fnClickAddRow();"><i class="fa fa-plus-square-o"></i>&nbsp;新增</button>--%>
                            <button class="btn btn-info" type="button" onclick="insertUserView();"><i
                                    class="fa fa-plus-square-o" id="insertButton"></i>&nbsp;新增
                            </button>
                            <%--<button class="btn btn-info " type="button" id="fnClickEditRows"><i class="fa fa-paste"></i>编辑</button>--%>

                            <button class="btn btn-danger" type="button" id="fnClickDelRows"><i
                                    class="fa fa-trash" id="deleteButton"></i> <span class="bold">删除</span></button>

                            <button class="btn btn-warning" type="button" onclick="updateUserState('2')"><i
                                    class="fa fa-key" id="cancelButton"></i> <span class="bold">注销</span></button>
                            <button class="btn btn-warning" type="button" onclick="updateUserState('3')"><i
                                    class="fa fa-lock" id="freezeButton"></i> <span class="bold">冻结</span></button>
                        </div>
                        <%--<button class="btn btn-info " type="button" id="export" onclick="fn_click_export()"><i
                                class="fa fa-cloud-download" ></i>导出
                        </button>--%>
                        <button class="btn btn-info " type="button" id="export"
                                onclick="formSubmit('exportUserExcelPlus','_self');this.blur();">
                            <i class="fa fa-cloud-download"></i>导出
                        </button>


                        <table class="table table-striped table-bordered table-hover "
                               id="editable">
                            <thead>
                            <tr>
                                <th><input type="checkbox" id="checkAll" onchange="ckeckAll(this)"/></th>
                                <th width="100">创建时间</th>
                                <th width="100">单位名称</th>
                                <th width="100">身份证</th>
                                <th width="100">姓名</th>
                                <th width="100">类型</th>
                                <th width="100">状态</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</form>
<jsp:include page="insertUserView.jsp"></jsp:include>
<jsp:include page="updateUserView.jsp"></jsp:include>
<script src="${pageContext.request.contextPath }/resources/scorejs/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/scorejs/plugins/validate/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/scorejs/bootstrap-modal.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/ajaxfileupload.js"></script>

<script type="text/javascript"
        src="${pageContext.request.contextPath }/resources/datetimepicker/bootstrap.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath }/resources/datetimepicker/bootstrap-datetimepicker.js"
        charset="UTF-8"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath }/resources/datetimepicker/bootstrap-datetimepicker.zh-CN.js"
        charset="UTF-8"></script>
<script>
    var oTable;
    $(document).ready(function () {

        jQuery.validator.addMethod("userStartTimeValidate", function (value, element) {
             var length = value.length;
             var examStartTimeAsString = value;
             var examEndTimeAsString = $("#exam_endTime").val();
             return this.optional(element) || (examEndTimeAsString != '' && examStartTimeAsString < examEndTimeAsString);
         }, "开始时间不得晚于结束时间");

        $("#userManagingForm").validate({
            rules: {
                exam_startTime: {
                    maxlength: 50
                },
                exam_endTime: {
                    maxlength: 50
                }
            },
            messages: {
                exam_startTime: {
                    maxlength: "不得超过50位"
                },
                exam_endTime: {
                    maxlength: "不得超过50位"
                }
            }
        });

        $('#div_paper_startTimeAsString').datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd hh:ii:ss',
            todayBtn: 1,
            todayHighlight: true,
            autoclose: 1
        });

        $('#div_paper_endTimeAsString').datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd hh:ii:ss',
            todayBtn: 1,
            todayHighlight: true,
            autoclose: 1
        });

        oTable = $('#editable').DataTable({
            searching: false,
            "sPaginationType": "simple_numbers",
            "oLanguage": {
                "sProcessing": "<span style='color:#1c9dd4;'>数据加载中...</span>",
                "sLengthMenu": "每页显示  _MENU_ 条记录",
                "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "前一页",
                    "sNext": "后一页",
                    "sLast": "尾页"
                },
                "sZeroRecords": "抱歉， 没有找到数据",
                "sInfoEmpty": "没有数据"
            },
            "bAutoWidth": true,
            "bProcessing": true,
            "bServerSide": true,
            "bFilter": true,
            "iDisplayLength": 10,
            "bLengthChange": true,
            "sAjaxSource": "${pageContext.request.contextPath}/user/userManage/paging",
            "fnServerParams": function (aoData) {//向服务器传额外的参数
                aoData.push({"name": "sid", "value": "1"});
                aoData.push({"name": "searchUserInfo", "value": $("#searchUserInfo").val()});
                aoData.push({"name": "searchState", "value": $("#searchState").val()});
                aoData.push({"name": "beginDate", "value": $("#exam_startTime").val()});
                aoData.push({"name": "overDate", "value": $("#exam_endTime").val()});
            },
            "aoColumns": [
                {
                    "mData": function (obj) {
                        return '<input type="checkbox" id="checkUserId" name="checkUserId" value="' + obj.id + '" >';
                    }, "sWidth": "20px"
                },
                {"mData": "createDate"},
                {"mData": "companyName"},
                {"mData": "userName"},
                {"mData": "name"},
                {"mData": "userType"},
                {"mData": "state"},
                {
                    "mData": function (obj) {
                        var operate = '<div id="operateButton"><a class="btn btn-primary" onclick="userEdit(' + "'" + obj.id + "'" + ')" ><i class="fa fa-edit">修改</i></a> | ' + '&nbsp;&nbsp;';
                        operate += '<a class="btn btn-primary" href="#" onclick="resetUserPassword(' + "'" + obj.id + "'" + ')"><i class="fa fa-edit">重置密码</i></a>' + '&nbsp;&nbsp;';
                        operate += '</div>';
                        return operate;
                    }
                }
            ],
            "columnDefs": [
                {"orderable": false, "targets": [0,5,7]},
                {"width": "5%", "targets": 0},
                {"width": "20%", "targets": 1},
                {"width": "15%", "targets": 2},
                {"width": "15%", "targets": 3},
                {"width": "10%", "targets": 4},
                {"width": "10%", "targets": 5},
                {"width": "10%", "targets": 6},
                {"width": "15%", "targets": 7},
            ],
            "order": [[1, "desc"]]
        });



        $.ajax({
            url: "${pageContext.request.contextPath}/system/selectMenuDataByType?type=USER_STATE",
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                var selectObj = '<select class="form-control" style="width: 100px;" id="searchState" name="searchState">';
                selectObj += '<option value="">全部</option>';
                for (var i in data) {
                    var detailList = data;
                    selectObj += '<option value=' + detailList[i]['code'] + '>' + detailList[i]['value'] + '</option>';
                }
                selectObj += '</select>';
                $('#searchStateObject').html(selectObj);
            },
            error: function (msg) {
                alert("出错了！");
            }
        });

        <!--判断是作业人员还是查看人员,进行隐藏或显示-->
        $.ajax({
            url: "${pageContext.request.contextPath}/user/selectExistUser",
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                if (data.obj.isAdmin == 2) {
                    /* alert(2);*/
                    document.getElementById("crudButton").style.display = "none";
                    /*document.getElementById("operateButton").style.display = "none";*/
                }
            },
            error: function (msg) {
                alert("出错了！");
            }
        });

    });

    /*
 显示问题插入框
  */
    function insertUserView() {
        $('#selectUserView').modal('show');
    }

    function userEdit(id) {
        $.ajax({
            url: "${pageContext.request.contextPath}/user/selectExistUser",
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                if (data.obj.isAdmin == 2) {
                    alert("无法修改");
                } else {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/user/viewUser?id=" + id,
                        type: "GET",
                        dataType: "json",
                        async: false,
                        cache: false,
                        contentType: "application/json",
                        success: function (data) {
//                alert("查询成功");
                            if (!data.success) {
                                //alert(data.msg);
                                flag = false;
                            } else {
//                    alert('显示模态框');
                                $('#selectUpdateUserView').modal('show');
                                $('#userNameUpdate').val(data.obj.userName);
                                $('#nameUpdate').val(data.obj.name);
                                $('#userIdUpdate').val(data.obj.id);
                                $("#stateUpdate option[value='" + (data.obj.state - 1) + "']").attr("selected", "selected");
                            }
                            flag = true;
                        },
                        error: function (msg) {
                            //alert("出错了！");
                        }
                    });
                }
            },
            error: function (msg) {
                alert("出错了！");
            }
        });

    }

    $('#fnClickSearch').click(function () {
        oTable.draw();
    });

    $('#fnClickClear').click(function () {
        $("#searchUserInfo").val("");
        $("#searchState").val("");
        oTable.draw();
    });

    function fnClickAddRow() {
        location.href = '${pageContext.request.contextPath}/user/addUser';
    }

    function ckeckAll(obj) {
        var $checked = $(obj).prop("checked");
        $("td input[type='checkbox']").each(function () {
            $(this).prop("checked", $checked);
        });
        $(obj).prop("checked", $checked);
    }

    $('#fnClickDelRows').click(function () {
        var arr = [];
        $("[name=checkUserId]:checkbox").each(function () {
            if ($(this).prop("checked")) {
                arr.push($(this).val());
            }
        });
        if (arr.length == 0) {
            alert('请选择要删除的用户！');
            return;
        }
        if (confirm("确认删除？")) {
            $.ajax({
                url: "${pageContext.request.contextPath }/user/deleteUser?ids=" + arr,
                cache: false,
                dataType: "json",
                success: function (r) {
                    alert('删除成功');
                    oTable.draw();
                }
            });
        }
        ;
    });

    function resetUserPassword(id) {
        $.ajax({
            url: "${pageContext.request.contextPath}/user/selectExistUser",
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                if (data.obj.isAdmin == 2) {
                    alert('无法修改');
                } else {
                    $.ajax({
                        url: "${pageContext.request.contextPath }/user/resetUserPassword?id=" + id,
                        type: "GET",
                        cache: false,
                        dataType: "json",
                        success: function (r) {
                            alert('重置成功');
                            oTable.draw();
                        }
                    });
                }
            },
            error: function (msg) {
                alert("出错了！");
            }
        });


    }

    function updateUserState(state) {
        var arr = [];
        $("[name=checkUserId]:checkbox").each(function () {
            if ($(this).prop("checked")) {
                arr.push($(this).val());
            }
        });
        if (arr.length == 0 && state == '2') {
            alert('请选择要注销的用户！');
            return;
        }
        if (arr.length == 0 && state == '3') {
            alert('请选择要冻结的用户！');
            return;
        }
        var desc = '确认注销？';
        if (state == '3') {
            desc = "确认冻结？";
        }
        if (confirm(desc)) {
            $.ajax({
                url: "${pageContext.request.contextPath }/user/updateUserState?ids=" + arr + "&state=" + state,
                cache: false,
                dataType: "json",
                success: function (r) {
                    if (state == '2') {
                        alert('注销成功');
                    }
                    if (state == '3') {
                        alert('冻结成功');
                    }
                    oTable.draw();
                }
            });
        }
    }

    function fn_click_export() {
        alert('导出ing');
        $.ajax({
            url: "${pageContext.request.contextPath }/user/exportUserExcel",
            cache: false,
            type: "get",
            dataType: "json",
            success: function (json) {
                alert(json);
                if (json.success) {
                    alert("导出成功");
                    alert(json.obj);
                    window.open(json.obj);
                } else {
                    alert(json.msg);
                }

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
//                            console.log(msg);
//                            alert("出错了:"+msg);
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    }

    /* 打开一个新页面：调用时不加第二个参数 add by tony */
    function formSubmit(url, sTarget) {
//        alert(1)
        document.forms[0].target = sTarget
        document.forms[0].action = url;
        document.forms[0].submit();
        return true;
    }

</script>
