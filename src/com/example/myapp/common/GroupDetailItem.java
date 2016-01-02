/*
 * GroupDetailItem.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp.common;

import java.util.List;

/**
 * 明细页面可展开视图
 * 
 * @author Engle
 * 
 */
public class GroupDetailItem {

	/** 日期索引 */
	private List<String> dateIndex;

	/** 日期单位,如:周,月 */
	private String dateTag;

	/** 日期分布 */
	private List<String> dateRangeList;

	/** 分布开始时间 */
	private List<String> dateRangeStartList;

	/** 分布结束时间 */
	private List<String> dateRangeEndList;

	/** 消费总金额 */
	private List<String> dateAmountList;

	/**
	 * 构造函数
	 * 
	 * @param dateIndex
	 *            日期索引
	 * @param dateRangeList
	 *            日期周分布
	 * @param dateRangeStartList
	 *            周开始日期
	 * @param dateRangeEndList
	 *            周结束日期
	 * @param AmountList
	 *            周消费金额
	 */
	public GroupDetailItem(List<String> dateIndex, String dateTag,
			List<String> dateRangeList, List<String> dateRangeStartList,
			List<String> dateRangeEndList, List<String> dateAmountList) {
		this.dateIndex = dateIndex;
		this.dateTag = dateTag;
		this.dateRangeList = dateRangeList;
		this.dateRangeStartList = dateRangeStartList;
		this.dateRangeEndList = dateRangeEndList;
		this.dateAmountList = dateAmountList;
	}

	/**
	 * @return the dateIndex
	 */
	public List<String> getDateIndex() {
		return dateIndex;
	}

	/**
	 * @param dateIndex
	 *            the dateIndex to set
	 */
	public void setDateIndex(List<String> dateIndex) {
		this.dateIndex = dateIndex;
	}

	/**
	 * @return the dateTag
	 */
	public String getDateTag() {
		return dateTag;
	}

	/**
	 * @param dateTag
	 *            the dateTag to set
	 */
	public void setDateTag(String dateTag) {
		this.dateTag = dateTag;
	}

	/**
	 * @return the dateRangeList
	 */
	public List<String> getDateRangeList() {
		return dateRangeList;
	}

	/**
	 * @param dateRangeList
	 *            the dateRangeList to set
	 */
	public void setDateRangeList(List<String> dateRangeList) {
		this.dateRangeList = dateRangeList;
	}

	/**
	 * @return the dateRangeStartList
	 */
	public List<String> getDateRangeStartList() {
		return dateRangeStartList;
	}

	/**
	 * @param dateRangeStartList
	 *            the dateRangeStartList to set
	 */
	public void setDateRangeStartList(List<String> dateRangeStartList) {
		this.dateRangeStartList = dateRangeStartList;
	}

	/**
	 * @return the dateRangeEndList
	 */
	public List<String> getDateRangeEndList() {
		return dateRangeEndList;
	}

	/**
	 * @param dateRangeEndList
	 *            the dateRangeEndList to set
	 */
	public void setDateRangeEndList(List<String> dateRangeEndList) {
		this.dateRangeEndList = dateRangeEndList;
	}

	/**
	 * @return the dateAmountList
	 */
	public List<String> getDateAmountList() {
		return dateAmountList;
	}

	/**
	 * @param dateAmountList
	 *            the dateAmountList to set
	 */
	public void setDateAmountList(List<String> dateAmountList) {
		this.dateAmountList = dateAmountList;
	}

}
