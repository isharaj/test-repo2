package com.lk.pearson.converter.core;

import com.lk.pearson.converter.exceptions.CoreConversionFailedException;
import com.lk.pearson.domain.Input;
import com.lk.pearson.domain.Output;

public class ConversionContext {

	private ConversionStrategy conversionStrategy;

	public ConversionContext(ConversionStrategy conversionStrategy) {
		this.conversionStrategy = conversionStrategy;
	}

	public Output executeConversionStrategy(Input input) throws CoreConversionFailedException {
		Output output = conversionStrategy.convert(input);
		return output;
	}

}
