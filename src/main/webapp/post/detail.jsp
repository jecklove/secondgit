<%@page import="entity.Post"%>
<%@page import="enums.PostTypeEnum"%>
<%@page import="java.util.List"%>
<%@page import="service.PostService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>帖子列表页</title>
<link href="${ bp }/static/css/main.css" rel="stylesheet">
<style type="text/css">
	p {
		margin: 10px 0;
	}
	
	#postDetail,#commentList {
		width: 100%;
		border: 1px solid green;
		padding: 5px;
	}
</style>
</head>
<body>
	<%@ include file="../common/header.jsp" %>

	<div id="pageContent">
	
		<div id="postDetail">
			<p>标题：${ post.title }</p>
			<p>类别：${ PostTypeEnum.values()[post.type].name }</p>
			<p>作者：${ post.nickname }</p>
			<p>发帖时间：<fmt:formatDate value="${ post.create_time }" pattern="yyyy年MM月dd日 HH点mm分ss秒"/> </p>
			<p>内容：</p>
			<p>${ post.content }</p>
		</div>
		
		<br><br>
		
		<div id="commentList">
			<form action="${ bp }/comment?method=add&post_id=${ post.id }" method="post">
				<p>
					<input type="text" name="content" placeholder="在此发表评论..." value="${ content }" style="width: 300px">
					<input type="submit" value="发表评论">
					<span>${ message }</span>
				</p>
			</form>
			<hr>
			<c:forEach items="${ post.comments }" var="comment" varStatus="vs">
				<p>${ comment.content }</p>
				<p align="right">${ comment.nickname }--<fmt:formatDate value="${ comment.create_time }" pattern="yyyy年MM月dd日 HH点mm分ss秒"/></p>
				<hr>
			</c:forEach>
		</div>
		
	</div>
</body>
</html>