<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <!-- Title and other stuffs -->
    <title>Select System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="">
    <%@include file="/WEB-INF/pages/common/macTopCommon.jsp" %>
</head>

<body>



<!-- Main bar -->
<div class="mainbar">

    <!-- Page heading -->
    <div class="page-head">
        <!-- Page heading -->
        <h2 class="pull-left">
            <!-- page meta -->
            <span class="page-meta">教师添加</span>
        </h2>


        <!-- Breadcrumb -->
        <div class="bread-crumb pull-right">
            <a href="index.html"><i class="icon-home"></i> 教师管理</a>
            <!-- Divider -->
            <span class="divider">/</span>
            <a href="#" class="bread-current">首页</a>
        </div>

        <div class="clearfix"></div>

    </div>
    <!-- Page heading ends -->



    <!-- Matter -->

    <div class="matter">
        <div class="container">

            <div class="row">

                <div class="col-md-12">


                    <div class="widget wgreen">

                        <div class="widget-head">
                            <div class="pull-left">教师</div>
                            <div class="widget-icons pull-right">
                                <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a>
                                <a href="#" class="wclose"><i class="icon-remove"></i></a>
                            </div>
                            <div class="clearfix"></div>
                        </div>

                        <div class="widget-content">
                            <div class="padd">
                                <hr />
                                <!-- Form starts.  -->
                                <form class="form-horizontal" role="form" id="addForm">

                                    <div class="form-group ">

                                        <label class="col-lg-1 control-label">职称</label>
                                        <div class="col-lg-2">
                                            <select name="teaPosition"  class="form-control">
                                                <option value="" selected>---请选择---</option>
                                                <c:forEach var="teaPosition" items="${requestScope.teaPosition}">
                                                    <option value="${teaPosition.key}">${teaPosition.value}</option>
                                                </c:forEach>
                                            </select>
                                        </div>




                                        <label class="col-lg-1 control-label">学历</label>
                                        <div class="col-lg-2">
                                            <select name="teaEducation" class="form-control">
                                                <option value="" selected>---请选择---</option>
                                                <c:forEach var="teaEducation" items="${requestScope.teaEducation}">
                                                    <option value="${teaEducation.key}">${teaEducation.value}</option>
                                                </c:forEach>
                                            </select>
                                        </div>




                                        <label class="col-lg-1 control-label">所属系别</label>
                                        <div class="col-lg-2" >
                                            <select name="teaDepId" class="form-control">
                                                <option value="" selected>---请选择---</option>
                                                <c:forEach var="dep" items="${requestScope.teaDepList}">
                                                    <option value="${dep.id}">${dep.depName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>



                                    </div>


                                    <div class="form-group">
                                        <label class="col-lg-4 control-label">教师账号</label>
                                        <div class="col-lg-8">
                                            <input type="text" class="form-control" name="userCode" placeholder="教师账号">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-4 control-label">教师姓名</label>
                                        <div class="col-lg-8">
                                            <input type="text" class="form-control" name="userName" placeholder="教师姓名">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-4 control-label">教师性别</label>
                                        <div class="col-lg-8">
                                                <label class="radio-inline">
                                                    <input type="radio" name="userSex"  value="1" checked>男
                                                </label>
                                                <label class="radio-inline">
                                                    <input type="radio" name="userSex"   value="2">女
                                                </label>
                                            </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-4 control-label">邮箱</label>
                                        <div class="col-lg-8">
                                            <input type="email" class="form-control" name="userMail" placeholder="邮箱">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-4 control-label">电话</label>
                                        <div class="col-lg-8">
                                            <input type="tel" class="form-control" name="userPhone" placeholder="电话">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-4 control-label">qq</label>
                                        <div class="col-lg-8">
                                            <input type="text" class="form-control" name="userQq" placeholder="qq">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-4 control-label">所修专业</label>
                                        <div class="col-lg-8">
                                            <input type="text" class="form-control" name="teaMajorName" placeholder="所修专业">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-4 control-label">研究方向</label>
                                        <div class="col-lg-8">
                                            <input type="text" class="form-control" name="teaDirection" placeholder="研究方向">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-4 control-label">教师介绍</label>
                                        <div class="col-lg-8">
                                            <textarea class="form-control" rows="4" name="teaInfo" placeholder="教师介绍"></textarea>
                                        </div>
                                    </div>


                                    <hr />
                                    <div class="form-group">
                                        <div class="col-lg-offset-1 col-lg-9">
                                            <button type="button" id="addSubmit" class="btn btn-success">提交</button>
                                            <button type="reset" class="btn btn-info">重填</button>
                                            <button type="button" class="btn btn-info" onclick="window.history.go(-1);">返回</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="widget-foot">
                            <!-- Footer goes here -->
                        </div>
                    </div>

                </div>

            </div>

        </div>
    </div>

    <!-- Matter ends -->

</div>

<!-- Mainbar ends -->
<div class="clearfix"></div>

<%@include file="/WEB-INF/pages/common/macDownCommon.jsp" %>

<script type="text/javascript">

    $(document).ready(function() {
        /**
         * 下面是进行插件初始化
         * 你只需传入相应的键值对
         * */
        $('#addForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {/*输入框不同状态，显示图片的样式*/
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {/*验证*/
                teaPosition: {/*键名username和input name值对应*/
                    message: 'The username is not valid',
                    validators: {
                        notEmpty: {/*非空提示*/
                            message: '职称不能为空'
                        }
                    }
                },
                teaEducation: {/*键名username和input name值对应*/
                    message: 'The username is not valid',
                    validators: {
                        notEmpty: {/*非空提示*/
                            message: '学历不能为空'
                        }
                    }
                },
                teaDepName: {/*键名username和input name值对应*/
                    message: 'The username is not valid',
                    validators: {
                        notEmpty: {/*非空提示*/
                            message: '所属系别不能为空'
                        }
                    }
                },
                userCode: {/*键名username和input name值对应*/
                    message: 'The username is not valid',
                    validators: {
                        notEmpty: {/*非空提示*/
                            message: '账号不能为空'
                        },
                        stringLength: {/*长度提示*/
                            min: 8,
                            max: 8,
                            message: '账号长度必须为8位'
                        }/*最后一个没有逗号*/
                    }
                },
                userName: {/*键名username和input name值对应*/
                    message: 'The username is not valid',
                    validators: {
                        notEmpty: {/*非空提示*/
                            message: '姓名不能为空'
                        },
                        stringLength: {/*长度提示*/
                            min: 2,
                            max: 8,
                            message: '姓名长度必须为2到8位'
                        }/*最后一个没有逗号*/
                    }
                },
                userSex: {
                    validators: {
                        notEmpty: {
                            message: '性别未选择'
                        }
                    }
                },
                userPhone: {
                    message:'格式错误',
                    validators: {
                        notEmpty: {
                            message: '电话号码不能为空'
                        },
                        stringLength: {
                            min: 7,
                            max: 15,
                            message: '电话号码长度必须在7到15之间'
                        }
                    }
                },
                userQq: {
                    message:'格式错误',
                    validators: {
                        notEmpty: {
                            message: 'QQ号码不能为空'
                        }
                    }
                },
                userMail: {
                    validators: {
                        notEmpty: {
                            message: '邮箱不能为空'
                        },
                        emailAddress: {
                            message: '邮箱格式错误'
                        }
                    }
                }
            }
        });
    });

    $(function(){

        $("#addSubmit").click(function(){
            //获取表单对象
            var bootstrapValidator = $("#addForm").data('bootstrapValidator');
            //手动触发验证
            bootstrapValidator.validate();

            if(bootstrapValidator.isValid()){
            $.ajax({
                type: "post",
                url: "${ctx}/selectUserBase/teaAdd",
                data: $("#addForm").serialize(),
                dataType:"json",
                success:function(msg){
                    if("OK"!=msg){
                        alert(" 😅 "+msg);
                    }else {
                        alert(" 😎 添加成功","",function () {
                            location.href="${ctx}/selectUserBase/teaList";
                        },{type:"success",confirmButtonText:"好的"});
                    }

                },
                error: function(e) {
                    alert(" 😥 系统异常，请与我们的工程师小哥哥联系！");
                }
            });
            }
        });

    });


    /* Curve chart ends */
</script>

</body>
</html>