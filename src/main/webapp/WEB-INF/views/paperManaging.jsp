<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="${pageContext.request.contextPath }/resources/datetimepicker/bootstrap-datetimepicker.min.css"
      rel="stylesheet" media="screen">
<form id="paperManagingForm">
<div class="main">
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="row">
            <div class="col-lg-12">
                <p class="ibox float-e-margins">
                <div class="col-lg-12 ">
                    <div class="pull-left">
                        <strong class="bold pull-left" style="line-height: 30px;height: 30px">考试时间：</strong>
                        <div id="div_paper_startTimeAsString" class="input-group date form_datetime pull-left"
                             data-date-format="yyyy-mm-dd" th:data-date="" style="width: 190px;">
                            <input id="exam_startTime" class="form-control paperStartTimeValidate"
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
                            <input id="exam_endTime" class="form-control"
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
                        <strong class="bold">状态：</strong>
                        <label id="searchStateObject">状态：</label>
                    </div>
                    <div class="pull-left">
                        <strong class="bold">关键字：</strong>
                        <input id="searchPaperInfo" placeholder="试卷主题" class="myInput"
                               style="padding-left: 5px;" onchange="changeSearchKey()"/>
                    </div>
                    <button class="btn btn-info " type="button" id="export"
                            onclick="formSubmit('exportPaperExcelPlus','_self');this.blur();">
                        <i class="fa fa-cloud-download"></i>导出
                    </button>
                    <div class="pull-right">
                        <button class="btn btn-success" type="button" id="fnClickSearch">
                            <span class="bold">查询</span>
                            <i class="fa fa-search"></i>
                        </button>
                        <button class="btn btn-warning" type="button" id="fnClickClear">
                            <span class="bold">置空</span>
                            <i class="fa fa-circle-o"></i>
                        </button>
                    </div>
                </div>
                <%-- <div class="m-t col-lg-12">

                 </div>
                 <div class="m-t col-lg-12">

                 </div>--%>
                <p class="m-t clearfix">
                <table class="table table-striped table-bordered table-hover "
                       id="editable">
                    <thead>
                    <tr>
                        <%--  <th><input type="checkbox" id="checkAll" onchange="ckeckAll(this)" /></th>--%>
                        <th width="200">创建时间</th>
                        <th width="100">身份证号码</th>
                        <th width="100">姓名</th>
                        <th width="100">单位名称</th>
                        <th width="100">考试主题</th>
                        <th width="50">试卷分数</th>
                        <th width="50">正确数目</th>
                        <th width="100">状态</th>
                        <th width="200">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                </p>
            </div>
        </div>
    </div>
</div>
</form>
<jsp:include page="updatePaperView.jsp"></jsp:include>
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
        jQuery.validator.addMethod("paperStartTimeValidate", function (value, element) {
            var length = value.length;
            var examStartTimeAsString = value;
            var examEndTimeAsString = $("#exam_endTime").val();
            return this.optional(element) || (examEndTimeAsString != '' && examStartTimeAsString < examEndTimeAsString);
        }, "开始时间不得晚于结束时间");

        $("#paperManagingForm").validate({
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
            "sAjaxSource": "${pageContext.request.contextPath}/exam/testPaperManage/paging",
            "fnServerParams": function (aoData) {//向服务器传额外的参数
                aoData.push({"name": "sid", "value": "1"});
                aoData.push({"name": "searchPaperInfo", "value": $("#searchPaperInfo").val()});
                aoData.push({"name": "searchState", "value": $("#searchState").val()});
                aoData.push({"name": "beginDate", "value": $("#exam_startTime").val()});
                aoData.push({"name": "overDate", "value": $("#exam_endTime").val()});
            },

            "aoColumns": [
                /*{ "mData": function(obj){
                    return '<input type="checkbox" id="checkUserId" name="checkUserId" value="'+obj.id+'" >';
                },"sWidth": "20px"},*/
                {"mData": "createDate"},
                {"mData": "userName"},
                {"mData": "name"},
                {"mData": "companyName"},
                {"mData": "paperTheme"},
                {"mData": "paperScore"},
                {"mData": "rightNum"},
                {"mData": "searchState"},
                {
                    "mData": function (obj) {
                        var operate = '<a class="btn btn-primary" href="${pageContext.request.contextPath}/exam/viewPaperDetail?id=' + obj.id + '"><i class="fa fa-edit">查看</i></a> | ' + '&nbsp;&nbsp;';
                        operate += '<a class="btn btn-primary" onclick="paperEdit(' + "'" + obj.id + "'" + ')"><i class="fa fa-edit">修改</i></a> | ' + '&nbsp;&nbsp;';
                        operate += '<a class="btn btn-primary" onclick="deletePaper(' + "'" + obj.id + "'" + ')" ><i class="fa fa-edit">删除</i></a> ';
                        return operate;
                    }
                }
            ],
            "columnDefs": [
                {"orderable": false, "targets": [2,3,7, 8]},
                {"width": "10%", "targets": 0},
                {"width": "10%", "targets": 1},
                {"width": "10%", "targets": 2},
                {"width": "10%", "targets": 3},
                {"width": "10%", "targets": 4},
                {"width": "10%", "targets": 5},
                {"width": "10%", "targets": 6},
                {"width": "10%", "targets": 7},
                {"width": "20%", "targets": 8},
            ],
            "order": [[0, "desc"]]
        });

        $.ajax({
            url: "${pageContext.request.contextPath}/system/selectMenuDataByType?type=TESTPAPER_STATE",
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
    });
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

    function paperEdit(id) {
        //alert(2);
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
                        url: "${pageContext.request.contextPath}/exam/viewPaper?id=" + id,
                        type: "GET",
                        dataType: "json",
                        async: false,
                        cache: false,
                        contentType: "application/json",
                        success: function (data) {
                            //alert("查询成功");
                            if (!data.success) {
                                alert(data.msg);
                                flag = false;
                            } else {
                                //alert('显示模态框');
                                $('#selectPaperView').modal('show');
                                /*   $('#paperTheme').val(data.obj.paperTheme);*/
                                $('#paperScore').val(data.obj.paperScore);
                                $('#paperIdUpdate').val(data.obj.id);
                            }
                            flag = true;
                        },
                        error: function (msg) {
                            //alert("出错了！");
                        }
                    });
                }
            }
        });
    }

    function deletePaper(id) {
        <%--href="${pageContext.request.contextPath}/exam/deletePaperById?id='+obj.id+'"--%>
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
                            url: "${pageContext.request.contextPath }/exam/deletePaperById?id=" + id,
                            cache: false,
                            dataType: "json",
                            success: function () {
                                alert('删除成功');
                                oTable.draw();
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
