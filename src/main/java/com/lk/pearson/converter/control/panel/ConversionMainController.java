package com.lk.pearson.converter.control.panel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lk.pearson.converter.concurrent.workers.IncomingMessageHandler;
import com.lk.pearson.converter.concurrent.workers.OutgoingMessageHandler;
import com.lk.pearson.converter.constants.Outputs;
import com.lk.pearson.converter.exceptions.CoreConversionFailedException;
import com.lk.pearson.converter.util.ConverterBuilder;
import com.lk.pearson.converter.util.ConverterHelper;
import com.lk.pearson.domain.Input;
import com.lk.pearson.queue.QueueManager;

public class ConversionMainController {
	
	private ConverterHelper converterHelper;
	private ConverterBuilder converterBuilder;
	private QueueManager queueManager;
	private IncomingMessageHandler incomingMessageHandler;
	private OutgoingMessageHandler outgoingMessageHandler;
	private static String stroageLocationPath = new ConverterHelper().createTempLocationInUserDirectory("zipfile");
	
	public ConversionMainController(){
		converterHelper = new ConverterHelper();
		converterBuilder = new ConverterBuilder();
		queueManager = new QueueManager();
		queueManager.startAllQueues();
	}
	
	public void acceptInput(String htmlContent, String fileName, String tokenId, Outputs desiredOutput) {
		Input input = converterBuilder.buildHtmlInputFromString(htmlContent, fileName, tokenId);
		incomingMessageHandler = new IncomingMessageHandler();
		incomingMessageHandler.processInputMessage(input, desiredOutput);
	}

	public byte[] endConversionSessionAndGetZipFile(String tokenId) throws CoreConversionFailedException {
		outgoingMessageHandler = new OutgoingMessageHandler();
		try {
			byte[] outputZipFile = outgoingMessageHandler.endConversionSessionAndGetOutputZip(tokenId);
			converterHelper.cleanupCacheAfterConversion(tokenId);
			return outputZipFile;
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new CoreConversionFailedException(e.getMessage());
		} catch (IOException e) {
			throw new CoreConversionFailedException(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		String html = "<html><head><title>Import me</title></head><body><p>Hello World!</p></body></html>";
		String html2 = "<html><head><title>Import me</title></head><body><p>Hello World22222!</p></body></html>";
		
		ConversionMainController conversionMainController = new ConversionMainController();
		conversionMainController.acceptInput(html, "test1", "1", Outputs.DOCX);
		System.out.println("GAVE");
		conversionMainController.acceptInput(html, "test2", "1", Outputs.DOCX);
		System.out.println("GAVE");
		conversionMainController.acceptInput(html, "test3", "1", Outputs.DOCX);
		System.out.println("GAVE");
		conversionMainController.acceptInput(html, "test4", "1", Outputs.DOCX);
		System.out.println("GAVE");
		conversionMainController.acceptInput(html2, "test5", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test6", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test7", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test8", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test9", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test10", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test11", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test12", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test13", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test14", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test15", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test16", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test17", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test18", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test19", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test20", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test21", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test22", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test23", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test24", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test25", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test26", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test27", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test28", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test29", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test30", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test31", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test32", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test33", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test34", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test35", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test36", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test37", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test38", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test39", "1", Outputs.DOCX);
		conversionMainController.acceptInput(html2, "test40", "1", Outputs.DOCX);
		try {
			byte[] zipFile = conversionMainController.endConversionSessionAndGetZipFile("1");
			writeByteArrayToFile(stroageLocationPath+"\\1.zip", zipFile);
			System.out.println("================== COMPLETE ========================");
		} catch (CoreConversionFailedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void writeByteArrayToFile(String filePath, byte[] byteContent) {
		try {
			FileOutputStream fos = new FileOutputStream(filePath);

			fos.write(byteContent);
			fos.close();

		} catch (FileNotFoundException ex) {
			System.out.println("FileNotFoundException : " + ex);
		} catch (IOException ioe) {
			System.out.println("IOException : " + ioe);
		}

	}

}
