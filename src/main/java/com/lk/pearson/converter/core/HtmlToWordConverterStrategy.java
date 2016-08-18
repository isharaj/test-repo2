package com.lk.pearson.converter.core;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.contenttype.ContentType;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.AlternativeFormatInputPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.CTAltChunk;

import com.lk.pearson.converter.constants.Outputs;
import com.lk.pearson.converter.exceptions.CoreConversionFailedException;
import com.lk.pearson.converter.util.ConverterHelper;
import com.lk.pearson.domain.Input;
import com.lk.pearson.domain.Output;
import com.lk.pearson.input.format.filters.HtmlFilters;

public class HtmlToWordConverterStrategy implements ConversionStrategy {
	
	private static final String tempFileStorageLocationName = "TempHtmlContainer";
	private static final String filePathSeparator = "\\";
	private static Outputs outputFormat;

	public Output convert(Input input) throws CoreConversionFailedException {
		ConverterHelper converterUtil = new ConverterHelper();
		String inputFileName = input.getInputFileName();
		String inputHtml = input.getInputAsString();
		byte[] outpuDoc = convertHtmlToDoc(inputHtml, inputFileName);
		Output outputObject = converterUtil.createOutputFromStream(outpuDoc, input.getInputFileName() , input.getToken() , outputFormat.DOCX);
		return outputObject;

	}
	
	public byte[] convertHtmlToDoc(String html, String docTitle) throws CoreConversionFailedException {
		long startTime = new Date().getTime();
		HtmlFilters htmlModifier = new HtmlFilters();
		WordprocessingMLPackage wordMLPackage;
		String stroageLocationPath = "";
		ConverterHelper converterUtil = new ConverterHelper();
		html = htmlModifier.removeHyperLinksFromHtmlString(html);
		
		try {
			stroageLocationPath = converterUtil.createTempLocationInUserDirectory(tempFileStorageLocationName);
			wordMLPackage = WordprocessingMLPackage.createPackage();
			

			AlternativeFormatInputPart afiPart = new AlternativeFormatInputPart(new PartName("/hw.html"));
			afiPart.setBinaryData(html.getBytes());
			afiPart.setContentType(new ContentType("text/html"));
			Relationship altChunkRel = wordMLPackage.getMainDocumentPart().addTargetPart(afiPart);

			CTAltChunk ac = Context.getWmlObjectFactory().createCTAltChunk();
			ac.setId(altChunkRel.getId());
			wordMLPackage.getMainDocumentPart().addObject(ac);
			
			wordMLPackage.getContentTypeManager().addDefaultContentType("html", "text/html");
			wordMLPackage.save(new java.io.File(stroageLocationPath + filePathSeparator + docTitle.trim() + ".docx"));

			File generatedWordDoc = new File(stroageLocationPath + filePathSeparator + docTitle.trim() + ".docx");
			byte[] binaryOutput = null;
			try {
				binaryOutput = converterUtil.getFileAsByteArray(generatedWordDoc);
			} catch (IOException e) {
               throw new CoreConversionFailedException(e.getMessage());
			}
			long endTime = new Date().getTime();
			long timeSpent = (endTime - startTime);
			System.out.println("======= CONVERTED FILE: "+docTitle+" in "+timeSpent +" mseconds.");
			return binaryOutput;
		}catch (Exception e) {
			throw new CoreConversionFailedException(e.getMessage());
		}
	}

}
