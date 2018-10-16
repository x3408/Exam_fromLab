<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link href="${pageContext.request.contextPath }/resources/datetimepicker/bootstrap-datetimepicker.min.css"
      rel="stylesheet" media="screen">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="main">
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <p>
                            关键字：<input id="searchInfo" class="myInput"/>
                        <div class="pull-left">
                            <strong class="bold pull-left" style="line-height: 30px;height: 30px">时间：</strong>
                            <div id="div_paper_startTimeAsString" class="input-group date form_datetime pull-left"
                                 data-date-format="yyyy-mm-dd" th:data-date="" style="width: 190px;">
                                <input id="startTime" class="form-control"
                                       name="startTime" size="16" type="text" style=""/>
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
                                <input id="endTime" class="form-control "
                                       name="endTime" size="16" type="text" style=""/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-remove"></span>
                                </span>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-th"></span>
                                </span>
                            </div>
                        </div>
                        <button class="btn btn-success" type="button" id="fnClickSearch"><i
                                class="fa fa-search"></i> <span class="bold">查询</span></button>
                        <button class="btn btn-warning" type="button" id="fnClickClear"><i
                                class="fa fa-circle-o"></i> <span class="bold">置空</span></button>
                        <button class="btn btn-info" type="button" onclick="insertMaterialView();"
                                id="insertButton"><i
                                class="fa fa-plus-square-o"></i>&nbsp;新增
                        </button>
                        </p>
                        <table class="table table-striped table-bordered table-hover "
                               id="editable">
                            <thead>
                            <tr>
                                <%-- <th><input type="checkbox" id="checkAll" onchange="ckeckAll(this)"/></th>--%>
                                <th width="200">ID号</th>
                                <th width="100">创建时间</th>
                                <th width="100">资料主题</th>
                                <th width="200">资料备注</th>
                                <th width="200">操作</th>
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
<jsp:include page="insertMaterialView.jsp"/>
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
        /* jQuery.validator.addMethod("StartTimeValidate", function (value, element) {
             var length = value.length;
             var examStartTimeAsString = value;
             var examEndTimeAsString = $("#endTime").val();
             return this.optional(element) || (examEndTimeAsString != '' && examStartTimeAsString < examEndTimeAsString);
         }, "开始时间不得晚于结束时间");*/

        /*  $("#paperManagingForm").validate({
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
          });*/

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
            "sAjaxSource": "${pageContext.request.contextPath}/user/materialManage/paging",
            "fnServerParams": function (aoData) {//向服务器传额外的参数
                aoData.push({"name": "sid", "value": "1"});
                aoData.push({"name": "searchInfo", "value": $("#searchInfo").val()});
                aoData.push({"name": "beginDate", "value": $("#startTime").val()});
                aoData.push({"name": "overDate", "value": $("#endTime").val()});
            },

            "aoColumns": [
                {"mData": "id"},
                {"mData": "createDate"},
                {"mData": "materialTheme"},
                {"mData": "materialDescription"},
                {
                    "mData": function (obj) {
                        var operate = '<a class="btn btn-primary" href="' + obj.materialUrl + '"><i class="fa fa-edit">查看</i></a> | ' + '&nbsp;&nbsp;';
                        operate += '<a class="btn btn-primary" onclick="deleteMaterial(' + "'" + obj.id + "'" + ')" ><i class="fa fa-edit">删除</i></a> ';
                        return operate;
                    }
                }
            ],
            "columnDefs": [
                {"orderable": false, "targets": [4]},
                {"width": "10%", "targets": 0},
                {"width": "10%", "targets": 1},
                {"width": "10%", "targets": 2},
                {"width": "10%", "targets": 3},
                {"width": "10%", "targets": 4},
            ],
            "order": [[1, "desc"]]
        });
    });

    $('#fnClickSearch').click(function () {
        oTable.draw();
    });

    $('#fnClickClear').click(function () {
        $("#searchInfo").val("");
        $("#beginTime").val("");
        $("#endTime").val("");
        oTable.draw();
    });

    function insertMaterialView() {
        $('#selectMaterialView').modal('show');
    }

    function ckeckAll(obj) {
        var $checked = $(obj).prop("checked");
        $("td input[type='checkbox']").each(function () {
            $(this).prop("checked", $checked);
        });
        $(obj).prop("checked", $checked);
    }


    function deleteMaterial(id) {
        $.ajax({
            url: "${pageContext.request.contextPath}/user/selectExistUser",
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                if (data.obj.isAdmin == 2) {
                    alert("无法修改");
                } else {
                    if (confirm("是否继续删除?")) {
                        $.ajax({
                            url: "${pageContext.request.contextPath }/user/deleteMaterialById?id=" + id,
                            cache: false,
                            dataType: "json",
                            success: function () {
                                alert('删除成功');
                                oTable.draw();
                                window.location.reload();
                            }
                        });
                    }
                }
            }
        });
    }

    function formSubmit(url, sTarget) {
//        alert(1)
        document.forms[0].target = sTarget
        document.forms[0].action = url;
        document.forms[0].submit();
        return true;
    }


</script>
