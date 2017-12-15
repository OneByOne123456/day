package com.dp.demo.sys.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dp.common.AjaxResult;
import com.dp.common.Constant;
import com.dp.common.utils.util.JsonUtil;
import com.dp.common.utils.util.UserUtil;
import com.dp.demo.sys.pojo.Family;
import com.dp.demo.sys.pojo.User;
import com.dp.demo.sys.service.FamilyService;
import com.dp.demo.sys.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import net.sf.ehcache.search.impl.BaseResult;


@Controller
@RequestMapping("/user")
public class UserController {
	private static Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private FamilyService familyService;
	
	//用户登录  
    @RequestMapping("/login.do") 
    @ResponseBody
    public AjaxResult login(User user, HttpServletRequest request){  
        Subject subject=SecurityUtils.getSubject();  
        UsernamePasswordToken token=new UsernamePasswordToken(user.getName(),user.getPassword());  
        try {  
            //调用subject.login(token)进行登录，会自动委托给securityManager,调用之前  
            subject.login(token);//会跳到我们自定义的realm中  
            User u = userService.getByUsername(user.getName());
            request.getSession().setAttribute(Constant.SESSION_CURRENT_USER,u);  
            return new AjaxResult().success(u.getFamilyId());
        }catch (Exception e){ 
        	log.error(e.getMessage(),e);
            return new AjaxResult().error();
        }  
    }  
  
    @RequestMapping("/logout.do")  
    public String logout(HttpServletRequest request){  
        request.getSession().invalidate();  
        return "login";  
    }  
    @RequestMapping("/index.do")
	public String index(HttpServletRequest request){
		return "/static/html/index";
	}
	@RequestMapping("/queryAllUser.do")
	@ResponseBody
	public AjaxResult queryAllUser(){
		List<User> list = userService.queryAllUser();
		return AjaxResult.success(list);
	}
	@RequestMapping("/register.do")
	@ResponseBody
	public AjaxResult register(User user){
		try {
			userService.register(user);
			return AjaxResult.success();
		} catch(IllegalArgumentException e){
			log.error(e.getMessage(),e);
			return AjaxResult.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return AjaxResult.error("注册失败！");
		}
	}
	@RequestMapping("/userData.do")
	public String showUser(HttpServletRequest request){
		try {
			Map<String,Object> map = userService.userData();
			request.setAttribute("user", JsonUtil.getJson(map.get("user")));
			request.setAttribute("family", JsonUtil.getJson(map.get("family")));
			request.setAttribute("familyUserList", JsonUtil.getJson(map.get("familyUserList")));
			request.setAttribute("admin", map.get("admin"));
			return "/static/html/userData";
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(),e);
			return "/static/html/userData";
		}
	}
}
