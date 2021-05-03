package edu.awieclawski.web.service;

import java.io.Serializable;

/**
 * used to pass results, communicates between classes
 * 
 * @author AWieclawski
 *
 */
public class MessageService implements Serializable {

	private static final long serialVersionUID = -4156300228498149701L;

	private Integer intResult;
	private Boolean booResult;
	private String error;
	private String info;

	public MessageService(Integer intResult, String error, String info) {
		super();
		this.intResult = intResult;
		this.error = error;
		this.info = info;
	}

	public MessageService(Boolean booResult, String error, String info) {
		super();
		this.booResult = booResult;
		this.error = error;
		this.info = info;
	}

	public MessageService(String error, String info) {
		super();
		this.error = error;
		this.info = info;
	}

	public Integer getIntResult() {
		return intResult;
	}

	public void setIntResult(Integer intResult) {
		this.intResult = intResult;
	}

	public Boolean getBooResult() {
		return booResult;
	}

	public void setBooResult(Boolean booResult) {
		this.booResult = booResult;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public static MessageService getNewMessageService() {
		return new MessageService(-1, null, null);
	}

	@Override
	public String toString() {
		return "MessageService [intResult=" + intResult + ", booResult=" + booResult + ", error=" + error + ", info="
				+ info + "]";
	}

}
