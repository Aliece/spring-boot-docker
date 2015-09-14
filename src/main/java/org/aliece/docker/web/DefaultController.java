package org.aliece.docker.web;

import org.aliece.docker.config.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangsaizhong on 15/9/7.
 */
@Controller
public class DefaultController {
    @Autowired
    private MybatisProperties mybatisProperties;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Spring Boot Application In Docker" + mybatisProperties.getBasePackage();
    }

    @RequestMapping("/velocity")
    public String velocity( Model model ) {
        model.addAttribute("test", "hi boot..");
        return "index";
    }
}
