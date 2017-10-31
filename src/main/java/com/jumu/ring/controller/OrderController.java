package com.jumu.ring.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jumu.ring.config.WechatConfig;
import com.jumu.ring.dao.OrderDao;
import com.jumu.ring.entity.CrbtOrder;
import com.jumu.ring.service.OrderService;
import com.jumu.ring.utils.MD5;
import com.jumu.ring.utils.WechatCore;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by Administrator on 2017/8/22.
 */
@Controller
@RequestMapping("order")
public class OrderController {
	
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDao orderDao;

    @ApiOperation(value="生成订单", notes="用户点击购买生成订单(目前还没有接入微信支付所以没有返回微信sign，返回的是订单实体)")
    @ApiImplicitParam(name = "order", value = "订单实体", required = true, dataType = "CrbtOrder")
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    @ResponseBody
    private Object insert(@RequestBody CrbtOrder order,HttpServletRequest request) {
    	String ip = request.getLocalAddr();
    	log.info(ip);
    	log.info(order.getOpenid());
    	Object obj = orderService.save(order,order.getOpenid(),ip);
    	log.info(obj.toString());
        return obj;
    }

    @ApiIgnore
    @RequestMapping(value = "findOne/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Object findOne(@PathVariable long id) {
        return orderService.findOne(id);
    }
    
    @RequestMapping(value = "notify")
    @ResponseBody
    public String notify(HttpServletRequest request){
    	Map<String, String> resultMap = new HashMap<String, String>();
    	InputStream is = null;
    	try {
			is = request.getInputStream();
			if (is !=null ) {
				String result = IOUtils.toString(is, "utf-8");
				log.info(result);
				if (StringUtils.isEmpty(result)) {
					// 参数错误
					resultMap.put("return_code", "FAIL");
					resultMap.put("return_msg", "参数错误");
					return WechatCore.mapToXml(resultMap);
				} else {
					Map<String, String> paramMap = WechatCore
							.xmlToMap(result);

					String return_code = paramMap.get("return_code");
					if (return_code.equals("SUCCESS")) {
						// 校验签名
						String signData = paramMap.get("sign");

						paramMap = WechatCore.paraFilter(paramMap);
						result = WechatCore.createLinkString(paramMap);
						result += "&key=" + WechatConfig.KEYSECRECT;
						try {
							result = MD5.getMessageDigest(
									result.getBytes("utf-8"))
									.toUpperCase();
						} catch (UnsupportedEncodingException e1) {
							// e1.printStackTrace();
						}
						// result = MD5.getMD5ofStr(result).toUpperCase();
						if (!result.equals(signData)) {
							resultMap.put("return_code", "FAIL");
							resultMap.put("return_msg", "签名错误");
							return WechatCore.mapToXml(resultMap);
						}

						String result_code = paramMap.get("result_code");
						if (result_code.equals("SUCCESS")) {
							// 成功支付
							// 交易成功，更新商户订单状态
							String id = paramMap.get("out_trade_no");
							log.info("订单编号"+id);
							CrbtOrder order = orderService.findOne(Long.parseLong(id));
							log.info("订单信息"+order.toString());
							if (order == null) {
								resultMap.put("return_code", "FAIL");
								resultMap.put("return_msg", "支付订单不存在！");
								return WechatCore.mapToXml(resultMap);
							}
							if (order.getStatus() == 1) {
								resultMap.put("return_code", "SUCCESS");
								resultMap.put("return_msg", "OK");
								return WechatCore.mapToXml(resultMap);
							}
							String total_fee = paramMap.get("total_fee");
							if (order.getFee() * 100 != Float
									.valueOf(total_fee)) {
								resultMap.put("return_code", "FAIL");
								resultMap.put("return_msg", "支付金额不一致！");
								return WechatCore.mapToXml(resultMap);
							}
							String pay_voucher = paramMap.get("transaction_id");
							String payment_time = paramMap.get("time_end");
							String bank_type = paramMap.get("bank_type");// 付款银行
							String type = paramMap.get("attach");//回传参数,付费内容，3补缴
							order.setStatus((byte) 1);
							order.setPaySuccessDate(new Date());
							orderDao.save(order);
						}
					}
				}
				
			}
			resultMap.put("return_code", "FAIL");
			resultMap.put("return_msg", "系统错误");
			return WechatCore.mapToXml(resultMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("return_code", "FAIL");
			resultMap.put("return_msg", "系统错误");
			return WechatCore.mapToXml(resultMap);
		}
    }
}
