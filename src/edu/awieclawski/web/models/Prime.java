package edu.awieclawski.web.models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Prime implements Serializable {
	/**
	 * Model of Response
	 */
	private static final long serialVersionUID = 6237298250762821838L;
	private long value;
	private String reqid;

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public String getReqid() {
		return reqid;
	}

	public void setReqid(String reqid) {
		this.reqid = reqid;
	}

	public Prime() {
		super();
	}

	public Prime(long value) {
		this();
		this.value = value;
	}

	public Prime(long value, String reqid) {
		this(value);
		this.reqid = reqid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Prime number = (Prime) o;

		if (value != number.value)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (value ^ (value >>> 32));
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append("Prime");
		sb.append("{value=").append(value);
		sb.append(", reqid=").append(reqid);
		sb.append('}');
		return sb.toString();
	}

}