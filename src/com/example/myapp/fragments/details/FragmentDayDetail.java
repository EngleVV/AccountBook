/*
 * FragmentDayDetail.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp.fragments.details;

import java.util.Calendar;

import com.example.myapp.common.util.CalendarUtils;
import com.example.myapp.db.SqlQuery;
import com.example.myapp.fragments.parents.FragmentItemDetail;

/**
 * @author Engle
 * 
 */
public class FragmentDayDetail extends FragmentItemDetail {

	/*
	 * @see com.example.myapp.fragments.FragmentItemDetail#getSqlQuery()
	 */
	@Override
	protected SqlQuery getSqlQuery() {
		Calendar calendar = Calendar.getInstance();
		String strToday = CalendarUtils.toStandardDateString(calendar);
		String sqlString = "select uuid,amount,type,date,accountType,lastModifyDate from detail_record where date like ? order by date desc";
		String sqlConditions[] = new String[] { strToday.substring(0, 10) + "%" };
		return new SqlQuery(sqlString, sqlConditions);
	}

}
