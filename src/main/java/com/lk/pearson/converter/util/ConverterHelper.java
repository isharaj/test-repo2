package com.lk.pearson.converter.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.lk.pearson.converter.cache.Cache;
import com.lk.pearson.converter.cache.CacheManager;
import com.lk.pearson.converter.constants.Outputs;
import com.lk.pearson.domain.Output;
import com.lk.pearson.domain.QueueSignature;
import com.lk.pearson.queue.QueueManager;
import com.lk.pearson.queue.QueueManagerUtil;

public class ConverterHelper {
	private static final String filePathSeparator = "\\";
	private static final String incomingMessageRegisterSign = "-IR";
	QueueManager queueManager = new QueueManager();
	
	
	
	public Output createOutputFromStream(byte[] output, String fileName , String tokenId , Outputs format) {
		Output outputObject = new Output();
		outputObject.setOutputasByteArray(output);
		outputObject.setOutputFileName(fileName);
		outputObject.setToken(tokenId);
		outputObject.setOutputFormat(format);
		return outputObject;

	}
	
	public void cleanupCacheAfterConversion(String tokenId) {
		Cache cache = CacheManager.getInstance();
		QueueManagerUtil<QueueSignature> queueManagerUtil = new QueueManagerUtil<QueueSignature>();
		QueueSignature incomingQueueSignature = queueManagerUtil.getInputQueueSignature();
		QueueSignature outgoingQueueSignature = queueManagerUtil.getOutputQueueSignature();
		String incomingMessageCountSign = getIncomingMessageSign(tokenId);
		cache.deleteFromCache(incomingQueueSignature.toString());
		cache.deleteFromCache(outgoingQueueSignature.toString());
		cache.deleteFromCache(incomingMessageCountSign);
	}
	
	public String getIncomingMessageSign(String tokenId) {
		String signature = tokenId + incomingMessageRegisterSign;
		return signature;
	}

	public void registerIncomingMessage(String tokenId) {
		String incomingMessageCountSign = getIncomingMessageSign(tokenId);
		Cache cache = CacheManager.getInstance();
		Integer incomintMessageCount = (Integer) cache.getFromCache(incomingMessageCountSign);
		if (incomintMessageCount == null) {
			cache.putInCache(incomingMessageCountSign, 1);
		} else {
			int newCount = incomintMessageCount + 1;
			cache.putInCache(incomingMessageCountSign, newCount);
		}
	}

	public int getRegisteredIncomingMessages(String tokenId) {
		Cache cache = CacheManager.getInstance();
		String incomingMessageCountSign = getIncomingMessageSign(tokenId);
		Integer incomintMessageCount = (Integer) cache.getFromCache(incomingMessageCountSign);
		return incomintMessageCount;
	}
	public void makeDir(String parentPath , String dirName){
		new File(parentPath+filePathSeparator+dirName).mkdir();
	}

	public byte[] getFileAsByteArray(File file) throws IOException {

		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];

		for (int readNum; (readNum = fis.read(buf)) != -1;) {
			bos.write(buf, 0, readNum); // no doubt here is 0
		}
		byte[] bytes = bos.toByteArray();
		fis.close();

		return bytes;
	}

	public String createTempLocationInUserDirectory(String dirName) {
		boolean success = new java.io.File(System.getProperty("user.home"), dirName).mkdirs();
		String path = System.getProperty("user.home") + "\\" + dirName;
		System.out.println(" Creating new directory " + path + " is successful:" + success);
		return path;
	}

}
