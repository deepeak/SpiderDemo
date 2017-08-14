package com.zzd.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zzd.bean.TbaInfo;
import com.zzd.dao.MainDao;
import com.zzd.util.MyBatisFactory;

public class SaveToDB {
	public  static Map<Integer, List<TbaInfo>> cacheMap=new ConcurrentHashMap<Integer, List<TbaInfo>>(2);
	private static int which = 1;
	private static MainDao dao;
	static {
		@SuppressWarnings("resource")
		ApplicationContext context= new ClassPathXmlApplicationContext("applicationContext.xml");   
		dao= (MainDao)context.getBean("mainDao");   
		List<TbaInfo> list1=new ArrayList<TbaInfo>(1500);
		List<TbaInfo> list2=new ArrayList<TbaInfo>(1500);
		cacheMap.put(1, list1);
		cacheMap.put(2, list2);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				int tmp = which;
				if (which == 1) {
					which = 2;
				} else {
					which = 1;
				}
				System.out.println("flush to db:"+cacheMap.get(tmp).size());
					dao.addTbas(cacheMap.get(tmp));
					cacheMap.get(tmp).clear();
				
			}
		}, 3000, 5*60*1000);
	}
	
	public void saveTba(TbaInfo tba) {
		if (tba == null || tba.getName() == null || tba.getName().equals("")) {
			return;
		}
		cacheMap.get(which).add(tba);
	}
}
