package com.itheima.ssm.controller;

import com.itheima.ssm.domain.SysLog;
import com.itheima.ssm.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


@Component
@Aspect
public class LogApp {

    //已经在web配置文件中配置了request的监听器，用于获取request对象
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISysLogService ISysLogService;

    //定义四个变量，来接收日志信息
    private Date startTime; // 访问时间
    private Class executionClass;// 访问的类
    private Method executionMethod; // 访问的方法

    @Before("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint joinPoint) throws NoSuchMethodException {//定义一个before方法，将切入点作为参数
        startTime=new Date();//获取操作开始时间
        executionClass = joinPoint.getTarget().getClass();//调用方法，获取切入点的class
        String methodName=joinPoint.getSignature().getName();//获取切入点的方法名称

        //获取切入点方法的参数
        Object[] args = joinPoint.getArgs();
        //判断该方法是否有参数
        if (args==null||args.length==0){
            //通过放射方式来获取方法
            executionMethod=executionClass.getMethod(methodName);
        }else {//如果方法有参数
            //executionClass.getMethod(methodName,参数的class对象)
            Class[] argsClass = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                //根据方法的参数，获取对应参数的class对象
                argsClass[i]=args[i].getClass();
            }
            executionMethod = executionClass.getMethod(methodName, argsClass);
        }
    }

    @After("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint joinPoint) throws Exception {//定义一个after方法，将切入点作为参数
        //还有username,ip,url,executionTime四个参数未获取到
        //获取执行时间
        Long executionTime=new Date().getTime()-startTime.getTime();

        //获取url,定义一个空的url来接收
        String url=null;
        //获取类上面的url
        if (executionClass!= SysLogController.class){
            //根据类上面的RequestMapping注解，获取注解
            RequestMapping classAnnotation = (RequestMapping) executionClass.getAnnotation(RequestMapping.class);
            if (classAnnotation!=null){//判断类上面的RequestMapping注解是否为空
                //获取方法上面的RequestMapping注解
                RequestMapping methodAnnotation=executionMethod.getAnnotation(RequestMapping.class);
                //拼接路径
                url=classAnnotation.value()[0]+methodAnnotation.value()[0];
            }
            //获取ip
            String ip = request.getRemoteAddr();

            //获取username
            //获取当前连接的user对象
            SecurityContext context = SecurityContextHolder.getContext();
            User user =(User) context.getAuthentication().getPrincipal();

            //创建一个SysLog对象，将所有数据封装进去
            SysLog sysLog = new SysLog();
            sysLog.setExecutionTime(executionTime);
            sysLog.setIp(ip);
            sysLog.setMethod("类名："+executionClass.getName()+"方法名："+executionMethod.getName());
            sysLog.setUrl(url);
            sysLog.setUsername(user.getUsername());
            sysLog.setVisitTime(startTime);

            //对象封装好了之后，调用service层，将数据传递给dao层存储到数据库
            ISysLogService.save(sysLog);

        }

    }
}
