<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="main">
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <p>
                            申请人信息：<input id="searchUserInfo" class="myInput"/>
                            状态：<%--<input id="searchState" />--%><label id="searchStateObject">状态：</label>
                            <button class="btn btn-success" type="button" id="fnClickSearch"><i
                                    class="fa fa-search"></i> <span class="bold">查询</span></button>
                            <button class="btn btn-warning" type="button" id="fnClickClear"><i
                                    class="fa fa-circle-o"></i> <span class="bold">置空</span></button>
                            <button class="btn btn-danger" type="button" id="fnClickDelRows"><i class="fa fa-trash"></i>
                                <span class="bold">删除</span></button>
                        </p>
                        <table class="table table-striped table-bordered table-hover "
                               id="editable">
                            <thead>
                            <tr>
                                <th><input type="checkbox" id="checkAll" onchange="ckeckAll(this)"/></th>
                                <th width="100">身份证</th>
                                <th width="100">姓名</th>
                                <th width="100">申请内容</th>
                                <th width="100">时间</th>
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
<jsp:include page="updateApplyView.jsp"/>
<script>
    var oTable;
    $(document).ready(function () {
        $.ajax({
            url: "${pageContext.request.contextPath}/user/selectExistUser",
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                if (data.obj.isAdmin == 2) {
                    document.getElementById("fnClickDelRows").style.display = "none";
                }
            },
            error: function (msg) {
                alert("出错了！");
            }
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
            "sAjaxSource": "${pageContext.request.contextPath}/user/applyManage/paging",
            "fnServerParams": function (aoData) {//向服务器传额外的参数
                aoData.push({"name": "sid", "value": "1"});
                aoData.push({"name": "searchUserInfo", "value": $("#searchUserInfo").val()});
                aoData.push({"name": "searchState", "value": $("#searchState").val()});
            },
            "aoColumns": [
                {
                    "mData": function (obj) {
                        return '<input type="checkbox" id="checkUserApplyId" name="checkUserApplyId" value="' + obj.id + '" >';
                    }, "sWidth": "20px"
                },
                {"mData": "userName"},
                {"mData": "name"},
                {"mData": "content"},
                {"mData": "createDate"},
                {"mData": "state"},
                {
                    "mData": function (obj) {
                        var operate = '<a class="btn btn-primary" onclick="applyEdit(' + "'" + obj.id + "'" + ')"><i class="fa fa-edit">修改</i></a>  ' + '&nbsp;&nbsp;';
                        return operate;
                    }
                }
            ],
            "columnDefs": [
                {"orderable": false, "targets": [0, 2,3, 6]},
                {"width": "5%", "targets": 0},
                {"width": "20%", "targets": 1},
                {"width": "15%", "targets": 2},
                {"width": "20%", "targets": 3},
                {"width": "20%", "targets": 4},
                {"width": "15%", "targets": 5},
                {"width": "20%", "targets": 6},
            ],
            "order": [[4, "desc"]]
        });

        $.ajax({
            url: "${pageContext.request.contextPath}/system/selectMenuDataByType?type=APPLY_STATE",
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


    function applyEdit(id) {
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
                        url: "${pageContext.request.contextPath}/user/viewApply?id=" + id,
                        type: "GET",
                        dataType: "json",
                        async: false,
                        cache: false,
                        contentType: "application/json",
                        success: function (data) {
                            if (!data.success) {
                                flag = false;
                            } else {
                                $('#selectUpdateApplyView').modal('show');
                                $('#applyIdUpdate').val(data.obj.id);
                                $("#stateUpdate option[value='" + data.obj.state + "']").attr("selected", "selected");
                            }
                            flag = true;
                        },
                        error: function (msg) {
                        }
                    });
                }
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


    function ckeckAll(obj) {
        var $checked = $(obj).prop("checked");
        $("td input[type='checkbox']").each(function () {
            $(this).prop("checked", $checked);
        });
        $(obj).prop("checked", $checked);
    }

    $('#fnClickDelRows').click(function () {
        var arr = [];
        $("[name=checkUserApplyId]:checkbox").each(function () {
            if ($(this).prop("checked")) {
                arr.push($(this).val());
            }
        });
//        alert(arr[0]);
        if (arr.length == 0) {
            alert('请选择要删除的申请！');
            return;
        }
        if (confirm("确认删除？")) {
            $.ajax({
                url: "${pageContext.request.contextPath }/user/deleteUserApply?ids=" + arr,
                cache: false,
                dataType: "json",
                success: function (r) {
                    alert('删除成功');
                    oTable.draw();
                }
            });
        }
    });
</script>
