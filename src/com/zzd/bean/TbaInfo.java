package com.zzd.bean;

public class TbaInfo {
	private int tid;
	private String name;
	private String sex;
	private String baAge;
	private int vipDays;
	private int faTie;
	private String loveBa;
	private String lovePlayBa;
	private int followers;
	private int follows;
	public TbaInfo() {
	}
	/**
	 * @return the tid
	 */
	public int getTid() {
		return tid;
	}

	/**
	 * @param tid
	 *            the tid to set
	 */
	public void setTid(int tid) {
		this.tid = tid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}


	/**
	 * @return the faTie
	 */
	public int getFaTie() {
		return faTie;
	}

	/**
	 * @param faTie
	 *            the faTie to set
	 */
	public void setFaTie(int faTie) {
		this.faTie = faTie;
	}

	/**
	 * @return the loveBa
	 */
	public String getLoveBa() {
		return loveBa;
	}

	/**
	 * @param loveBa
	 *            the loveBa to set
	 */
	public void setLoveBa(String loveBa) {
		this.loveBa = loveBa;
	}

	public String getBaAge() {
		return baAge;
	}

	public void setBaAge(String baAge) {
		this.baAge = baAge;
	}

	public int getFollowers() {
		return followers;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}

	public int getFollows() {
		return follows;
	}

	public void setFollows(int follows) {
		this.follows = follows;
	}
	public int getVipDays() {
		return vipDays;
	}
	public void setVipDays(int vipDays) {
		this.vipDays = vipDays;
	}
	public String getLovePlayBa() {
		return lovePlayBa;
	}
	public void setLovePlayBa(String lovePlayBa) {
		this.lovePlayBa = lovePlayBa;
	}
}
