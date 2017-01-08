package com.he.item;

public class MemCachedItem {

	public static String getMemCachedKey(String itemKey, String tag, String bizCode, boolean flag) {
		return itemKey + "-" + tag + "-" + bizCode;
	}
	
}
