package cn.e3mall.sso.controller;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**用户登录处理
 * @Date 2019/8/27 10:11
 */
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;
    @RequestMapping("/page/login")
    public String showLogin(String redirect, Model model){
        model.addAttribute("redirect",redirect);
        return "login";
    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public E3Result login(String username, String password,
                          HttpServletRequest request, HttpServletResponse response){
        E3Result e3Result = loginService.userLogin(username, password);
        //判断是否登录成功
        if (e3Result.getStatus() == 200){
            String token = e3Result.getData().toString();
            //如果登录成功需要把token写入cookid
            CookieUtils.setCookie(request,response,TOKEN_KEY,token);
        }
        //返回结果
        return e3Result;
    }
}
