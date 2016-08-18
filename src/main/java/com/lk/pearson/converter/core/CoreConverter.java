package com.lk.pearson.converter.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.contenttype.ContentType;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.AlternativeFormatInputPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.CTAltChunk;

import com.lk.pearson.converter.exceptions.CoreConversionFailedException;
import com.lk.pearson.converter.util.ConverterHelper;
import com.lk.pearson.input.format.filters.HtmlFilters;

public class CoreConverter {
	
	private static final String tempFileStorageLocationName = "TempHtmlContainer";
	private static final String filePathSeparator = "\\";
	
	
	public byte[] convertHtmlToDoc(String html, String docTitle) throws CoreConversionFailedException {
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
			
			return binaryOutput;
		}catch (Exception e) {
			throw new CoreConversionFailedException(e.getMessage());
		}
	}
	
	public static void main(String[] args) throws CoreConversionFailedException {
		String html = "<html><head><title>Import me</title></head><body><p>Hello World!</p></body></html>";
		String content = "";
		try {
			BufferedReader in = new BufferedReader(
					new FileReader("C:\\Users\\UPERESR\\Desktop\\html\\ge_aphd_01_03_intro.html"));
			String str;
			while ((str = in.readLine()) != null) {
				content += str;
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		html = content;

		CoreConverter coreConverter = new CoreConverter();
		byte[] binaryOutput = coreConverter.convertHtmlToDoc(html, "NewDocument");
		System.out.println("Conversion Complete:" + binaryOutput.length);

	}

}
