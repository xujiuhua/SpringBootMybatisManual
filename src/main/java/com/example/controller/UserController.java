package com.example.controller;

import com.example.domain.User;
import com.example.service.UserService;
import com.example.util.Common;
import com.example.util.PageView;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author xujiuhua
 * @create 2016-09-29-13:58
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("findUsers")
    @ResponseBody
    public List<User> findUsers() {
        List<User> users= userService.findUsers();
        return users;
    }

    /**
     * 测试事务
     * @param user
     * @param request
     */
    @RequestMapping("login")
    public void login(User user, HttpServletRequest request) {
        user.setId(1);
        userService.login(user);
    }

    /**
     * 分页查询，不带查询条件
     * http://localhost:8080/findByPage?pageNo=1&pageSize=2
     * @param request
     * @return
     */
    @RequestMapping("findByPage")
    @ResponseBody
    public Map<String, Object> findByPage(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        PageView pageView = null;
        String pageNow=request.getParameter("pageNo");
        String pageSize=request.getParameter("pageSize");
        if (Common.isEmpty(pageNow)) {
            pageView = new PageView(1);
        }else if(!Common.isEmpty(pageNow) && Common.isEmpty(pageSize)){
            pageView = new PageView(Integer.parseInt(pageNow));
        }else {
            pageView = new PageView(Integer.parseInt(pageSize),Integer.parseInt(pageNow));
        }
        pageView = userService.findByPage(pageView);
        List<User> list = pageView.getRecords();
        map.put("list", list);
        return map;
    }

    /**
     * 分页查询，带查询条件
     * @param request
     * @return
     */
    @RequestMapping("findByPasswordPage")
    @ResponseBody
    public Map<String, Object> findByPasswordPage(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        PageView pageView = null;
        User user = new User();
        user.setPassword("123456");
        String pageNow=request.getParameter("pageNo");
        String pageSize=request.getParameter("pageSize");
        if (Common.isEmpty(pageNow)) {
            pageView = new PageView(1);
        }else if(!Common.isEmpty(pageNow) && Common.isEmpty(pageSize)){
            pageView = new PageView(Integer.parseInt(pageNow));
        }else {
            pageView = new PageView(Integer.parseInt(pageSize),Integer.parseInt(pageNow));
        }
        pageView = userService.findByPasswordPage(pageView, user);
        List<User> list = pageView.getRecords();
        map.put("list", list);
        return map;
    }

}
