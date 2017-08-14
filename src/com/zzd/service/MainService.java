package com.zzd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import com.zzd.bean.UrlInfo;

public class MainService {
	private static UrlService service;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		service = (UrlService) context.getBean("urlService");
		List<UrlInfo> list = new ArrayList<UrlInfo>(1);
		list.add(new UrlInfo("", "http://tieba.baidu.com/home/main?id=4a158155d7d4b8bf5d4e&fr=userbar"));
		service.addToUnVisited(list);
		int count = 3;
		while (count-- >= 0) {
			List<UrlInfo> urls = ConAndGetUrls.ExtractUrls(service.getOneUnvisited().getUrlHref());
			service.addToUnVisited(urls);
		}
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 4; i++) {
			executorService.execute(new task());
		}
	}

	static class task implements Runnable {
		@Override
		public void run() {
			while (!(service.getUnvisitedNum()==0)) {
				List<UrlInfo> urls = ConAndGetUrls.ExtractUrls(service.getOneUnvisited().getUrlHref());
				service.addToUnVisited(urls);
			}
		}
	}

	public static void print(@SuppressWarnings("rawtypes") LinkedBlockingQueue queue) {
		if (queue.size() % 100 == 0) {
			System.out.println(queue.size());
		}
	}

	public static UrlService getService() {
		return service;
	}

	public static void setService(UrlService service) {
		MainService.service = service;
	}

}
