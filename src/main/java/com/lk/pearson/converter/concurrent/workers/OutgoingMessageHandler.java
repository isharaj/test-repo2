package com.lk.pearson.converter.concurrent.workers;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.lk.pearson.converter.constants.ConstantRepository;
import com.lk.pearson.converter.constants.ExecutorType;
import com.lk.pearson.converter.constants.LifeCycle;
import com.lk.pearson.converter.constants.Outputs;
import com.lk.pearson.converter.util.ConverterHelper;
import com.lk.pearson.domain.Input;
import com.lk.pearson.domain.Output;
import com.lk.pearson.packaging.service.PackagingManager;
import com.lk.pearson.queue.QueueManager;

public class OutgoingMessageHandler {
	QueueManager queueManager;
	ConverterHelper converterUtil;
	PackagingManager packagingManager;
	
	
	public OutgoingMessageHandler(){
		queueManager = new QueueManager();
		converterUtil = new ConverterHelper();
		packagingManager = new PackagingManager();
	}
	
	public byte[] endConversionSessionAndGetOutputZip(String tokenId) throws InterruptedException, IOException {

		int messagesReceivedForProcessingCount = converterUtil.getRegisteredIncomingMessages(tokenId);
		long cycleWaitingTime = ConstantRepository.generateZipwaitingTime;
		long totalTimeout = ConstantRepository.generateZipTimeout;
		long cycles = totalTimeout / cycleWaitingTime;

		for (int count = 0; count < cycles; count++) {
			List<Output> processedMessageList = queueManager.getAllElementsFromOutputQueue(false, tokenId);
			int processedMessageCount = processedMessageList.size();
            System.out.println(" ------- FILES PROCESSED -------:"+processedMessageCount);
			if (processedMessageCount == messagesReceivedForProcessingCount) {
				// All messages successfully processed. No need to wait further
				break;
			}
			Thread.sleep(cycleWaitingTime);
		}
		List<Output> finalProcessedMessageList = queueManager.getAllElementsFromOutputQueue(false, tokenId);
		byte[] finalZipFile = packagingManager.createZipFile(finalProcessedMessageList, tokenId);
		return finalZipFile;
	}

	public void notifyIncomingMessage(Input processingCandidate, Outputs outputFormat) {
		Runnable outgoingMessageWorker = new OutgoingMessageWorker(processingCandidate, outputFormat);
		Executor.execute(outgoingMessageWorker, ExecutorType.OUTPUT);

	}



}
