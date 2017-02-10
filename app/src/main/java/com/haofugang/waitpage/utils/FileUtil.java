package com.haofugang.waitpage.utils;

import java.io.File;

/**
 * 文件操作工具包
 * 
 * @author  hfg
 * @created 2016-6-17
 */
public class FileUtil {

	/**
	 * 通过绝对路径判断文件是不是存在
	 * @return
     */
	public static boolean fileIsExists(String PathString){
		try{
			File f=new File(PathString);
			if(!f.exists()){
				return false;
			}
		}catch (Exception e) {
			return false;
		}
		return true;
	}
}