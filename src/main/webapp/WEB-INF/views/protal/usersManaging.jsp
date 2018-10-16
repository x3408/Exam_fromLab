<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="main">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <p>
                        用户信息：<input id="searchUserInfo" class="myInput" />
                        状态：<%--<input id="searchState" />--%><label id="searchStateObject">状态：</label>
                        <button class="btn btn-success" type="button" id="fnClickSearch"><i class="fa fa-search"></i> <span class="bold">查询</span></button>
                        <button class="btn btn-warning" type="button" id="fnClickClear"><i class="fa fa-circle-o"></i> <span class="bold">置空</span></button>
                    </p>
                    <p>
                        <button class="btn btn-info" type="button" onclick="fnClickAddRow();"><i class="fa fa-plus-square-o"></i>&nbsp;新增</button>
                        <%--<button class="btn btn-info " type="button" id="fnClickEditRows"><i class="fa fa-paste"></i>编辑</button>
                        <button class="btn btn-info " type="button" id="export"><i class="fa fa-cloud-download"></i>导出</button>--%>
                        <button class="btn btn-danger" type="button" id="fnClickDelRows"><i class="fa fa-trash"></i> <span class="bold">删除</span></button>

                        <button class="btn btn-warning" type="button" onclick="updateUserState('2')"><i class="fa fa-key"></i> <span class="bold">注销</span></button>
                        <button class="btn btn-warning" type="button" onclick="updateUserState('3')"><i class="fa fa-lock"></i> <span class="bold">冻结</span></button>
                    </p>
                    <table class="table table-striped table-bordered table-hover "
                           id="editable">
                        <thead>
                        <tr>
                            <th><input type="checkbox" id="checkAll" onchange="ckeckAll(this)" /></th>
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

<script>
    var oTable;
    $(document).ready(function() {
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
            "sAjaxSource": "${pageContext.request.contextPath}/user/userManage/paging",
            "fnServerParams": function ( aoData ) {//向服务器传额外的参数
                aoData.push( { "name": "sid", "value": "1" } );
                aoData.push( { "name": "searchUserInfo", "value": $("#searchUserInfo").val() });
                aoData.push( { "name": "searchState", "value": $("#searchStateObject").val() });
            },
            "aoColumns": [
                { "mData": function(obj){
                    return '<input type="checkbox" id="checkUserId" name="checkUserId" value="'+obj.id+'" >';
                },"sWidth": "20px"},
                { "mData": "userName" },
                { "mData": "name" },
                { "mData": "type" },
                { "mData": "state"},
                { "mData": function(obj){
                    var operate = '<a class="btn btn-primary" href="${pageContext.request.contextPath}/user/viewUser?id='+obj.id+'"><i class="fa fa-edit">修改</i></a> | ' +'&nbsp;&nbsp;';
                    operate += '<a class="btn btn-primary" href="#" onclick="resetUserPassword('+"'"+obj.id+"'"+')"><i class="fa fa-edit">重置密码</i></a>' +'&nbsp;&nbsp;';
                    return operate;
                }}
            ],
            "columnDefs": [
                { "orderable": false, "targets": [0,3,5] },
                { "width": "5%", "targets": 0 },
                { "width": "20%", "targets": 1 },
                { "width": "20%", "targets": 2 },
                { "width": "20%", "targets": 3 },
                { "width": "25%", "targets": 4 },
                { "width": "15%", "targets": 5 },
            ],
            "order": [[ 1, "desc" ]]
        } );

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

    $('#fnClickDelRows').click( function () {
        var arr = [];
        $("[name=checkUserId]:checkbox").each(function() {
            if($(this).prop("checked")) {
                arr.push($(this).val());
            }
        });
        if (arr.length == 0) {
            alert('请选择要删除的用户！');
            return;
        }
        if(confirm("确认删除？")) {
            $.ajax({
                url : "${pageContext.request.contextPath }/user/deleteUser?ids="+arr,
                cache : false,
                dataType : "json",
                success : function(r) {
                    alert('删除成功');
                    oTable.draw();
                }
            });
        };
    } );

    function resetUserPassword(id) {
        $.ajax({
            url : "${pageContext.request.contextPath }/user/resetUserPassword?id="+id,
            type: "GET",
            cache : false,
            dataType : "json",
            success : function(r) {
                alert('重置成功');
                oTable.draw();
            }
        });
    }

    function updateUserState(state) {
        var arr = [];
        $("[name=checkUserId]:checkbox").each(function() {
            if($(this).prop("checked")) {
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
        if(confirm(desc)) {
            $.ajax({
                url : "${pageContext.request.contextPath }/user/updateUserState?ids="+arr+"&state="+state,
                cache : false,
                dataType : "json",
                success : function(r) {
                    if (state == '2') {
                        alert('注销成功');
                    }
                    if (state == '3') {
                        alert('冻结成功');
                    }
                    oTable.draw();
                }
            });
        };
    }
</script>
