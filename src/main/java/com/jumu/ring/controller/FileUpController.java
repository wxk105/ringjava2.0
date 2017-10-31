package com.jumu.ring.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jumu.ring.config.WechatConfig;

@Controller
public class FileUpController {

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	private String main() {
		System.out.println("1111");
		return "index";
	}

	@RequestMapping(value = "/bellUpload", method = RequestMethod.POST)
	private String bellUpload(HttpServletRequest request, String turnover) {
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
		MultipartFile mfileTitle = mr.getFile("upLodefile");
		String fileName = mfileTitle.getOriginalFilename();
		try {
			InputStream inputStream = mfileTitle.getInputStream();
			FileOutputStream out = new FileOutputStream(WechatConfig.filePath + fileName);
			byte buffer[] = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			inputStream.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index";
	}

}
