
// 搜索表单id
let form;

// 初始化参数
function initPager(f) {
	form = f;
}

// 获取每页显示数量
let pageSize = $('#paging #pageSizeSelect').val();

// 页码
let pageNum = 1;

$('#paging #pageSizeSelect').change(function() {

	pageSize = $(this).val();

	// 把分页参数追加到表单
	form.attr('action', form.attr('action') + "&pageSize=" + pageSize);
	
	// 提交表单
	form.submit();
	
});

$('#paging a').click(function() {

	pageNum = $(this).data('page');
	
	// 把分页参数追加到表单
	form.attr('action', form.attr('action') + "&pageSize=" + pageSize + "&pageNum=" + pageNum);
	
	// 提交表单
	form.submit();

});
