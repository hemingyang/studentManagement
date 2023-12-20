<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>管理员注册</title>
    <link rel="stylesheet" href="layui/css/layui.css">
</head>
<body>
<br>
<center><p style="color: black; font-size: 30px;">管理员注册</p></center>

<div class="layui-container">
    <div class="layui-row">
        <div class="layui-col-md6 layui-col-md-offset3">
            <!-- 注册表单 -->
            <form class="layui-form" method="post" id="registrationForm" action="/addUser">
                <div class="layui-form-item">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-block">
                        <input type="text" name="username" required lay-verify="required"
                               placeholder="请输入用户名" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-block">
                        <input type="password" name="password" required lay-verify="required"
                               placeholder="请输入密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button type="submit" class="layui-btn layui-btn-normal">
                            <i class="layui-icon layui-icon-add-1"></i>注册
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="layui/layui.js"></script>

</body>
</html>
