package com.innovify.events.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.innovify.events.dto.GenericResponse;
import com.innovify.events.exception.EventException;

@Aspect
@Configuration
public class ServiceAspect {
	
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(public * com.innovify.events.service.*.*(..))")
    public Object serviceAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
    	
    	System.out.println("Before invoking getName() method");
		Object value = null;
		try {
			value = proceedingJoinPoint.proceed();
		}catch(EventException e) {
			e.printStackTrace();
			value = new GenericResponse(100, "FAILED", e.getMessage()); 
		}
		catch (Throwable e) {
			System.out.println(e.getClass());
			e.printStackTrace();
			value = new GenericResponse(100, "FAILED", "Request Failed.");
		}
		System.out.println("After invoking getName() method. Return value="+value);
		return value;
    }


}
