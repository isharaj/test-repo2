package com.lk.pearson.converter.concurrent.workers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lk.pearson.converter.constants.LifeCycle;
import com.lk.pearson.converter.constants.Outputs;
import com.lk.pearson.converter.core.ConversionManager;
import com.lk.pearson.converter.exceptions.CoreConversionFailedException;
import com.lk.pearson.converter.exceptions.InvalidConversionStrategyException;
import com.lk.pearson.domain.Input;
import com.lk.pearson.domain.Output;
import com.lk.pearson.queue.QueueManager;

public class OutgoingMessageWorker implements Runnable{
	
	private Input input;
	ConversionManager conversionManager;
	QueueManager queueManager;
	Outputs desiredOutputFormat;
	
	public OutgoingMessageWorker(Input input , Outputs desiredOutputFormat){
		this.input = input;
		conversionManager = new ConversionManager();
		queueManager = new QueueManager();
		this.desiredOutputFormat = desiredOutputFormat;
		List<Input> inputList = new ArrayList<Input>();
		inputList.add(input);
		changeStateInAllMessages(inputList);
	}

	public void run() {
		performConversion();

	}
	
	private void performConversion(){
		try {
			Output output = conversionManager.convert(input, desiredOutputFormat);
			output.setLifeCycle(LifeCycle.PROCESSED);
			queueManager.addElementToOutputQueue(output , false);
			System.out.println("QUEUE depth:"+queueManager.getAllElementsFromOutputQueue(false, input.getToken()).size());
		} catch (CoreConversionFailedException e) {
			e.printStackTrace();
			// Notify performance monitor of conversion failure here
		} catch (InvalidConversionStrategyException e) {
			// Notify performance monitor of conversion failure here
			e.printStackTrace();
		} catch (Exception exe){
			exe.printStackTrace();
			// Notify performance monitor of conversion failure here
		}
	}
	
	private void changeStateInAllMessages(List<Input> newInputMessageList) {
		Iterator<Input> newInputMessageIterator = newInputMessageList.iterator();
		while (newInputMessageIterator.hasNext()) {
			Input newInputMessage = newInputMessageIterator.next();
			newInputMessage.setLifeCycle(LifeCycle.PROCESSING);
		}
	}

}
