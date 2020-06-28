package entity;

import java.util.List;

import lombok.Data;

/**
 * 分页实体
 * @author junki
 * @date 2020年6月11日
 */
@Data
public class Pager<E> {
	
	// 页码
	private Long pageNum;
	
	// 页面数据量
	private Long pageSize;
	
	// limit开始索引
	private Long startIndex;
	
	// 数据表总数据量
	private Long total;
	
	// 总页数
	private Long totalPage;
	
	// 页面显示开始页码
	private Long startPage;
	
	// 页面显示结束页码
	private Long endPage;
	
	// 用于携带查询结果
	private List<E> data;

	/**
	 * 通过三要素创建pager对象
	 * @param pageNum
	 * @param pageSize
	 * @param total
	 */
	public Pager(Long pageNum, Long pageSize, Long total) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.total = total;
		
		this.startIndex = (pageNum - 1) * pageSize;
		
		this.totalPage = (total % pageSize == 0) ? (total / pageSize) : (total / pageSize + 1);
		
		/*
		 *	前5后4，总共10个标签
		 *	totalPage <= 10 
		 *		--> startPage = 1; endPage = totalPage
		 *  totalPage > 10
		 *  	pageNum <= 5 
		 *  		--> startPage = 1; endPage = 10
		 *  	pageNum >= (totalPage - 4) 
		 *  		--> startPage = totalPage - 9; endPage = totalPage
		 *  	pageNum > 5 && pageNum < (totalPage - 4)
		 *  		--> startPage = pageNum - 5; endPage = pageNum + 4
		 */
		
		if (this.totalPage <= 10) {
			this.startPage = 1L; 
			this.endPage = this.totalPage;
		} else {
			if (this.pageNum <= 5L) {
				this.startPage = 1L; 
				this.endPage = 10L;
			} else if (this.pageNum >= (this.totalPage - 4)) {
				this.startPage = this.totalPage - 9; 
				this.endPage = this.totalPage;
			} else {
				this.startPage = this.pageNum - 5; 
				this.endPage = this.pageNum + 4;
			}
		}
		
	}
	
}
