package cn.e3mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转Controller
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }
    //主要是进行一个请求参数页面的跳转    也可以一个一个写
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }


}
