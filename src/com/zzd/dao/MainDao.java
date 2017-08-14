package com.zzd.dao;

import java.util.List;

import com.zzd.bean.TbaInfo;
import com.zzd.bean.UrlInfo;

public interface MainDao {

	/**
	 * @param url
	 * @return
	 */
	public int UnvisitedHave(String urlHref);

	/**
	 * @param url
	 * @return
	 */
	public int VisitedHave(String urlHref);

	/**
	 * @param urlInfo
	 */
	public void addToUnVisited(UrlInfo urlInfo);

	/**
	 * @param urlId
	 */
	public void deleteOneUnVisited(int urlId);

	/**
	 * @param urlInfo
	 */
	public void addToVisited(UrlInfo urlInfo);

	/**
	 * @param tb
	 */
	public void addTbas(List<TbaInfo> tb);

	/**
	 * @return
	 */
	public UrlInfo getOneUnVisited();

	/**
	 * @return
	 */
	public int getUnvisitedNum();
}
