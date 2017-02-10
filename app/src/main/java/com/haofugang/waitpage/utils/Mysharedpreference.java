package com.haofugang.waitpage.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 时间:2015年6月18日 10:29:36
 * 
 * @hfg 新建sharedpreference 并且实现数据录入和 定向查询
 * 
 */

public class Mysharedpreference {
	/**
	 * 创建并且写入数据,实现多个数据录入
	 * 
	 * @param context
	 *            上下文对象
	 * @param name_str
	 *            表名
	 * @param boby_str
	 *            存入的内容值
	 * @param variable
	 *            存入的key值
	 */
	public static void mysharedpreference(Context context, String name_str,
										  String[] boby_str, String[] variable) {
		SharedPreferences Startguide = context.getSharedPreferences(name_str,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = Startguide.edit();
		for (int i = 0; i < variable.length; i++) {
			editor.putString(variable[i], boby_str[i]);
		}
		editor.commit();
	}

	/**
	 * 实现单个数据录入
	 * 
	 * @param context
	 *            上下文对象
	 * @param name_str
	 *            表名
	 * @param boby_str
	 *            存入的内容值
	 * @param variable
	 *            存入的key值
	 */
	public static void mysharedpreference(Context context, String name_str,
										  String boby_str, String variable) {
		SharedPreferences Startguide = context.getSharedPreferences(name_str,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = Startguide.edit();
		editor.putString(variable, boby_str);
		editor.commit();
	}

	/**
	 * 查询首项是不是为空
	 * 
	 * @param context
	 *            上下文对象
	 * @param name_str
	 *            表名
	 * @param variable
	 *            存入的key值
	 *            
	 * @return 返回false为空 
	 *         返回true为非空
	 */
	public static boolean content_pankong(Context context, String name_str,
										  String variable) {
		SharedPreferences Startguide = context.getSharedPreferences(name_str,
				Activity.MODE_PRIVATE);
		if (Startguide.getString(variable, "").equals("")
				|| Startguide.getString(variable, "") == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取某个数据
	 * 
	 * @param context
	 *            上下文对象
	 * @param name_str
	 *            表名
	 * @param variable
	 *            存入的key值
	 * @return
	 */
	public static String getstring(Context context, String name_str,
								   String variable) {
		SharedPreferences Startguide = context.getSharedPreferences(name_str,
				Activity.MODE_PRIVATE);
		if ( null==Startguide.getString(variable, "")||Startguide.getString(variable, "").equals("")) {
			return "";
		} else {

			return Startguide.getString(variable, "");
		}

	}

}
