package com.lk.pearson.converter.cache;

public interface Cache {
	
	public void putInCache(String key , Object value);
	public Object getFromCache(String key);
	public void deleteFromCache(String key);

}
