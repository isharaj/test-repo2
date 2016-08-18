package com.lk.pearson.converter.concurrent.workers;

import java.util.Iterator;
import java.util.List;

import com.lk.pearson.converter.constants.LifeCycle;
import com.lk.pearson.converter.constants.Outputs;
import com.lk.pearson.converter.util.ConverterHelper;
import com.lk.pearson.domain.Input;
import com.lk.pearson.domain.Output;
import com.lk.pearson.queue.QueueManager;

public class IncomingMessageWorker implements Runnable {

	QueueManager queueManager;
	Input incomingMessage;
	ConverterHelper converterHelper = new ConverterHelper();
	Outputs desiredOutputFormat;

	public IncomingMessageWorker(Input incomingMessage , Outputs desiredOutputFormat) {
		queueManager = new QueueManager();
		Output skeletonOutput = new Output();
		skeletonOutput.setToken(incomingMessage.getToken());
		queueManager.addElementToOutputQueue(skeletonOutput, true);
		this.incomingMessage = incomingMessage;
		this.desiredOutputFormat = desiredOutputFormat;
		addIncomingMessageToQueue(incomingMessage);
		converterHelper.registerIncomingMessage(incomingMessage.getToken());
		List<Input> newInputList = queueManager.getAllElementsFromInputQueue(false , LifeCycle.NEW);
		changeStateInAllMessages(newInputList);
	}

	public void run() {
		
		OutgoingMessageHandler outgoingMessageHandler = new OutgoingMessageHandler();
		outgoingMessageHandler.notifyIncomingMessage(incomingMessage , desiredOutputFormat);

	}

	private void addIncomingMessageToQueue(Input incomingMessage) {
		incomingMessage.setLifeCycle(LifeCycle.NEW);
		queueManager.addElementToInputQueue(incomingMessage);
		
	}
	
	private void changeStateInAllMessages(List<Input> newInputMessageList) {
		Iterator<Input> newInputMessageIterator = newInputMessageList.iterator();
		while (newInputMessageIterator.hasNext()) {
			Input newInputMessage = newInputMessageIterator.next();
			newInputMessage.setLifeCycle(LifeCycle.PROCESSING);
		}
	}

}
