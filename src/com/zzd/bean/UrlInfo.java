package com.zzd.bean;

public class UrlInfo {
	private int urlId;
	private String urlText;
	private String urlHref;

	public int getUrlId() {
		return urlId;
	}

	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}

	public String getUrlText() {
		return urlText;
	}

	public void setUrlText(String urlText) {
		this.urlText = urlText;
	}

	public String getUrlHref() {
		return urlHref;
	}

	public void setUrlHref(String urlHref) {
		this.urlHref = urlHref;
	}

	public UrlInfo(String urlText, String urlHref) {
		super();
		this.urlText = urlText;
		this.urlHref = urlHref;
	}

	public UrlInfo() {
	}
}
