package edu.awieclawski.web.models;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Cluster implements Serializable {
	/**
	 * Model of Response
	 */
	private static final long serialVersionUID = 3430580281662148311L;
	private long modulusn; // modulus 'n'
	private long key; // public or private key
	private String message; // received or sent text
	private List<Integer> encoded; // list of encrypted numbers
	private String reqid; // request id

	public Cluster() {
		super();
	}

	public Cluster(long modulusn, long key, String message) {
		this();
		this.modulusn = modulusn;
		this.key = key;
		this.message = message;
	}

	public Cluster(long modulusn, long key, String message, String reqid) {
		this(modulusn, key, message);
		this.reqid = reqid;
	}

	public Cluster(long modulusn, long key, String message, List<Integer> encoded, String reqid) {
		this(modulusn, key, message, reqid);
		this.encoded = encoded;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reqid == null) ? 0 : reqid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cluster other = (Cluster) obj;
		if (reqid == null) {
			if (other.reqid != null)
				return false;
		} else if (!reqid.equals(other.reqid))
			return false;
		return true;
	}

	public long getModulusn() {
		return modulusn;
	}

	public void setModulusn(long modulusn) {
		this.modulusn = modulusn;
	}

	public long getKey() {
		return key;
	}

	public void setKey(long key) {
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReqid() {
		return reqid;
	}

	public void setReqid(String reqid) {
		this.reqid = reqid;
	}

	public List<Integer> getEncoded() {
		return encoded;
	}

	public void setEncoded(List<Integer> encoded) {
		this.encoded = encoded;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append("Prime");
		sb.append("{modulusn=").append(modulusn);
		sb.append(", key=").append(key);
		sb.append(", message=").append(message);
		sb.append(", reqid=").append(reqid);
		sb.append('}');
		return sb.toString();
	}

}
