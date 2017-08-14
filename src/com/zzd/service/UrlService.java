package com.zzd.service;

import java.util.List;

import com.zzd.bean.UrlInfo;
import com.zzd.dao.MainDao;

public class UrlService {
	private MainDao dao;
	private static byte[] lock=new byte[]{};
	private static byte[] lock1=new byte[]{};

	public void addToUnVisited(List<UrlInfo> list) {
		if(list==null){
			return;
		}
		synchronized (lock1) {
			for (UrlInfo ui : list) {
				if (dao.UnvisitedHave(ui.getUrlHref()) > 0 || dao.VisitedHave(ui.getUrlHref()) > 0) {
					continue;
				}
				dao.addToUnVisited(ui);
			}
		}
	}

	public UrlInfo getOneUnvisited() {
		UrlInfo ui = null;
		synchronized (lock) {
			ui = dao.getOneUnVisited();
		}
		dao.deleteOneUnVisited(ui.getUrlId());
		dao.addToVisited(ui);
		return ui;
	}

	public int getUnvisitedNum() {
		return dao.getUnvisitedNum();
	}

	public MainDao getDao() {
		return dao;
	}

	public void setDao(MainDao dao) {
		this.dao = dao;
	}
}
