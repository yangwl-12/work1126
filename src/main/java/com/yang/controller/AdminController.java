package com.yang.controller;

import com.yang.entity.Admin;
import com.yang.service.AdminService;
import com.yang.util.SecurityCode;
import com.yang.util.SecurityImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private TaskController taskController;

    //登录
    @ResponseBody
    @RequestMapping("login")
    public String login(String name, String password, String code, HttpServletResponse response,HttpServletRequest request)  {
       // taskController.run();
        Admin admin = adminService.findOneByName(name);
        String securityCode = (String)request.getSession().getAttribute("securitycode");
        System.out.println(code);
        if(admin!=null){
            if(password.equals(admin.getPassword())){
                if(!code.equals(securityCode)){
                    return "1验证码错误";
                }else{
                    request.getSession().setAttribute("admin",admin);
                    return null;
                }
            }else {
                return "2密码错误";
            }
        }

        return "3用户名不存在";
    }

    //验证码
    @RequestMapping("yzm")
    public void yzm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //生成验证码的随机数
        String securityCode = SecurityCode.getSecurityCode();
        HttpSession session = request.getSession();
        session.setAttribute("securitycode", securityCode);
        System.out.println(securityCode);
        //绘制生成验证码的图片
        BufferedImage createImage = SecurityImage.createImage(securityCode);
        //响应到客户端
        // HttpServletResponse response = ServletActionContext.getResponse();
        ServletOutputStream outputStream = response.getOutputStream();
        /* 第一个参数：指定验证码的图片对象
         * 第二个参数：图片得到的格式
         * 第三个参数:指定输出流
         *
         * */
        ImageIO.write(createImage, "png", outputStream);
        outputStream.close();
    }

    //退出登录
    @RequestMapping("safeout")
    public String safeOut(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        return "back/login";
    }

}
