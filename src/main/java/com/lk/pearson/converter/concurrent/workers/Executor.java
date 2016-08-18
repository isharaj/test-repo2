package com.lk.pearson.converter.concurrent.workers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lk.pearson.converter.constants.ConstantRepository;
import com.lk.pearson.converter.constants.ExecutorType;

public class Executor {

	private static ExecutorService inputExecutor = Executors
			.newFixedThreadPool(ConstantRepository.numberOfInputThreads);
	private static ExecutorService outputExecutor = Executors
			.newFixedThreadPool(ConstantRepository.numberOfOutputThreads);

	public static void execute(Runnable incomingMessageWorker, ExecutorType executorType) {
		if (ExecutorType.INPUT.equals(executorType)) {
			inputExecutor.execute(incomingMessageWorker);
		} else if (ExecutorType.OUTPUT.equals(executorType)) {
			outputExecutor.execute(incomingMessageWorker);
		}

	}

	public static void shutDownInputExecutor() {
		inputExecutor.shutdown();
		while (!inputExecutor.isTerminated()) {
		}
	}

	public static void shutDownOutputExecutor() {
		outputExecutor.shutdown();
		while (!outputExecutor.isTerminated()) {
		}
	}

}
