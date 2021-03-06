package com.jumu.ring.controller;

import com.jumu.ring.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by Administrator on 2017/9/11.
 */
@ApiIgnore
@Controller
public class LoginController {

    @RequestMapping("/")
    public String index(){
        return "login";
    }

    @RequestMapping(value = "hello",method = RequestMethod.GET)
    public String hello(Model model) {
        model.addAttribute("hello", "welcome jumu");
        return "hello";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String hello(Model model, User user) {
        model.addAttribute("name", user.getUserName());
        return "index";
    }
}
