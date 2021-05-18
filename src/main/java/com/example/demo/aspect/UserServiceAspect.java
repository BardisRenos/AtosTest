//package com.example.demo.aspect;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class UserServiceAspect {
//
//    @Before(value = "execution(* com.example.demo.service.*(..)) and args(country)")
//    public void beforeAdvice(JoinPoint joinPoint, String country){
//        System.out.println("Before method:" + joinPoint.getSignature());
//
//        System.out.println("A User retrieved with name = " + country);
//    }
//
//    @After(value = "execution(* com.example.demo.service.*(..)) and args(country)")
//    public void afterAdvice(JoinPoint joinPoint, String country){
//        System.out.println("After method:" + joinPoint.getSignature());
//
//        System.out.println("Successfully retrieved a User with country = " + country);
//    }
//}

