<%@page import="java.net.URLDecoder"%>
<%@page import="entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录页面</title>
<link href="${ bp }/static/css/main.css" rel="stylesheet">
<link href="${ bp }/static/css/validate.css" rel="stylesheet">
</head>
<body>
	<div id="pageContent">
		<h3>登录页面</h3>
		<form id="loginForm">
			<p>用户名：<input type="text" name="username" value="${ cookie.username.value}"></p>
			<p>密码：<input type="password" name="password" value="${ cookie.password.value }"></p>
			<p><img alt="验证码加载失败" title="看不清？点击换一张！" src="${ bp }/kaptcha" onclick="this.src=this.src" id="captcha-img"></p>
			<p>验证码：<input type="text" name="captcha"></p>
			<p><input type="checkbox" name="rememberMe" value="rememberMe" ${ cookie.rememberMe.value eq "rememberMe" ? "checked" : "" }>记住我</p>
			<p><input type="submit" value="登录"></p>
			<p><a href="${ bp }/user/regist.jsp">没有账号？立即注册！</a></p>
			
			<p style="color:red;" id="message"></p>
		</form>
	</div>
	
	<script type="text/javascript" src="${ bp }/static/js/jquery-3.5.1.js"></script>
	<script type="text/javascript">
		$(function() {
			
			$('#loginForm').submit(function() {
				
				$('#captcha-img').attr('src', $('#captcha-img').attr('src'));
				
				$.ajax({
					url: '${ bp }/user?method=login',
					type: 'post',
					data: {
						'username': $('[name="username"]').val(),
						'password': $('[name="password"]').val(),
						'captcha': $('[name="captcha"]').val(),
						'rememberMe': $('[name="rememberMe"]').val()
					},
					dataType: 'json',
					success: function(result) {
						console.log(result);
						if (result.code == 200) {
							location.href = "${bp}/post?method=list";
						} else {
							$('#message').text(result.message);
						}
					},
					error: function() {
						console.log('登录接口请求失败');
					}
				});
				
				return false;
				
			});
			
		})
	</script>
</body>
</html>