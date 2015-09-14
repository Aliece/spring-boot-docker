package org.aliece.docker.web;

import org.aliece.docker.config.MybatisProperties;
import org.aliece.docker.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangsaizhong on 15/9/7.
 */
@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping("/products")
    public String velocity( Model model ) {
        model.addAttribute("products", productService.findAll());
        return "products";
    }
}
