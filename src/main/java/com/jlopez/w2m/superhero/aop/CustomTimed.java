package com.jlopez.w2m.superhero.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Configuration
public class CustomTimed {

	@Around("@annotation(com.jlopez.w2m.superhero.aop.TrackTime)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();

		Object result = joinPoint.proceed();

		long timeTaken = System.currentTimeMillis() - startTime;
		log.info("--> Time Taken by {} is {} ms", joinPoint.getSignature(), timeTaken);
		return result;
	}
}
