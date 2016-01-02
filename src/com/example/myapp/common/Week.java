/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.common;

import java.util.Calendar;
import java.util.Date;

/**
 * 获取一周开始和结尾的类
 * 
 * @author Engle
 */
public class Week {

	/** 当前日期 */
	private Date currentDate;

	/** 本周的开始时间 */
	private Date startWeekDate;

	/** 本周的结束时间 */
	private Date endWeekDate;

	/** 传入一个date作为构造函数的参数, 直接计算出本周的开始时间和结束时间 */
	public Week(Date d) {
		this.currentDate = d;
		Calendar c = Calendar.getInstance();
		c.setTime(this.currentDate);
		int diffOfDay = -(c.get(Calendar.DAY_OF_WEEK) - 2);
		if (diffOfDay == 1) {
			diffOfDay -= 7;
		}
		c.add(Calendar.DAY_OF_WEEK, diffOfDay);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		this.startWeekDate = c.getTime();

		c.setTime(this.startWeekDate);
		c.add(Calendar.DAY_OF_WEEK, 6);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH), 23, 59, 59);

		this.endWeekDate = c.getTime();
	}

	public Date getCurrentDate() {
		return this.currentDate;
	}

	public Date getStartWeekDate() {
		return this.startWeekDate;
	}

	public Date getEndWeekDate() {
		return this.endWeekDate;
	}
}
