package com.jumu.ring.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.jumu.ring.config.WechatConfig;
import com.jumu.ring.dao.OrderDao;
import com.jumu.ring.entity.CrbtOrder;
import com.jumu.ring.utils.MD5;
import com.jumu.ring.utils.UUIDGenerator;
import com.jumu.ring.utils.WechatCore;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/22.
 */
@Service
public class OrderService {
	
	private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderDao orderDao;

    public Object save(CrbtOrder order,String openId,String ip) {
    	order.setStatus((byte) 0);
    	order = orderDao.save(order);
    	log.info("返回参数"+order.toString());
    	Map<String, String> resultsMap = new HashMap<String, String>();
		Map<String, String > paramMap = new HashMap<String, String>();
		paramMap.put("appid", WechatConfig.APPID);
		paramMap.put("mch_id", WechatConfig.MCHID);
		paramMap.put("nonce_str", UUIDGenerator.generator().toUpperCase());
		paramMap.put("body", "铃声费");
		paramMap.put("out_trade_no",String.valueOf(order.getId()));
		paramMap.put("fee_type", "CNY");
		paramMap.put("total_fee", Math.round(order.getFee()*100)+"");
		paramMap.put("spbill_create_ip", ip);
		paramMap.put("notify_url", WechatConfig.NOTIFYURL);
		paramMap.put("trade_type", "JSAPI");
		paramMap.put("openid", openId);
		paramMap.put("attach", "2");//支付类型，1充值，2单个停车费，3补缴
		paramMap = WechatCore.paraFilter(paramMap);
		String result = WechatCore.createLinkString(paramMap);
		result += "&key="+WechatConfig.KEYSECRECT;
		try {
			result = MD5.getMessageDigest(result.getBytes("utf-8")).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		paramMap.put("sign", result);
		result = WechatCore.mapToXml(paramMap);
		log.info("统一订单提交前参数:"+result);
		PostMethod method = null;
		try {
			method = new PostMethod(WechatConfig.ORDERURL);
			method.setRequestContentLength(result.length());
			method.setRequestBody(result);
			HttpClient httpClient = new HttpClient();
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			int state = httpClient.executeMethod(method);
			result = method.getResponseBodyAsString();
			log.info("微信传回参数"+result);
			log.info("返回参数"+state);
			if(state==200){
				paramMap =	WechatCore.xmlToMap(result);
				log.info("微信传回参数"+paramMap.toString());
				String signData = paramMap.get("sign");
				paramMap = WechatCore.paraFilter(paramMap);
				result = WechatCore.createLinkString(paramMap);
				result += "&key="+WechatConfig.KEYSECRECT;
				try {
					result = MD5.getMessageDigest(result.getBytes("utf-8")).toUpperCase();
				} catch (UnsupportedEncodingException e1) {
				}
				if(!result.equals(signData)){
					log.info("");
					return null;
				}
				String return_code = paramMap.get("result_code");
				log.info("微信传回参数code"+return_code);
				if(return_code.equals("SUCCESS")){
					log.info("回复参数" +resultsMap.toString() );
					String prepay_id = paramMap.get("prepay_id");
					resultsMap.put("appId", WechatConfig.APPID);
					resultsMap.put("package", "prepay_id="+prepay_id);
					resultsMap.put("nonceStr", UUIDGenerator.generator().toUpperCase());
					resultsMap.put("signType", "MD5");
					resultsMap.put("timeStamp", Long.toString(System.currentTimeMillis()/1000));
					resultsMap = WechatCore.paraFilter(resultsMap);
					result = WechatCore.createLinkString(resultsMap)+"&key="+WechatConfig.KEYSECRECT;
					result = MD5.getMD5ofStr(result).toUpperCase();
					resultsMap.put("paySign", result);
					resultsMap.put("payment_id", Long.toString(order.getId()));
					resultsMap.put("money", Double.toString(order.getFee()));
					log.info("回复参数" +resultsMap.toString() );
					 return resultsMap;
				}else{
					return null;
				}
			}else{
				return null;
			}
		} catch (Exception e) {
			// logger.error("数据请求异常" , e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
        return resultsMap;
    }

    public CrbtOrder findOne(long id) {
        return orderDao.findOne(id);
    }

    
}
