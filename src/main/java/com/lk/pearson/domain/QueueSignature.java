package com.lk.pearson.domain;

public class QueueSignature {

	private String queueName;

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	@Override
	public String toString() {
		return queueName;
	}

}
