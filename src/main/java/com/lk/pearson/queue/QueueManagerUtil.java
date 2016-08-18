package com.lk.pearson.queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.lk.pearson.converter.cache.Cache;
import com.lk.pearson.converter.cache.CacheManager;
import com.lk.pearson.converter.constants.ConstantRepository;
import com.lk.pearson.converter.util.ConverterHelper;
import com.lk.pearson.domain.QueueSignature;

public class QueueManagerUtil<E> {
	
	private ConverterHelper converterUtil;
	
	public QueueManagerUtil(){
		converterUtil = new ConverterHelper();
	}
	
	public Queue<E> getNewQueueInstance(){
		BlockingQueue<E> newQueue = new LinkedBlockingQueue<E>();
		return newQueue;
	}

	public Queue<E> getInputQueue() {
		QueueSignature inputQueueSignature = getInputQueueSignature();
		Queue<E> inputQueue = getQueueFromCache(inputQueueSignature);
		return inputQueue;
	}
	
	public Queue<E> getOutputQueue() {
		QueueSignature outputQueueSignature = getOutputQueueSignature();
		Queue<E> inputQueue = getQueueFromCache(outputQueueSignature);
		return inputQueue;
	}
	
	public void addQueueToLocalCache(QueueSignature queueSignature , Queue<E> queue){
		Cache cache = CacheManager.getInstance();
		String queueSignString = queueSignature.toString();
		cache.putInCache(queueSignString, queue);
	}
	
	public Queue<E> getQueueFromCache(QueueSignature queueSignature) {
		Cache cache = CacheManager.getInstance();
		String queueString = queueSignature.toString();
		Queue<E> queueFromCache = (Queue<E>) cache.getFromCache(queueString);
		return queueFromCache;
	}
	
	public QueueSignature getInputQueueSignature() {
		QueueSignature queueSignature = new QueueSignature();
		queueSignature.setQueueName(ConstantRepository.INPUT_QUEUE);
		return queueSignature;
	}

	public QueueSignature getOutputQueueSignature() {
		QueueSignature queueSignature = new QueueSignature();
		queueSignature.setQueueName(ConstantRepository.OUTPUT_QUEUE);
		return queueSignature;
	}

}
