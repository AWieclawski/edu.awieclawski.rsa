package edu.awieclawski.web.models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CoPrimes implements Serializable {
	/**
	 * Model of Response
	 */
	private static final long serialVersionUID = -7730580281662148461L;
	private long modulusn; // modulus 'n'
	private long pubkey; // public key
	private long phin; // Euler's phi('n') function
	private String reqid; // request id

	public CoPrimes() {
		super();
	}

	public CoPrimes(long modulusn, long pubkey, long phin, String reqid) {
		super();
		this.modulusn = modulusn;
		this.pubkey = pubkey;
		this.phin = phin;
		this.reqid = reqid;
	}

	public CoPrimes(long modulusn, long pubkey, long phin) {
		super();
		this.modulusn = modulusn;
		this.pubkey = pubkey;
		this.phin = phin;
	}

	public CoPrimes(long modulusn, long pubkey, String reqid) {
		super();
		this.modulusn = modulusn;
		this.pubkey = pubkey;
		this.reqid = reqid;
	}

	public CoPrimes(long modulusn, long pubkey) {
		super();
		this.modulusn = modulusn;
		this.pubkey = pubkey;
	}

	public long getModulusn() {
		return modulusn;
	}

	public void setModulusn(long modulusn) {
		this.modulusn = modulusn;
	}

	public long getPubkey() {
		return pubkey;
	}

	public void setPubkey(long pubkey) {
		this.pubkey = pubkey;
	}

	public String getReqid() {
		return reqid;
	}

	public void setReqid(String reqid) {
		this.reqid = reqid;
	}

	public long getPhin() {
		return phin;
	}

	public void setPhin(long phin) {
		this.phin = phin;
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
		CoPrimes other = (CoPrimes) obj;
		if (reqid == null) {
			if (other.reqid != null)
				return false;
		} else if (!reqid.equals(other.reqid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Prime");
		sb.append("{modulusn=").append(modulusn);
		sb.append(", pubkey=").append(pubkey);
		sb.append(", phin=").append(phin);
		sb.append(", reqid='").append(reqid).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
