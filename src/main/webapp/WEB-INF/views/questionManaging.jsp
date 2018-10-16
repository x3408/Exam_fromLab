<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="main">
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <p>
                            关键字：<input id="searchKey" class="myInput"/>
                            试题类型：<label id="searchTypeObject">试题类型：</label>
                            状态：<label id="searchStateObject">状态：</label>
                            <button class="btn btn-success" type="button" id="fnClickSearch"><i
                                    class="fa fa-search"></i> <span class="bold">查询</span></button>
                            <button class="btn btn-warning" type="button" id="fnClickClear"><i
                                    class="fa fa-circle-o"></i> <span class="bold">置空</span></button>
                            <button class="btn btn-info" type="button" onclick="insertQuestionView();"
                                    id="insertButton"><i
                                    class="fa fa-plus-square-o"></i>&nbsp;新增
                            </button>
                            <button class="btn btn-primary" type="button" onclick="updateQuestionState('0')"
                                    id="upButton"><i
                                    class="fa fa-play"></i> <span class="bold">启用</span></button>
                            <button class="btn btn-warning " type="button" onclick="updateQuestionState('1')"
                                    id="downButton"><i
                                    class="fa fa-stop"></i> <span class="bold">停用</span></button>
                        </p>
                        <table class="table table-striped table-bordered table-hover "
                               id="editable">
                            <thead>
                            <tr>
                                <th><input type="checkbox" id="checkAll" onchange="ckeckAll(this)"/></th>
                                <th width="200">ID号</th>
                                <th width="100">创建时间</th>
                                <th width="100">类型</th>
                                <th width="200">题目</th>
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
<jsp:include page="insertQuestionView.jsp"></jsp:include>
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
                    document.getElementById("insertButton").style.display = "none";
                    document.getElementById("upButton").style.display = "none";
                    document.getElementById("downButton").style.display = "none";
                }
            },
            error: function (msg) {
                alert("出错了！");
            }
        });
        var selectObj = '<select class="form-control" id="searchType" name="searchType"  class="myInput">';
        selectObj += '<option value="">全部</option>';
        selectObj += '<option value="0">选择题</option>';
        selectObj += '<option value="1">判断题</option>';
        selectObj += '</select>';
        $('#searchTypeObject').html(selectObj);
        var selectStateObj = '<select class="form-control" id="searchState" name="searchState"  class="myInput">';
        selectStateObj += '<option value="">全部</option>';
        selectStateObj += '<option value="0">正常</option>';
        selectStateObj += '<option value="1">弃用</option>';
        selectStateObj += '</select>';
        $('#searchStateObject').html(selectStateObj);
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
            "sAjaxSource": "${pageContext.request.contextPath}/exam/questionManage/paging",
            "fnServerParams": function (aoData) {//向服务器传额外的参数
                aoData.push({"name": "searchKey", "value": $("#searchKey").val()});
                aoData.push({"name": "searchType", "value": $("#searchType").val()});
                aoData.push({"name": "searchState", "value": $("#searchState").val()});
            },
            "aoColumns": [
                {
                    "mData": function (obj) {
                        return '<input type="checkbox" id="checkQuestionId" name="checkQuestionId" value="' + obj.id + '" >';
                    }, "sWidth": "20px"
                },
                {"mData": "id"},
                {"mData": "createDate"},
                {"mData": "questionType"},
                {"mData": "questionTitle"},
                {"mData": "questionState"},
                {
                    "mData": function (obj) {
                        var operate = '<a class="btn btn-primary"  onclick="questionView(' + "'" + obj.id + "'" + ')" ><i class="fa fa-edit">查看</i></a> | ' + '&nbsp;&nbsp;';
                        operate += '<a class="btn btn-primary" onclick="questionEdit(' + "'" + obj.id + "'" + ')" ><i class="fa fa-edit">编辑</i></a> | ' + '&nbsp;&nbsp;';
                        operate += '<a class="btn btn-primary" href="#" onclick="deleteQuestion(' + "'" + obj.id + "'" + ')" ><i class="fa fa-edit">删除</i></a>' + '&nbsp;&nbsp;';
                        return operate;
                    }
                }
            ],
            "columnDefs": [
                {"orderable": false, "targets": [0, 6]},
                {"width": "20%", "targets": 0},
                {"width": "18%", "targets": 1},
                {"width": "15%", "targets": 2},
                {"width": "10%", "targets": 3},
                {"width": "25%", "targets": 4},
                {"width": "9%", "targets": 5},
                {"width": "19%", "targets": 6},
            ],
            "order": [[2, "desc"]]
        });
    });

    /*搜索*/
    $('#fnClickSearch').click(function () {
        oTable.draw();
    });
    /*重置*/
    $('#fnClickClear').click(function () {
        $("#searchKey").val("");
        $("#searchState").val("");
        $("#searchType").val("");
        oTable.draw();
    });

    /*
    显示问题插入框
     */
    $("#insertButton").click=function () {
        $("#selectQuestionView").val(" ");
    }
    function insertQuestionView() {
        $('#selectQuestionView').modal('show');
    }

    /*
       查看问题
        */
    function questionView(id) {
//        alert('查看');
//        alert(id);
        $.ajax({
            url: "${pageContext.request.contextPath}/exam/questionView?id=" + id,
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
                    $('#selectQuestionView').modal('show');
                    var alter = document.getElementById("alternativeOptionVisible");
                    var choiceAnswer = document.getElementById("choiceAnswer");
                    var judgeAnswer = document.getElementById("judgeAnswer");
                    if (data.obj.questionType == 0) {
//                        alert('选择题,显示')
                        $("#choiceAnswer input").attr("type","radio");
                        $(":radio[name='rightAnswer'][value=" + data.obj.rightAnswer + "]").attr("checked", "checked");
                        alter.style.display = "block";
                        choiceAnswer.style.display = "block";
                        judgeAnswer.style.display = "none";

                    } else if(data.obj.questionType == 2){      //2多选题
                        $("#choiceAnswer input").attr("type","checkbox");
                        var answer = data.obj.rightAnswer.split(',');
                        for (var j=0; j<answer.length; j++) {
                            $(":checkbox[name='rightAnswer'][value=" + answer[j] + "]").attr("checked", "checked");
                        }
                        alter.style.display = "block";
                        choiceAnswer.style.display = "block";
                        judgeAnswer.style.display = "none";
                    } else {
                        $("#choiceAnswer input").attr("type","radio");
                        $(":radio[name='rightAnswer'][value=" + data.obj.rightAnswer + "]").attr("checked", "checked");
//                        alert('填空题,隐藏')
                        alter.style.display = "none";
                        choiceAnswer.style.display = "none";
                        judgeAnswer.style.display = "block";

                    }
//                    alert(data.obj.questionTitle);
                    $('#questionTheme').val(data.obj.questionTheme);
                    $('#questionTitle').val(data.obj.questionTitle);
                    $('#questionType').val(data.obj.questionType);
                    $('#alternativeOption1').val(data.obj.alternativeOption1);
                    $('#alternativeOption2').val(data.obj.alternativeOption2);
                    $('#alternativeOption3').val(data.obj.alternativeOption3);
                    $('#alternativeOption4').val(data.obj.alternativeOption4);

//                    $('#rightAnswer').val(data.obj.rightAnswer);
//                    alert('完成数据回显');
                    $('#show1').hide();
                }
                flag = true;
            },
            error: function (msg) {
                //alert("出错了！");
            }
        });
    }

    /*
    编辑问题
     */
    function questionEdit(id) {
//         alert(id);
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
                        url: "${pageContext.request.contextPath}/exam/questionView?id=" + id,
                        type: "GET",
                        dataType: "json",
                        async: false,
                        cache: false,
                        contentType: "application/json",
                        success: function (data) {
                            if (!data.success) {
                                //alert(data.msg);
                                flag = false;
                            } else {
                                $('#selectQuestionView').modal('show');
//                    alert(data.obj.questionTitle);
                                var alter = document.getElementById("alternativeOptionVisible");
                                var choiceAnswer = document.getElementById("choiceAnswer");
                                var judgeAnswer = document.getElementById("judgeAnswer");
                                if (data.obj.questionType == 0) {
//                        alert('选择题,显示')
                                    $("#choiceAnswer input").attr("type","radio");
                                    $(":radio[name='rightAnswer'][value=" + data.obj.rightAnswer + "]").attr("checked", "checked");
                                    alter.style.display = "block";
                                    choiceAnswer.style.display = "block";
                                    judgeAnswer.style.display = "none";

                                } else if(data.obj.questionType == 2){      //2多选题
                                    $("#choiceAnswer input").attr("type","checkbox");
                                    var answer = data.obj.rightAnswer.split(',');
                                    for (var j=0; j<answer.length; j++) {
                                        $(":checkbox[name='rightAnswer'][value=" + answer[j] + "]").attr("checked", "checked");
                                    }
                                    alter.style.display = "block";
                                    choiceAnswer.style.display = "block";
                                    judgeAnswer.style.display = "none";
                                } else {
                                    $("#choiceAnswer input").attr("type","radio");
                                    $(":radio[name='rightAnswer'][value=" + data.obj.rightAnswer + "]").attr("checked", "checked");
//                        alert('填空题,隐藏')
                                    alter.style.display = "none";
                                    choiceAnswer.style.display = "none";
                                    judgeAnswer.style.display = "block";

                                }
                                $('#questionTheme').val(data.obj.questionTheme);
                                $('#questionTitle').val(data.obj.questionTitle);
                                $('#questionType').val(data.obj.questionType);
                                $('#alternativeOption1').val(data.obj.alternativeOption1);
                                $('#alternativeOption2').val(data.obj.alternativeOption2);
                                $('#alternativeOption3').val(data.obj.alternativeOption3);
                                $('#alternativeOption4').val(data.obj.alternativeOption4);
//                    $('#rightAnswer').val(data.obj.rightAnswer);
                                $('#show1').show();
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

    function deleteQuestion(id) {
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
                            url: "${pageContext.request.contextPath }/exam/deteleQuestionById?id=" + id,
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

    /*
    选择所有check
     */
    function ckeckAll(obj) {
        var $checked = $(obj).prop("checked");
        $("td input[type='checkbox']").each(function () {
            $(this).prop("checked", $checked);
        });
        $(obj).prop("checked", $checked);
    }

    /*
    更新问题的状态
     */
    function updateQuestionState(state) {
        var arr = [];
        $("[name=checkQuestionId]:checkbox").each(function () {
            if ($(this).prop("checked")) {
//                alert('选中');
                arr.push($(this).val());
            }
        });
        if (arr.length == 0 && state == '0') {
            alert('请选择要启用的问题！');
            return;
        }
        if (arr.length == 0 && state == '1') {
            alert('请选择要弃用的问题！');
            return;
        }
        var desc = '确认启用？';
        if (state == '1') {
            desc = "确认弃用？";
        }
//        alert("aa" + confirm(desc));
        if (confirm(desc)) {
            $.ajax({
                url: "${pageContext.request.contextPath }/exam/updateQuestionState?ids=" + arr + "&state=" + state,
                cache: false,
                dataType: "json",
                success: function (r) {
                    if (state == '0') {
                        alert('启用成功');
                    }
                    if (state == '1') {
                        alert('弃用成功');
                    }
                    oTable.draw();
                }
            });
        };
    }
</script>
