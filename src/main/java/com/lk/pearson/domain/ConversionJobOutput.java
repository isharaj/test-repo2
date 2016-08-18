package com.lk.pearson.domain;

import java.util.ArrayList;
import java.util.List;

public class ConversionJobOutput {
	
	private String tokenId;
	private List<Output> outputList = new ArrayList<Output>();
	
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public List<Output> getOutputList() {
		return outputList;
	}
	public void setOutputList(List<Output> outputList) {
		this.outputList = outputList;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}else if (obj instanceof ConversionJobOutput) {
			if (((ConversionJobOutput) obj).getTokenId().equals(tokenId)) {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 100;
	}

}
