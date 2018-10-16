<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="${pageContext.request.contextPath }/resources/datetimepicker/bootstrap-datetimepicker.min.css"
      rel="stylesheet" media="screen">
<div class="main">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <p>

                    <div id="a">试卷信息：<input id="searchPaperInfo" class="myInput"  /></div>
                    状态：<%--<input id="searchState" />--%><label id="searchStateObject" class="b"> 状态：</label>

                        <label class="col-sm-4 control-label">活动时间<font style="color: red">*</font></label>
                        <div class="col-sm-4">
                            <div id="form_datetime_startTimeAsString" class="input-group date form_datetime" data-date-format="yyyy-mm-dd hh:ii:ss"
                                 data-date="${date}">
                                <input class="form-control activityStartTimeValidate" id="activityStartTimeAsString" name="startTimeAsString" size="16"
                                            type="text" path="activityStartTimeAsString" value="" />
                                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                            </div>
                        </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">至<font style="color: red">*</font></label>
                        <div class="col-sm-4">
                            <div id="form_datetime_endTimeAsString" class="input-group date form_datetime" data-date-format="yyyy-mm-dd hh:ii:ss"
                                 data-date="${date}" >
                                <input class="form-control" id="activityEndTimeAsString" name="activityEndTimeAsString" size="16"
                                            type="text" value="" path="activityEndTimeAsString" />
                                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                            </div>
                        </div>
                        <button class="btn btn-success" type="button" id="fnClickSearch"><i class="fa fa-search"></i> <span class="bold">查询</span></button>
                        <button class="btn btn-warning" type="button" id="fnClickClear"><i class="fa fa-circle-o"></i> <span class="bold">置空</span></button>
                    </div>

                    </p>

                    <table class="table table-striped table-bordered table-hover "
                           id="editable">
                        <thead>
                        <tr>
                          <%--  <th><input type="checkbox" id="checkAll" onchange="ckeckAll(this)" /></th>--%>
                            <th width="200">创建时间:</th>
                            <th width="100">考生名:</th>
                            <th width="100">试卷主题</th>
                            <th width="50">试卷分数</th>
                            <th width="50">正确数目</th>
                            <th width="100">状态</th>
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
    $(document).ready(function() {
        jQuery.validator.addMethod("activityStartTimeValidate", function(value,element) {
            var length = value.length;
            var activityStartTimeAsString = value;
            var activityEndTimeAsString = $("#activityEndTimeAsString").val();
            return this.optional(element) || (activityEndTimeAsString != '' && activityStartTimeAsString < activityEndTimeAsString);
        }, "活动开始时间不得晚于活动结束时间");
        $('#form_datetime_startTimeAsString').datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd hh:ii:ss',
            todayBtn:  1,
            todayHighlight: true,
            autoclose: 1
        });

        $('#form_datetime_endTimeAsString').datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd hh:ii:ss',
            todayBtn:  1,
            todayHighlight: true,
            autoclose: 1
        });
        oTable = $('#editable').DataTable( {
            searching:false,
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
            "fnServerParams": function ( aoData ) {//向服务器传额外的参数
                aoData.push( { "name": "sid", "value": "1" } );
                aoData.push( { "name": "searchPaperInfo", "value": $("#searchPaperInfo").val() });
                aoData.push( { "name": "searchState", "value": $("#searchState").val() });
                aoData.push( { "name": "beginDate", "value": $("#activityStartTimeAsString").val() });
                aoData.push( { "name": "overDate", "value": $("#activityEndTimeAsString").val() });

            },

            "aoColumns": [
                /*{ "mData": function(obj){
                    return '<input type="checkbox" id="checkUserId" name="checkUserId" value="'+obj.id+'" >';
                },"sWidth": "20px"},*/
                {"mData":"createDate"},
                { "mData":"userName"},
                { "mData": "paperTheme" },
                { "mData": "paperScore" },
                { "mData": "rightNum" },
                { "mData": "state"},
                { "mData": function(obj){
                    var operate = '<a class="btn btn-primary" href="${pageContext.request.contextPath}/user/viewUser?id='+obj.id+'"><i class="fa fa-edit">查看</i></a> | ' +'&nbsp;&nbsp;';
                    operate+= '<a class="btn btn-primary" href="${pageContext.request.contextPath}/exam/deletePaperById?id='+obj.id+'"><i class="fa fa-edit">删除</i></a> | ' +'&nbsp;&nbsp;';
                    return operate;
                }}
            ],
            "columnDefs": [
                { "orderable": false, "targets": [6] },
                { "width": "20%",  "targets": 0 },
                { "width": "20%", "targets": 1 },
                { "width": "20%", "targets": 2 },
                { "width": "10%", "targets": 3 },
                { "width": "10%", "targets": 4 },
                { "width": "10%", "targets": 5 },
                { "width": "20%", "targets": 6 },
            ],
            "order": [[ 0, "desc" ]]
        } );

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

    $('#fnClickSearch').click( function () {
        oTable.draw();
    } );

    $('#fnClickClear').click( function () {
        $("#searchUserInfo").val("");
        $("#searchState").val("");
        oTable.draw();
    } );

    function fnClickAddRow(){
        location.href='${pageContext.request.contextPath}/user/addUser';
    }

    function ckeckAll(obj){
        var $checked=$(obj).prop("checked");
        $("td input[type='checkbox']").each(function(){
            $(this).prop("checked",$checked);
        });
        $(obj).prop("checked",$checked);
    }


</script>
