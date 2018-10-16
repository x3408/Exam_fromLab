<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

        <title><tiles:getAsString name="title" ignore="true"/></title>

        <link href="${pageContext.request.contextPath }/resources/css/update/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath }/resources/css/update/font-awesome/css/font-awesome.css" rel="stylesheet">

        <!-- Toastr style -->
        <link href="${pageContext.request.contextPath }/resources/css/update/plugins/toastr/toastr.min.css" rel="stylesheet">

        <!-- Gritter -->
        <link href="${pageContext.request.contextPath }/resources/js/update/plugins/gritter/jquery.gritter.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath }/resources/css/update/animate.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath }/resources/scorecss/font-awesome/css/font-awesome.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath }/resources/scorecss/plugins/jsTree/style.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath }/resources/scorecss/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath }/resources/scorecss/style.css" rel="stylesheet">

        <!-- 原来引用  -->
        <link href="${pageContext.request.contextPath }/resources/scorecss/plugins/dataTables/datatables.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath }/resources/css/dataTables.bootstrap.css" rel="stylesheet">


        <link href="${pageContext.request.contextPath }/resources/css/update/style.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath }/resources/css/update/myStyle.css" rel="stylesheet">

        <!-- Mainly scripts -->
     <%--   <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/validate/jquery.validate.min.js"></script>--%>
        <script src="${pageContext.request.contextPath }/resources/js/jquery-1.11.1.min.js"></script>
        <%--<script src="${pageContext.request.contextPath }/resources/js/update/jquery-3.1.1.min.js"></script>--%>
        <script src="${pageContext.request.contextPath }/resources/js/update/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath }/resources/js/update/plugins/metisMenu/jquery.metisMenu.js"></script>
        <script src="${pageContext.request.contextPath }/resources/js/update/plugins/slimscroll/jquery.slimscroll.min.js"></script>

        <!-- Flot -->
        <script src="${pageContext.request.contextPath }/resources/js/update/plugins/flot/jquery.flot.js"></script>
        <script src="${pageContext.request.contextPath }/resources/js/update/plugins/flot/jquery.flot.tooltip.min.js"></script>
        <script src="${pageContext.request.contextPath }/resources/js/update/plugins/flot/jquery.flot.spline.js"></script>
        <script src="${pageContext.request.contextPath }/resources/js/update/plugins/flot/jquery.flot.resize.js"></script>
        <script src="${pageContext.request.contextPath }/resources/js/update/plugins/flot/jquery.flot.pie.js"></script>

        <!-- Peity -->
        <script src="${pageContext.request.contextPath }/resources/js/update/plugins/peity/jquery.peity.min.js"></script>
        <script src="${pageContext.request.contextPath }/resources/js/update/demo/peity-demo.js"></script>

        <!-- Custom and plugin javascript -->
        <script src="${pageContext.request.contextPath }/resources/js/update/inspinia.js"></script>
        <script src="${pageContext.request.contextPath }/resources/js/update/plugins/pace/pace.min.js"></script>
        <script src="${pageContext.request.contextPath }/resources/js/update/plugins/jsTree/jstree.min.js"></script>

        <!-- jQuery UI -->
        <script src="${pageContext.request.contextPath }/resources/js/update/plugins/jquery-ui/jquery-ui.min.js"></script>

        <!-- GITTER -->
        <script src="${pageContext.request.contextPath }/resources/js/update/plugins/gritter/jquery.gritter.min.js"></script>

        <!-- Sparkline -->
        <script src="${pageContext.request.contextPath }/resources/js/update/plugins/sparkline/jquery.sparkline.min.js"></script>

        <!-- Sparkline demo data  -->
        <script src="${pageContext.request.contextPath }/resources/js/update/demo/sparkline-demo.js"></script>

        <!-- ChartJS-->
        <script src="${pageContext.request.contextPath }/resources/js/update/plugins/chartJs/Chart.min.js"></script>

        <!-- Toastr -->
        <script src="${pageContext.request.contextPath }/resources/js/update/plugins/toastr/toastr.min.js"></script>

        <!-- 原来引用 -->
        <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/dataTables/datatables.min.js"></script>
        <script src="${pageContext.request.contextPath }/resources/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath }/resources/js/respond.min.js"></script>
        <%--    <link href="${pageContext.request.contextPath }/resources/scorecss/bootstrap.min.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath }/resources/scorecss/font-awesome/css/font-awesome.css" rel="stylesheet">

            <link href="${pageContext.request.contextPath }/resources/scorecss/plugins/dataTables/datatables.min.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath }/resources/css/dataTables.bootstrap.css" rel="stylesheet">

            <link href="${pageContext.request.contextPath }/resources/scorecss/animate.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath }/resources/scorecss/style.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath }/resources/scorecss/plugins/iCheck/custom.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath }/resources/scorecss/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath }/resources/scorecss/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath }/resources/scorecss/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath }/resources/scorecss/plugins/datapicker/datepicker3.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath }/resources/scorecss/media.css" rel="stylesheet">

            <!-- Mainly scripts -->
            <script src="${pageContext.request.contextPath }/resources/scorejs/jquery-3.1.1.min.js"></script>
            <script src="${pageContext.request.contextPath }/resources/scorejs/bootstrap.min.js"></script>
            <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/metisMenu/jquery.metisMenu.js"></script>
            <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/slimscroll/jquery.slimscroll.min.js"></script>

            <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/dataTables/datatables.min.js"></script>
            <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/jquery-ui/jquery-ui.min.js"></script>
            <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/validate/jquery.validate.min.js"></script>
            <script src="${pageContext.request.contextPath }/resources/scorejs/bootstrap-modal.js"></script>
            <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/datapicker/bootstrap-datepicker.js"></script>
            <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/jquery-ui/jquery-ui.min.js"></script>
            <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/validate/jquery.validate.min.js"></script>--%>

        <!-- Custom and plugin javascript -->
        <%--<script src="${pageContext.request.contextPath }/resources/scorejs/inspinia.js"></script>
        <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/pace/pace.min.js"></script>
        <script src="${pageContext.request.contextPath }/resources/js/jquery.dataTables.js"></script>
        <!-- Mainly scripts -->
        <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/metisMenu/jquery.metisMenu.js"></script>
        <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/slimscroll/jquery.slimscroll.min.js"></script>
        <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/jeditable/jquery.jeditable.js"></script>

        <!-- Custom and plugin javascript -->
        <script src="${pageContext.request.contextPath }/resources/scorejs/inspinia.js"></script>
        <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/pace/pace.min.js"></script>

        <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/iCheck/icheck.min.js"></script>

        <!-- Peity -->
        <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/peity/jquery.peity.min.js"></script>

        <!-- jqGrid -->
        <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/jqGrid/i18n/grid.locale-en.js"></script>
        <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/jqGrid/jquery.jqGrid.min.js"></script>


        <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/jquery-ui/jquery-ui.min.js"></script>

        <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/fullcalendar/moment.min.js"></script>
        <script src="${pageContext.request.contextPath }/resources/scorejs/plugins/daterangepicker/daterangepicker.js"></script>
        <script src="${pageContext.request.contextPath }/resources/js/datetime/bootstrap-datetimepicker.js"></script>
        <script src="${pageContext.request.contextPath }/resources/js/autocomplete.js"></script>--%>
        <!--[if lt IE 9]>


        <![endif]-->
    </head>
    <body>
        <div id="wrapper">
            <!-- side nav -->
            <tiles:insertAttribute name="left"/>

            <!-- main -->
            <div id="page-wrapper" class="gray-bg dashbard-1">
                <!-- header -->
                <tiles:insertAttribute name="header"/>
                <tiles:insertAttribute name="main"/>
                <tiles:insertAttribute name="footer"/>
            </div>

            <%--        <div class="header">
                        <!-- header -->
                        <tiles:insertAttribute name="header" />
                    </div>--%>

            <%--<div class="footer">
                <tiles:insertAttribute name="footer" />
            </div>--%>
        </div>

        <script>
            $(document).ready(function () {
                setTimeout(function () {
                    toastr.options = {
                        closeButton: true,
                        progressBar: true,
                        showMethod: 'slideDown',
                        timeOut: 4000
                    };
                    //toastr.success('Responsive Admin Theme', 'Welcome to INSPINIA');
                }, 1300);
            });
        </script>
    </body>
</html>