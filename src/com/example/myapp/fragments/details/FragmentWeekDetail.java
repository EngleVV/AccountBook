/*
 * FragmentWeekDetail.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp.fragments.details;

import java.util.Calendar;

import com.example.myapp.GlobleData;
import com.example.myapp.common.Week;
import com.example.myapp.common.util.CalendarUtils;
import com.example.myapp.db.SqlQuery;
import com.example.myapp.fragments.parents.FragmentItemDetail;

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
		String strUsername = ((GlobleData) getActivity().getApplication())
				.getUsername();
		strUsername = (null == strUsername) ? "" : strUsername;
		Week week = new Week(calendar.getTime());
		String sqlString = "select uuid,amount,type,date,accountType,lastModifyDate from detail_record where date >= ? and date <= ? and user = ? order by date desc";
		String sqlConditions[] = new String[] {
				CalendarUtils.toStandardDateString(week.getStartWeekDate()),
				CalendarUtils.toStandardDateString(week.getEndWeekDate()),
				strUsername };
		return new SqlQuery(sqlString, sqlConditions);
	}

}
