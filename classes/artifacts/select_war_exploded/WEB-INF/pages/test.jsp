<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <!-- Title and other stuffs -->
    <title>Mac风格响应式后台管理模版演示 - 源码之家</title>
    <meta name="author" content="">
    <%@include file="/WEB-INF/pages/common/macTopCommon.jsp" %>
</head>

<body>


<!-- Header starts -->
<%--<header>--%>
    <%--<%@include file="/WEB-INF/pages/top.jsp" %>--%>
<%--</header>--%>
<!-- Header ends -->

<!-- Main content starts -->


    <div class="mainbar">

        <!-- Page heading -->
        <div class="page-head">
            <h2 class="pull-left"><i class="icon-home"></i> 首页</h2>

            <!-- Breadcrumb -->
            <div class="bread-crumb pull-right">
                <a href="#"><i class="icon-home"></i> 首页</a>
                <!-- Divider -->
                <span class="divider">/</span>
                <a href="#" class="bread-current">控制台</a>
            </div>

            <div class="clearfix"></div>

        </div>
        <!-- Page heading ends -->


        <!-- Matter -->

        <div class="matter">
            <div class="container">

                <!-- 搜索页 ================================================== -->
                <div class="row small">
                    <form class="navbar-form center" role="search">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="学号/姓名">
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="学号/姓名">
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="学号/姓名">
                        </div>
                        <button type="button" class="btn btn-default">搜索</button>
                        <button type="button" class="btn btn-info pull-left"><i class="icon-remove"></i>批量删除</button>
                    </form>
                </div>
                <!-- Table -->
                <div class="row">

                    <div class="col-md-12">

                        <div class="widget">

                            <div class="widget-head">
                                <div class="pull-left">用户列表</div>
                                <div class="widget-icons pull-right">
                                    <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a>
                                    <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                </div>
                                <div class="clearfix"></div>
                            </div>

                            <div class="widget-content ">

                                <table class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th class=" text-center"><input type="checkbox" id="selectAll"></th>
                                        <th>序号</th>
                                        <th>姓名</th>
                                        <th>邮箱</th>
                                        <th>电话</th>
                                        <th>qq</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="user" items="${userList}" varStatus="index">
                                        <tr>
                                            <td  class=" text-center"><input type="checkbox" value="${user.id}" /></td>
                                            <td>${index.count}</td>
                                            <td>${user.userName}</td>
                                            <td>${user.userMail}</td>
                                            <td>${user.userPhone}</td>
                                            <td><fmt:formatDate value="${user.gmtCreate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                            <td>
                                                <c:set var="status" value="${user.userStatus}"/>
                                                <c:choose>
                                                    <c:when test="${status eq 1}">
                                                        <span class="label label-success">启用</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="label label-danger">禁用</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:set var="status" value="${user.userStatus}"/>
                                                <c:choose>
                                                    <c:when test="${status eq 0}">
                                                        <button class="btn btn-xs btn-success"><i class="icon-ok"></i>启用</button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button class="btn btn-xs btn-danger"><i class="icon-remove"></i>禁用</button>
                                                    </c:otherwise>
                                                </c:choose>
                                                <button class="btn btn-xs btn-warning"><i class="icon-pencil"></i>编辑</button>
                                                <button class="btn btn-xs btn-danger"><i class="icon-remove">删除</i>
                                                </button>

                                            </td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>

                                <div class="widget-foot center">
                                    <ul class="pagination ">
                                        <c:if test="${page.current-1 eq 0}">
                                            <li><a href="#" class="btn  disabled">上一页</a></li>
                                        </c:if>
                                        <c:if test="${page.current-1 > 0}">
                                            <li><a class="disabled" href="/selectUserBase/userList?page=${page.current-1}">上一页</a></li>
                                            <li><a href="/selectUserBase/userList?page=${page.current-1}">${page.current-1}</a></li>
                                        </c:if>


                                        <li><a href="/selectUserBase/userList?page=${page.current}">${page.current}</a></li>

                                        <c:if test="${page.current+1 <= page.pages}">
                                            <li><a href="/selectUserBase/userList?page=${page.current+1}">${page.current+1}</a></li>
                                        </c:if>
                                        <c:if test="${page.current+2 <= page.pages}">
                                            <li><a href="/selectUserBase/userList?page=${page.current+2}">${page.current+2}</a></li>
                                        </c:if>
                                        <c:if test="${page.current+1 <= page.pages}">
                                            <li><a href="/selectUserBase/userList?page=${page.current+1}">下一页</a></li>
                                        </c:if>
                                        <c:if test="${page.current+1 > page.pages}">
                                            <li><a class="btn  disabled" href="#">下一页</a></li>
                                        </c:if>

                                    </ul>

                                    <div class="clearfix"></div>

                                </div>

                            </div>

                        </div>

                    </div>

                </div>

            </div>
        </div>

        <!-- Matter ends -->
    </div>


    <div class="clearfix"></div>
<%@include file="/WEB-INF/pages/common/macDownCommon.jsp" %>



</body>
</html>