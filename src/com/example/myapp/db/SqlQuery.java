/*
 * SqlQuery.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp.db;

/**
 * @author Engle
 * 
 */
public class SqlQuery {

	/** 数据库查询语句 */
	String sqlString;

	/** 数据库查询条件 */
	String sqlConditions[];

	/**
	 * @param sqlString
	 *            查询语句
	 * @param sqlCondition
	 *            查询条件
	 */
	public SqlQuery(String sqlString, String[] sqlConditions) {
		super();
		this.sqlString = sqlString;
		this.sqlConditions = sqlConditions;
	}

	/**
	 * @return the sqlString
	 */
	public String getSqlString() {
		return sqlString;
	}

	/**
	 * @param sqlString
	 *            the sqlString to set
	 */
	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}

	/**
	 * @return the sqlCondition
	 */
	public String[] getSqlConditions() {
		return sqlConditions;
	}

	/**
	 * @param sqlCondition
	 *            the sqlCondition to set
	 */
	public void setSqlConditions(String[] sqlConditions) {
		this.sqlConditions = sqlConditions;
	}

}
