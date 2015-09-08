package org.aliece.docker.web;

import org.aliece.docker.domain.User;
import org.aliece.docker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangsaizhong on 15/9/7.
 */
@RestController
@RequestMapping("/api/v/p")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value="/user",method= RequestMethod.GET)
    public List<User> readUserInfo(){
        List<User> list = new ArrayList<User>();
        list.add(userService.readUserByUsername("aliece"));
        return list;
    }

    @RequestMapping(value = "/user/{id}" , method = RequestMethod.GET )
    @ResponseBody
    public User findOne( @PathVariable("id") String id ) throws Exception {
        Future<User> res = userService.findOne(id);
        return res.get(1000, TimeUnit.MILLISECONDS);
    }


    @RequestMapping(value =  "/user" , method = RequestMethod.POST )
    @ResponseBody
    public String register( @RequestParam("name") String name ) {
        userService.insert(new User());
        return "success";
    }

}
