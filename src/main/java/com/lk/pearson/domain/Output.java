package com.lk.pearson.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.lk.pearson.converter.constants.Inputs;
import com.lk.pearson.converter.constants.LifeCycle;
import com.lk.pearson.converter.constants.Outputs;

public class Output {
	
	private String outputAsString;
	private byte[] outputasByteArray;
	private File outputAsFile;
	private FileInputStream outputAsFileInputStream;
	private InputStream inputAsInputStream;
	private String outputFileName;
	private Outputs outputFormat;
	private String token;
	private LifeCycle lifeCycle;
	
	public String getOutputAsString() {
		return outputAsString;
	}
	public void setOutputAsString(String outputAsString) {
		this.outputAsString = outputAsString;
	}
	public byte[] getOutputasByteArray() {
		return outputasByteArray;
	}
	public void setOutputasByteArray(byte[] outputasByteArray) {
		this.outputasByteArray = outputasByteArray;
	}
	public File getOutputAsFile() {
		return outputAsFile;
	}
	public void setOutputAsFile(File outputAsFile) {
		this.outputAsFile = outputAsFile;
	}
	public FileInputStream getOutputAsFileInputStream() {
		return outputAsFileInputStream;
	}
	public void setOutputAsFileInputStream(FileInputStream outputAsFileInputStream) {
		this.outputAsFileInputStream = outputAsFileInputStream;
	}
	public InputStream getInputAsInputStream() {
		return inputAsInputStream;
	}
	public void setInputAsInputStream(InputStream inputAsInputStream) {
		this.inputAsInputStream = inputAsInputStream;
	}
	public String getOutputFileName() {
		return outputFileName;
	}
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
	public Outputs getOutputFormat() {
		return outputFormat;
	}
	public void setOutputFormat(Outputs outputFormat) {
		this.outputFormat = outputFormat;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(LifeCycle lifeCycle) {
		this.lifeCycle = lifeCycle;
	}

}
