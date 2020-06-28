<%@page import="enums.PostTypeEnum"%>
<%@page import="entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>发帖页面</title>
<link href="${ bp }/static/css/main.css" rel="stylesheet">
<link href="${ bp }/static/css/validate.css" rel="stylesheet">
</head>
<body>
	
	<%@ include file="../common/header.jsp" %>
	
	<div id="pageContent">
		<h3>发帖页面</h3>
		<form action="${ bp }/post?method=add" method="post" id="addForm">
			<p>标题：<input type="text" name="title" value="${ post.title }"></p>
			<p>
				类别：
				<select name="type">
					<option value="">--请选择--</option>
					<c:forEach items="${ PostTypeEnum.values() }" var="type">
						<option value="${ type.ordinal() }">${ type.name }</option>
					</c:forEach>
				</select>
			</p>
			<p>内容：</p>
			<p><textarea name="content" cols="50" rows="10">${ post.content }</textarea></p>
			<p><input type="submit" value="发帖"></p>
			<p style="color:red;">${ message }</p>
		</form>
	</div>

	<script type="text/javascript" src="${ bp }/static/js/jquery-3.5.1.js"></script>
	<script type="text/javascript" src="${ bp }/static/js/jquery.validate.min.js"></script>
	<script type="text/javascript">
		$(function() {
			// 指定表单进行校验
			$('#addForm').validate({
				rules: {
					title: {
						required: true,
						maxlength: 30,
					},
					type: {
						required: true,
					},
					content: 'required'
				},
				messages: {
					title: {
						required: "标题不能为空",
						maxlength: "标题不能超过30个字",
					},
					type: {
						required: "类别不能为空",
					},
					content: "内容不能为空"
				}
			});
		})
	</script>
</body>
</html>