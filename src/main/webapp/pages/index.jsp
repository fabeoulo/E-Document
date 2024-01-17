<%-- 
    Document   : page1
    Created on : 2017/4/19, 上午 09:35:39
    Author     : Wei.Cheng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="user" property="principal" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%--<meta http-equiv="refresh" content="${pageContext.session.maxInactiveInterval};url="/> --%>
        <sec:csrfMetaTags/>
        <title>${initParam.pageTitle}</title>
        <link rel="shortcut icon" href="<c:url value="/images/favicon.ico" />"/>

        <link href="<c:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />" rel="stylesheet">
        <link href="<c:url value="/webjars/bootstrap/3.3.7/css/bootstrap-theme.min.css" />" rel="stylesheet">
        <link href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css" />" rel="stylesheet">
        <link href="<c:url value="/webjars/jquery-ui-themes/1.12.1/redmond/jquery-ui.min.css" />" rel="stylesheet">

        <link href="<c:url value="/webjars/free-jqgrid/4.14.1/css/ui.jqgrid.min.css" />" rel="stylesheet"/>
        <link href="<c:url value="/webjars/free-jqgrid/4.14.1/plugins/css/ui.multiselect.css" />" rel="stylesheet"/>

        <link href="<c:url value="/css/sb-admin-2.min.css" />" rel="stylesheet">
        <link href="<c:url value="/css/metisMenu.min.css" />" rel="stylesheet">
        <link href="<c:url value="/css/sbadmin2-sidebar-toggle.css" />" rel="stylesheet">
        <link href="<c:url value="/css/sys-beta.css" />" rel="stylesheet">

        <script src="<c:url value="/webjars/jquery/1.12.4/jquery.min.js" />"></script>

        <script src="<c:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js" />"></script>

        <script src="<c:url value="/webjars/jquery-ui/1.12.1/jquery-ui.min.js" />"></script>

        <script src="<c:url value="/webjars/jquery-form/4.2.1/jquery.form.min.js" />"></script>

        <script src="<c:url value="/webjars/free-jqgrid/4.14.1/js/jquery.jqgrid.min.js" />"></script>
        <script src="<c:url value="/webjars/free-jqgrid/4.14.1/js/grid.jqueryui.js" />"></script>
        <script src="<c:url value="/webjars/free-jqgrid/4.14.1/js/grid.formedit.js" />"></script>
        <script src="<c:url value="/webjars/free-jqgrid/4.14.1/js/jqmodal.js" />"></script>
        <script src="<c:url value="/webjars/free-jqgrid/4.14.1/plugins/min/ui.multiselect.js" />"></script>

        <script src="<c:url value="/js/sb-admin-2.min.js" />"></script>
        <script src="<c:url value="/js/metisMenu.min.js" />"></script>

        <script src="<c:url value="/js/jqgrid-custom-setting.js" />"></script> 
        <script src="<c:url value="/js/sessionExpiredDetect.js" />"></script>
        <script src="<c:url value="/js/jquery.blockUI.js" />"></script>

        <c:set var="isBeta" value="${initParam.betaMode == 1}" />
        <script>
            var current_include_page_name = "";
            $(function () {
                var page = $("#exchange_page");

                $(".redirect-link").on("click", function () {
                    var target_page_name = $(this).attr("href");
                    if (target_page_name != current_include_page_name && target_page_name != "#") {
                        current_include_page_name = target_page_name;
                        page.block({message: "loading"});
                        page.load(target_page_name, function (response, status, xhr) {
                            if (status == "error") {
                                var msg = "Sorry but there was an error: ";
                                page.html(msg + xhr.status + " " + xhr.statusText);
                            }
                            page.unblock();
                        });
                    }
                    return false;
                });

                $("#preload_page").trigger("click");

                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                $(document).ajaxSend(function (e, xhr, options) {
                    xhr.setRequestHeader(header, token);
                });

                $("#menu-toggle").click(function (e) {
                    e.preventDefault();

                    $("#wrapper").toggleClass("toggled");

                    $('#wrapper.toggled').find("#sidebar-wrapper").find(".collapse").collapse('hide');

                });
            });
        </script>

    </head>
    <body>
        <div id="wrapper" >

            <!-- Navigation -->
            <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <!--                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                                            <span class="sr-only">Toggle navigation</span>
                                            <span class="icon-bar"></span>
                                            <span class="icon-bar"></span>
                                            <span class="icon-bar"></span>
                                        </button> -->
                    <!--                    <ul class="nav navbar-top-links navbar-right">
                                            <li>
                                                menu toggle button 
                                                <button id="menu-toggle" type="button" data-toggle="button" class="btn btn-default btn-xs">
                                                    <i class="fa fa-exchange fa-fw"></i>
                                                </button>
                                            </li>
                                        </ul>-->
                    <c:if test="${isBeta}">
                        <a href="#" class="navbar-left">
                            <img src="<c:url value="/images/beta_icon.png" />" id='beta-sign'/>
                        </a>
                    </c:if>
                    <a class="navbar-brand" href="<c:url value="/" />">${initParam.pageTitle}</a>
                </div>
                <!-- /.navbar-header -->

                <ul class="nav navbar-top-links navbar-right">
                    <li class="dropdown">
                    <lable for="jobnumber">Jobnumber: </lable><font id="jobnumber"><c:out value="${user.jobnumber}" /></font> / 
                    <lable for="name">Name: </lable><font id="name"><c:out value="${user.username}" /></font> / 
                    <lable for="unit">Unit: </lable><font id="unit"><c:out value="${user.unit.name}" /></font>
                    </li>

                    <!-- /.dropdown -->
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                            </li>
                            <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <form id="logout_form" action="<c:url value="/logout" />" method="post">
                                    <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
                                </form>
                                <a href="#" onclick="$('#logout_form').submit()"><i class="fa fa-sign-out fa-fw"></i> logout</a>
                            </li>
                        </ul>
                        <!-- /.dropdown-user -->
                    </li>
                    <!-- /.dropdown -->
                </ul>
                <!-- /.navbar-top-links -->

                <!-- Sidebar wrapper over SB Admin 2 sidebar -->
                <div id="sidebar-wrapper">

                    <div class="navbar-default sidebar" role="navigation">
                        <div class="sidebar-nav navbar-collapse">
                            <ul class="nav in" id="side-menu">
                                <li>
                                    <a href="#" class="active"><i class="fa fa-dashboard fa-fw"></i> 
                                        <span class="masked">
                                            Dashboard
                                        </span>
                                    </a>
                                </li>
                                <li>
                                    <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> 
                                        <span class="masked">
                                            Charts<span class="fa arrow"></span>
                                        </span>
                                    </a>
                                    <ul class="nav nav-second-level">
                                        <li>
                                            <a class="redirect-link" href="chart.jsp">Flot Charts</a>
                                        </li>
                                        <li>
                                            <a class="redirect-link" href="chart.jsp">Morris.js Charts</a>
                                        </li>
                                    </ul>
                                    <!-- /.nav-second-level -->
                                </li>
                                <li>
                                    <a href="#"><i class="fa fa-table fa-fw"></i> Tables<span class="fa arrow"></span></a>
                                    <ul class="nav nav-second-level">
                                        <li>
                                            <a class="redirect-link" id="preload_page" href="worktime.jsp">工時大表</a>
                                        </li>
                                        <sec:authorize access="hasAnyRole('ADMIN', 'OPER', 'AUTHOR')">
                                            <li>
                                                <a class="redirect-link" href="author/worktime_insert_series.jsp">系列機種批次新增</a>
                                            </li>
                                        </sec:authorize>
                                        <li>
                                            <a class="redirect-link" href="conversion.jsp">工時對照表</a>
                                        </li>
                                        <li>
                                            <a class="redirect-link" href="flowPermutations.jsp">重工途程排列組合表</a>
                                        </li>
                                        <sec:authorize access="hasAnyRole('ADMIN', 'OPER')">
                                            <li>
                                                <a class="redirect-link" href="mod/flow.jsp">Flow</a>
                                            </li>
                                            <li>
                                                <a class="redirect-link" id="" href="mod/user.jsp">User</a>
                                            </li>
                                            <li>
                                                <a class="redirect-link" href="mod/pending.jsp">Pending</a>
                                            </li>
                                            <li>
                                                <a class="redirect-link" href="mod/preAssy.jsp">PreAssy</a>
                                            </li>
                                            <li>
                                                <a class="redirect-link" href="mod/type.jsp">Type</a>
                                            </li>
                                            <li>
                                                <a class="redirect-link" href="mod/cobot.jsp">Cobot</a>
                                            </li>
                                             <li>
                                                <a class="redirect-link" href="mod/outlabel.jsp">OutLabel</a>
                                            </li>
                                             <li>
                                                <a class="redirect-link" href="mod/cartonlabel.jsp">CartonLabel</a>
                                            </li>
                                            <li>
                                                <a class="redirect-link" href="mod/batchUpdate.jsp">大表批次更新</a>
                                            </li>
                                        </sec:authorize>
                                    </ul>
                                </li>
                                <li>
                                    <a href="#"><i class="fa fa-wrench fa-fw"></i> UI Elements<span class="fa arrow"></span></a>
                                    <ul class="nav nav-second-level"> 
                                        <li>
                                            <a class="redirect-link" href="audit.jsp">資料版本查詢</a>
                                        </li>
                                        <sec:authorize access="hasRole('ADMIN')">
                                            <li>
                                                <a class="redirect-link" href="admin/fileupload.jsp">Excel文件上傳</a>
                                            </li>
                                            <li>
                                                <a class="redirect-link" href="admin/worktime_permission.jsp">欄位權限設定</a>
                                            </li>
                                            <li>
                                                <a class="redirect-link" href="admin/wowface.jsp">Not exist page</a>
                                            </li>
                                            <li>
                                                <a href="test/test.jsp" onclick="">測試區</a>
                                            </li>
                                        </sec:authorize>
                                    </ul>
                                    <!-- /.nav-second-level -->
                                </li>
                            </ul>
                        </div>
                        <!-- /.sidebar-collapse -->
                    </div>
                    <!-- /.navbar-static-side -->
                </div>
            </nav>

            <div id="page-wrapper">

                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-12">
                        <!-- /.panel -->
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div id="exchange_page" class="row">
                                </div>
                                <!-- /.row -->
                            </div>
                            <!-- /.panel-body -->
                        </div>
                    </div>
                </div>
            </div>
            <!-- /#page-wrapper -->
        </div>
        <c:if test="${isBeta}">
            <jsp:include page="footer_beta_remind.jsp" />
        </c:if>
    </body>
</html>
