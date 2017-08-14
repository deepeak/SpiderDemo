package com.zzd.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zzd.bean.HubeiCollage;
import com.zzd.bean.TbaInfo;
import com.zzd.bean.UrlInfo;

public class ConAndGetUrls {
	public static SaveToDB saveToDB = new SaveToDB();
	private static ExecutorService pool = Executors.newFixedThreadPool(100);

	public static List<UrlInfo> ExtractUrls(String url) {
		if (url == null || url.equals("")) {
			return null;
		}
		Connection conn = Jsoup.connect(url);
		Document doc = null;
		try {
			doc = conn.timeout(100000).get();
			if (doc == null) {
				doc = conn.timeout(100000).post();
			}
			if (doc == null) {
				return null;
			}
			final Document document = doc;
			TbaInfo tbInfo = new TbaInfo();
			Future<String> nameFuture = pool.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					Elements results1 = document.select("span.userinfo_username ");
					if (results1 != null && results1.size() != 0) {
						String linkText = results1.get(0).text();
						if (linkText != null && !linkText.equals("")) {
							return linkText;
						}
					}
					return null;
				}
			});

			Future<TbaInfo> propsFuture = pool.submit(new Callable<TbaInfo>() {
				@Override
				public TbaInfo call() throws Exception {
					TbaInfo tb = new TbaInfo();
					Elements results = document.select(".userinfo_userdata");
					for (Element element : results) {
						Elements links = element.getElementsByTag("span");
						for (Element link : links) {
							String linkCLass = link.attr("class");
							String linkText = link.text();
							if (linkCLass.contains("sex")) {
								if (linkCLass.contains("_male")) {
									tb.setSex("男");
								} else {
									tb.setSex("女");
								}
							} else if (linkText.contains("吧")) {
								String baAge = linkText.substring(3);
								if (baAge.contains("-")) {
									tb.setBaAge("0");
								} else {
									tb.setBaAge(baAge);
								}
							} else if (linkText.contains("发")) {
								int faTie = 0;
								if (linkText.endsWith("万")) {
									float faTies = Float.parseFloat(linkText.substring(3, linkText.length() - 1));
									faTie = (int) (faTies * 10000);
								} else if (linkText.contains("-")) {
									faTie = 0;
								} else {
									faTie = Integer.parseInt(linkText.substring(3));
								}
								tb.setFaTie((faTie));
							} else if (linkText.contains("会")) {
								tb.setVipDays(Integer.parseInt(linkText.substring(5, linkText.length() - 1)));
							}

						}
					}
					return tb;
				}
			});

			Future<String> lovebaFuture = pool.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					Element loveBaresults = document.getElementById("forum_group_wrap");
					if (loveBaresults != null) {
						Elements links = loveBaresults.select(".u-f-item.unsign");
						StringBuffer sbBuffer = new StringBuffer();
						for (Element link : links) {
							/*
							 * String linkTitle = link.attr("title"); if
							 * (linkTitle != null && !linkTitle.equals("")) {
							 * sbBuffer.append(linkTitle + "|"); continue; }
							 */
							Elements lovaBaNames = link.getElementsByTag("span");
							for (Element name : lovaBaNames) {
								String linkCLass = name.attr("class");
								String linkText = name.text();
								if (linkCLass == null || linkCLass.equals("")) {
									sbBuffer.append(linkText);
								} else if (linkCLass.equals("honor")) {
									sbBuffer.append("-" + linkText);
								} else if (linkCLass.contains("f")) {
									sbBuffer.append("-" + linkCLass.substring(14));
								}
							}
							sbBuffer.append("|");
						}
						String loves = sbBuffer.toString();
						return loves.substring(0, loves.length() - 1);
					}
					return null;
				}
			});

			Future<String> lovePlayFuture = pool.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					Element lovePlayBaresults = document.getElementById("game_group_wrap");
					if (lovePlayBaresults != null) {
						Elements links = lovePlayBaresults.select(".u-f-item.unsign");
						StringBuffer sbBuffer = new StringBuffer();
						for (Element link : links) {
							Elements lovaPlayBaNames = link.getElementsByTag("span");
							for (Element name : lovaPlayBaNames) {
								String linkCLass = name.attr("class");
								String linkText = name.text();
								if (linkCLass == null || linkCLass.equals("")) {
									sbBuffer.append(linkText);
								} else if (linkCLass.contains("f")) {
									sbBuffer.append("-" + linkCLass.substring(14));
								}
							}
							sbBuffer.append("|");
						}
						String lovesPlay = sbBuffer.toString();
						return lovesPlay.substring(0, lovesPlay.length() - 1);
					}
					return null;
				}
			});

			Future<String> foFuture = pool.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					Elements followersOrFollow = document.select("h1.ihome_aside_title");
					String follow = "";
					String follower = "";
					if (followersOrFollow != null) {
						for (Element content : followersOrFollow) {
							String text = content.text();
							if (text.contains("他关注的") || text.contains("她关注的")) {
								Elements numbers = content.getElementsByTag("span");
								for (Element detail : numbers) {
									Elements aText = detail.getElementsByTag("a");
									for (Element nums : aText) {
										String number = nums.text();
										if (number != null && !number.equals("")) {
											follow = number;
										}
									}
								}
							} else if (text.contains("关注他的") || text.contains("关注她的")) {
								Elements numbers = content.getElementsByTag("span");
								for (Element detail : numbers) {
									Elements aText = detail.getElementsByTag("a");
									for (Element nums : aText) {
										String number = nums.text();
										if (number != null && !number.equals("")) {
											follower = number;
										}
									}
								}
							}
						}
					}
					return follow + "-" + follower;
				}
			});

			Future<List<UrlInfo>> listfuture = pool.submit(new Callable<List<UrlInfo>>() {
				@Override
				public List<UrlInfo> call() throws Exception {
					Elements followers = document.select(".j_user_card");
					List<UrlInfo> datas = new ArrayList<UrlInfo>(100);
					if (followers != null) {
						for (Element follower : followers) {
							String followerLink = follower.attr("href");
							if (followerLink == null || followerLink.equals("")) {
								continue;
							}
							UrlInfo urlInfo = new UrlInfo();
							urlInfo.setUrlHref("http://tieba.baidu.com" + followerLink);
							datas.add(urlInfo);
						}
					}
					return datas;
				}
			});
			try {
				tbInfo.setName(nameFuture.get());
				TbaInfo tb = propsFuture.get();
				tbInfo.setSex(tb.getSex());
				tbInfo.setBaAge(tb.getBaAge());
				tbInfo.setFaTie(tb.getFaTie());
				tbInfo.setVipDays(tb.getVipDays());
				boolean flag = false;
				String lovaBa= lovebaFuture.get();
				if(lovaBa==""||lovaBa==null){
					return null;
				}
				String[] loveDesc = lovaBa.split("[|]");
				for (String str : loveDesc) {
					final String[] detail = str.split("-");
					Future<Boolean> havefuture1 = pool.submit(new Callable<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return HubeiCollage.OneHave(detail[0]);
						}
					});
					Future<Boolean> havefuture2 = pool.submit(new Callable<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return HubeiCollage.TwoHave(detail[0]);
						}
					});
					if (havefuture1.get() == true || havefuture2.get() == true) {
						flag = true;
						break;
					}
				}
				if(flag==false){
					return null;
				}
				tbInfo.setLoveBa(lovaBa);
				tbInfo.setLovePlayBa(lovePlayFuture.get());
				String[] fo = foFuture.get().split("-");
				if (fo.length == 0) {
					tbInfo.setFollows(0);
					tbInfo.setFollowers(0);
				} else if (fo.length == 1) {
					tbInfo.setFollows(Integer.parseInt(fo[0]));
					tbInfo.setFollowers(0);
				} else {
					if (fo[0] == null || fo[0].equals("")) {
						tbInfo.setFollows(0);
						tbInfo.setFollowers(Integer.parseInt(fo[1]));
					} else {
						tbInfo.setFollows(Integer.parseInt(fo[0]));
						tbInfo.setFollowers(Integer.parseInt(fo[1]));
					}

				}
				
				final TbaInfo tba = tbInfo;
				pool.execute(new Runnable() {
					@Override
					public void run() {
						saveToDB.saveTba(tba);
					}
				});
				return listfuture.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
