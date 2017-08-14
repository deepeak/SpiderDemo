package com.zzd.service;

import java.util.Iterator;
import java.util.List;

import com.zzd.bean.UrlInfo;

public class UrlsFilter {
	public static List<UrlInfo> filterUrls(List<UrlInfo> urls, String[] tags) {
		if (urls.size() == 0 || urls == null) {
			return null;
		}
		Iterator<UrlInfo> iterator = urls.iterator();
		while (iterator.hasNext()) {
			UrlInfo urlInfo = iterator.next();
			String urlText = urlInfo.getUrlText();
			String urlHref = urlInfo.getUrlHref();
			if (urlHref.length() >= 200) {
				iterator.remove();
				continue;
			}
			int flag = 0;
			for (String tag : tags) {
				if (urlText.contains(tag)) {
					flag = 1;
					break;
				}
			}
			if (flag == 0) {
				iterator.remove();
			}
		}
		return urls;

	}
}
