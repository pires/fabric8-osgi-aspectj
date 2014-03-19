package com.github.pires.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple aspect.
 */
@Aspect
public class MyAspect {

  private static final Logger log = LoggerFactory.getLogger(MyAspect.class);

  @Before("execution(* *.*(..))")
  public void logBefore(JoinPoint joinPoint) {
    System.out.println("logBefore() is running for "
        + joinPoint.getSignature().getName());
  }

  @After("execution(* *.*(..))")
  public void logAfter(JoinPoint joinPoint) {
    log.info("logAfter() is running for {}", joinPoint.getSignature().getName());
  }

}
