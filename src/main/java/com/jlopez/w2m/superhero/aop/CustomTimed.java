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

		StringBuilder logInfo = new StringBuilder(100);
		logInfo.append(joinPoint.getSignature().getDeclaringTypeName()).append("::")
				.append(joinPoint.getSignature().getName()).append(" [").append(timeTaken).append("ms]");

		log.info(logInfo.toString());
		return result;
	}
}
