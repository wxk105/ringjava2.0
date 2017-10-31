package com.jumu.ring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jumu.ring.service.WechatService;

@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
@RequestMapping("/wechat")
public class WechatController {

	@Autowired
	private WechatService wechatService;
	
	@RequestMapping(value = "/oppenid/{code}", method = RequestMethod.POST)
	@ResponseBody
	public String registerUser(@PathVariable String code) {
		 String openid = wechatService.getOpenId(code);
		 	return openid;
	    }
}
