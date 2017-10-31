package com.jumu.ring.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jumu.ring.config.WechatConfig;

@Service
public class WechatService {

	public String getOpenId(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=AppId&secret=AppSecret&code=CODE&grant_type=authorization_code";
		url = url.replace("AppId", WechatConfig.APPID)
				.replace("AppSecret", WechatConfig.APPSECRECT)
				.replace("CODE", code);
		HttpGet get = new HttpGet(url);
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		String openid = "";
		try {
			response = httpclient.execute(get);
			String jsonStr = EntityUtils
					.toString(response.getEntity(), "utf-8");
			JSONObject jsonTexts = (JSONObject) JSONObject.parse(jsonStr);

			if (jsonTexts.get("openid") != null) {
				openid = jsonTexts.get("openid").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return openid;
	}

}
