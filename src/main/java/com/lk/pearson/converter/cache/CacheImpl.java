package com.lk.pearson.converter.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CacheImpl implements Cache {

	private Map<String, Object> localCache = Collections.synchronizedMap(new HashMap<String, Object>());
	
	public CacheImpl() {
		localCache = Collections.synchronizedMap(new HashMap<String, Object>());
	}
	public void putInCache(String key, Object value) {
		localCache.put(key, value);

	}
	public Object getFromCache(String key) {
		Object cachedObject = localCache.get(key);
		return cachedObject;
	}
	public void deleteFromCache(String key) {
		localCache.remove(key);
	}

}
