package com.lk.pearson.converter.util;

import com.lk.pearson.converter.constants.Inputs;
import com.lk.pearson.domain.Input;

public class ConverterBuilder {
	
	public Input buildHtmlInputFromString(String htmlContent, String fileName, String tokenId) {
		Input input = new Input();
		input.setInputAsString(htmlContent);
		input.setInputFormat(Inputs.HTML);
		input.setToken(tokenId);
		input.setInputFileName(fileName);

		return input;
	}
	
	
	

}
