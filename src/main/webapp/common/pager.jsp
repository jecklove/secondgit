<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="paging">
	<span>共${ pager.total }条数据</span>
	
	<span>
		每页显示
		<select id="pageSizeSelect">
			<option value="5" ${ pager.pageSize eq 5 ? "selected" : "" } >5</option>
			<option value="10" ${ pager.pageSize eq 10 ? "selected" : "" } >10</option>
			<option value="20" ${ pager.pageSize eq 20 ? "selected" : "" } >20</option>
		</select>
		条数据
	</span>
	
	<span>共${ pager.totalPage }页</span>
	
	<c:if test="${ pager.pageNum > 1 }">
		<span><a href="#" data-page="1">首页</a></span>
		<span><a href="#" data-page="${ pager.pageNum - 1 }">上一页</a></span>
	</c:if>
	
	<c:forEach begin="${ pager.startPage }" end="${ pager.endPage }" var="i">
		<c:if test="${ pager.pageNum eq i }">
			<span>${ i }</span>
		</c:if>
		<c:if test="${ pager.pageNum ne i }">
			<span><a href="#" data-page="${ i }">${ i }</a></span>
		</c:if>
	</c:forEach>
	
	<c:if test="${ pager.pageNum < pager.totalPage }">
		<span><a href="#" data-page="${ pager.pageNum + 1 }">下一页</a></span>
		<span><a href="#" data-page="${ pager.totalPage }">尾页</a></span>
	</c:if>
</div>
</body>
</html>