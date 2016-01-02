/*
 * FragmentWeekDetail.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.myapp.common.Week;
import com.example.myapp.db.SqlQuery;

/**
 * 周明细页面
 * 
 * @author Engle
 * 
 */
public class FragmentWeekDetail extends FragmentItemDetail {

	/*
	 * @see com.example.myapp.fragments.FragmentItemDetail#getSqlQuery()
	 */
	@Override
	protected SqlQuery getSqlQuery() {
		Calendar calendar = Calendar.getInstance();
		Week week = new Week(calendar.getTime());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		String sqlString = "select amount,type,date,accountType from detail_record where date >= ? and date <= ?";
		String sqlConditions[] = new String[] {
				simpleDateFormat.format(week.getStartWeekDate()),
				simpleDateFormat.format(week.getEndWeekDate()) };
		return new SqlQuery(sqlString, sqlConditions);
	}

}
