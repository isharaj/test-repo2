package com.lk.pearson.converter.core;

import com.lk.pearson.converter.exceptions.CoreConversionFailedException;
import com.lk.pearson.domain.Input;
import com.lk.pearson.domain.Output;

public interface ConversionStrategy {
	
	public  Output convert(Input input) throws CoreConversionFailedException;

}
