package com.lk.pearson.converter.core;

import com.lk.pearson.converter.constants.Outputs;
import com.lk.pearson.converter.exceptions.CoreConversionFailedException;
import com.lk.pearson.converter.exceptions.InvalidConversionStrategyException;
import com.lk.pearson.domain.Input;
import com.lk.pearson.domain.Output;

public class ConversionManager {

	public Output convert(Input input, Outputs desiredOutputFormat)
			throws CoreConversionFailedException, InvalidConversionStrategyException {

		ConversionStrategy conversionStrategy = getApplicableConversionStrategy(desiredOutputFormat);
		ConversionContext conversionContext = new ConversionContext(conversionStrategy);
		Output convertedOutput = conversionContext.executeConversionStrategy(input);
		return convertedOutput;

	}

	private ConversionStrategy getApplicableConversionStrategy(Outputs desiredOutputFormat) {
		ConversionStrategy conversionStrategy = null;
		if (desiredOutputFormat.equals(Outputs.DOCX)) {
			conversionStrategy = new HtmlToWordConverterStrategy();
		} else if (desiredOutputFormat.equals(Outputs.HTML)) {
			// NOT IMPLEMENTED YET
		} else if (desiredOutputFormat.equals(Outputs.PDF)) {
			conversionStrategy = new PdfToWordConverterStrategy();
		} else if (desiredOutputFormat.equals(Outputs.PLAIN_TEXT)) {
			// NOT IMPLEMENTED YET
		} else if (desiredOutputFormat.equals(Outputs.XLS)) {
			conversionStrategy = new SpreadSheetToWordConverterStrategy();
		}
		return conversionStrategy;
	}

}
