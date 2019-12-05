package com.yang.aspect;

import com.yang.annotaction.LogAnnotaction;
import com.yang.entity.Admin;
import com.yang.entity.Log;
import com.yang.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@Configuration
@Aspect
public class LogAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private LogService logService;
    @Around("@annotation(com.yang.annotaction.LogAnnotaction)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){
            //谁在什么时间做了什么事，是否成功

        //谁
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        //时间
        Date date = new Date();
        //获取方法的名字
        String name = proceedingJoinPoint.getSignature().getName();
        //获取注解的信息
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        LogAnnotaction annotation = signature.getMethod().getAnnotation(LogAnnotaction.class);
        String value = annotation.value();
        //System.out.println(value);

        try {
            Object proceed = proceedingJoinPoint.proceed();
            String status="success";
            String sid = UUID.randomUUID().toString().replace("-","");
            Log log = new Log().setId(sid).setAction(value).setAdmin(admin.getName()).setStatus(status).setTime(date);
            logService.addOne(log);
            //System.out.println(admin+date+name+status);
            return proceed;
        } catch (Throwable throwable) {
            String status="error";
            Log log = new Log().setAction(value).setAdmin(admin.getName()).setStatus(status).setTime(date);
            logService.addOne(log);
            System.out.println(admin.getName()+date+name+status);
            throwable.printStackTrace();
            return null;

        }


    }
}
