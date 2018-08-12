package com.cyhee.rabit.cmm.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ParameterLogger {
	private static final Logger logger = LoggerFactory.getLogger(ParameterLogger.class);
	
	@Around("@annotation(LogParam)")
	public Object onBeforeHandler(ProceedingJoinPoint joinPoint) throws Throwable {
		CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();

		String [] params = codeSignature.getParameterNames();
		Object [] args = joinPoint.getArgs();
		
		for(int i = 0; i < params.length; i++)
			System.out.println(String.format("[%s] %s", params[i], args[i]));
		
		return joinPoint.proceed();
	}
}
