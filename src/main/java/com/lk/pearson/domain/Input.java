package com.lk.pearson.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.lk.pearson.converter.constants.Inputs;
import com.lk.pearson.converter.constants.LifeCycle;

public class Input {
	
	private String inputAsString;
	private Inputs inputs;
	private byte[] inputasByteArray;
	private File inputAsFile;
	private FileInputStream inputAsFileInputStream;
	private InputStream inputAsInputStream;
	private String inputFileName;
	private Inputs inputFormat;
	private String token;
	private List<String> packagingLevelDetails;
	private LifeCycle lifeCycle;
	
	public String getInputAsString() {
		return inputAsString;
	}
	public void setInputAsString(String inputAsString) {
		this.inputAsString = inputAsString;
	}
	public byte[] getInputasByteArray() {
		return inputasByteArray;
	}
	public void setInputasByteArray(byte[] inputasByteArray) {
		this.inputasByteArray = inputasByteArray;
	}
	public File getInputAsFile() {
		return inputAsFile;
	}
	public void setInputAsFile(File inputAsFile) {
		this.inputAsFile = inputAsFile;
	}
	public FileInputStream getInputAsFileInputStream() {
		return inputAsFileInputStream;
	}
	public void setInputAsFileInputStream(FileInputStream inputAsFileInputStream) {
		this.inputAsFileInputStream = inputAsFileInputStream;
	}
	public InputStream getInputAsInputStream() {
		return inputAsInputStream;
	}
	public void setInputAsInputStream(InputStream inputAsInputStream) {
		this.inputAsInputStream = inputAsInputStream;
	}
	public String getInputFileName() {
		return inputFileName;
	}
	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}
	public Inputs getInputFormat() {
		return inputFormat;
	}
	public void setInputFormat(Inputs inputFormat) {
		this.inputFormat = inputFormat;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Inputs getInputs() {
		return inputs;
	}
	public void setInputs(Inputs inputs) {
		this.inputs = inputs;
	}
	public List<String> getPackagingLevelDetails() {
		return packagingLevelDetails;
	}
	public void setPackagingLevelDetails(List<String> packagingLevelDetails) {
		this.packagingLevelDetails = packagingLevelDetails;
	}
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(LifeCycle lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	
	
	
}