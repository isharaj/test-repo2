package com.lk.pearson.converter.concurrent.workers;

import com.lk.pearson.converter.constants.ExecutorType;
import com.lk.pearson.converter.constants.Outputs;
import com.lk.pearson.converter.util.ConverterHelper;
import com.lk.pearson.domain.Input;

public class IncomingMessageHandler {
	ConverterHelper converterHelper = new ConverterHelper();

	public void processInputMessage(Input input, Outputs desiredOutputFormat) {
		Runnable incomingMessageWorker = new IncomingMessageWorker(input , desiredOutputFormat);
		Executor.execute(incomingMessageWorker, ExecutorType.INPUT);

	}

}
