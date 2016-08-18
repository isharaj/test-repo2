package com.lk.pearson.converter.cache;

public class CacheManager {
	private static Cache localCache = new CacheImpl();
	
	public static Cache getInstance() {
		return localCache;
	}

}
