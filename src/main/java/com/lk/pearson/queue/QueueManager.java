package com.lk.pearson.queue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import com.lk.pearson.converter.constants.LifeCycle;
import com.lk.pearson.domain.ConversionJobOutput;
import com.lk.pearson.domain.Input;
import com.lk.pearson.domain.Output;
import com.lk.pearson.domain.QueueSignature;

public class QueueManager {

	private static boolean inputQueueInitialized = false;
	private static boolean outputQueueInitialized = false;
	private static Queue<Input> inputQueue;
	private static Queue<ConversionJobOutput> outputQueue;

	public void startAllQueues() {

		if (!inputQueueInitialized) {
			QueueManagerUtil<Input> queueManagerUtil = new QueueManagerUtil<Input>();
			inputQueue = queueManagerUtil.getNewQueueInstance();
			inputQueueInitialized = true;
			QueueSignature inputQueueSignature = queueManagerUtil.getInputQueueSignature();
			queueManagerUtil.addQueueToLocalCache(inputQueueSignature, inputQueue);
		}
		if (!outputQueueInitialized) {
			QueueManagerUtil<ConversionJobOutput> queueManagerUtil = new QueueManagerUtil<ConversionJobOutput>();
			outputQueue = queueManagerUtil.getNewQueueInstance();
			outputQueueInitialized = true;
			QueueSignature outputQueueSignature = queueManagerUtil.getOutputQueueSignature();
			queueManagerUtil.addQueueToLocalCache(outputQueueSignature, outputQueue);
		}

	}

	public void addElementToInputQueue(Input input) {
		QueueManagerUtil<Input> queueManagerUtil = new QueueManagerUtil<Input>();
		Queue<Input> inputQueue = queueManagerUtil.getInputQueue();
		inputQueue.offer(input);
		queueManagerUtil.addQueueToLocalCache(queueManagerUtil.getInputQueueSignature(), inputQueue);
	}

	public void addConversionJobToOutputQueue(ConversionJobOutput conversionJobOutput) {
		QueueManagerUtil<ConversionJobOutput> queueManagerUtil = new QueueManagerUtil<ConversionJobOutput>();
		Queue<ConversionJobOutput> outputQueue = queueManagerUtil.getOutputQueue();
		outputQueue.offer(conversionJobOutput);
		queueManagerUtil.addQueueToLocalCache(queueManagerUtil.getOutputQueueSignature(), outputQueue);
	}

	public void addElementToOutputQueue(Output output, boolean prepareSkeletonOutputQueue) {
		QueueManagerUtil<ConversionJobOutput> queueManagerUtil = new QueueManagerUtil<ConversionJobOutput>();
		Queue<ConversionJobOutput> conversionJobOutputQueue = queueManagerUtil.getOutputQueue();
		ConversionJobOutput conversionJobSearchCandidate = new ConversionJobOutput();
		conversionJobSearchCandidate.setTokenId(output.getToken());
		boolean found = false;

		Iterator<ConversionJobOutput> conversionJobIterator = conversionJobOutputQueue.iterator();
		while (conversionJobIterator.hasNext()) {
			ConversionJobOutput conversionJobOutput = conversionJobIterator.next();
			if (conversionJobOutput.equals(conversionJobSearchCandidate)) {
				found = true;
				if (!prepareSkeletonOutputQueue) {
					conversionJobOutput.getOutputList().add(output);
//					queueManagerUtil.addQueueToLocalCache(queueManagerUtil.getOutputQueueSignature(),
//							conversionJobOutputQueue);
				}
				break;
			}
		}
		if (!found && prepareSkeletonOutputQueue) {
			ConversionJobOutput newConversionJobOutput = new ConversionJobOutput();
			newConversionJobOutput.setTokenId(output.getToken());
			List<Output> newOutputList = new ArrayList<Output>();
			newConversionJobOutput.setOutputList(newOutputList);
			conversionJobOutputQueue.offer(newConversionJobOutput);
			queueManagerUtil.addQueueToLocalCache(queueManagerUtil.getOutputQueueSignature(), conversionJobOutputQueue);
		}

		
		//System.out.println("===========::::::::::::::::::::::================:"+queueManagerUtil.getOutputQueue().size());
	}

	public List<Input>  getAllElementsFromInputQueue(boolean removeElementsFromParent , LifeCycle lifeCycleStatus) {
		QueueManagerUtil<Input> queueManagerUtil = new QueueManagerUtil<Input>();
		Queue<Input> inputQueue = queueManagerUtil.getInputQueue();
		List<Input> inputList = new ArrayList<Input>();

		Iterator<Input> inputQueueIterator = inputQueue.iterator();
		while (inputQueueIterator.hasNext()) {
			Input input = inputQueueIterator.next();
			if (lifeCycleStatus.equals(input.getLifeCycle())) {
				inputList.add(input);
				if (removeElementsFromParent) {
					inputQueueIterator.remove();
				}
			}

		}
		return inputList;
	}
	
	public List<Output> getAllElementsFromOutputQueue(boolean removeElementsFromParent, String tokenId) {
		QueueManagerUtil<ConversionJobOutput> queueManagerUtil = new QueueManagerUtil<ConversionJobOutput>();
		Queue<ConversionJobOutput> outputQueue = queueManagerUtil.getOutputQueue();
		Iterator<ConversionJobOutput> conversionJobOutputIterator = outputQueue.iterator();
		ConversionJobOutput conversionJobOutputSearchCandidate = new ConversionJobOutput();
		ConversionJobOutput conversionJobOutputResult = new ConversionJobOutput();
		conversionJobOutputSearchCandidate.setTokenId(tokenId);

		while (conversionJobOutputIterator.hasNext()) {
			ConversionJobOutput conversionJobOutput = conversionJobOutputIterator.next();
			if (conversionJobOutput.equals(conversionJobOutputSearchCandidate)) {
				conversionJobOutputResult = conversionJobOutput;
				if (removeElementsFromParent) {
					conversionJobOutputIterator.remove();
				}
			}
		}
		List<Output> outputList = conversionJobOutputResult.getOutputList();
		return outputList;
	}
}
