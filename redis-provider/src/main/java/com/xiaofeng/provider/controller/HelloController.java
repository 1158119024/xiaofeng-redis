package com.xiaofeng.provider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

/**
 * @Auther: 晓枫
 * @Date: 2019/4/6 00:20
 * @Description:
 */
@Controller
public class HelloController {

    /**
     * @param map
     * @return
     */
    @RequestMapping("/hello")
    public String helloHtml(HashMap<String, Object> map) {
        map.put("hello", "欢迎进入HTML页面");
        return "/index";
    }

    /**
     * @return
     */
    @RequestMapping("/sendMsgHtml")
    public String sendMsgHtml() {
        return "/sendMsg";
    }
}
