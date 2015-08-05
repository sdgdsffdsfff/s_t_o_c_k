package com.sumur.stock.entity.other;

import java.util.List;
import java.util.Map;

/**
 * 分页信息，分页信息中包含了若干，以及当前的页码信息
 * @author jiamin
 */
public class PageEntity {
	/**
	 * 所有符合数据的总的数据量
	 */
	private int maxCount;

	/**
	 * 所有符合数据的总的页码数量
	 */
	private int maxPage;

	/**
	 * 当前的页面，索引号从0开始
	 */
	private int nowPage;

	/**
	 * 用开发人员设置的，每页最大的数据量，默认为20条
	 */
	private int maxRow = 20;
	
	/**
	 * 当前页开始坐标
	 */
	private int startRow;

	/**
	 * 得到的查询数据
	 */
	private List<?> results;
	
	private Map<String, Object> conditions;
	
	/**
	 * 带其他参数的页面构造函数
	 * @param nowPage 当前页
	 * @param maxRows 每页最大尺寸
	 * @param conditions 查询的其他条件
	 */
    public PageEntity(int nowPage, int maxRow, Map<String, Object> conditions) {
        this.nowPage = nowPage;
        this.maxRow = maxRow;
        this.startRow = maxRow > 0 ? nowPage * maxRow : 0;
        this.conditions = conditions;
    }
    
    /**
     * 不带其他参数分页构造函数
     * @param nowPage 当前页
     * @param maxRows 每页最大尺寸
     */
    public PageEntity(int nowPage, int maxRow) {
        this.nowPage = nowPage;
        this.maxRow = maxRow;
        this.startRow = maxRow > 0 ? nowPage * maxRow : 0;
    }

    
	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getMaxRow() {
		return maxRow;
	}

	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public List<?> getResults() {
		return results;
	}

	public void setResults(List<?> results) {
		this.results = results;
	}

	public Map<String, Object> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	@Override
	public String toString() {
		return "PageEntity [maxCount=" + maxCount + ", maxPage=" + maxPage
				+ ", nowPage=" + nowPage + ", maxRows=" + maxRow
				+ ", startRow=" + startRow + ", results=" + results + "]";
	}
}