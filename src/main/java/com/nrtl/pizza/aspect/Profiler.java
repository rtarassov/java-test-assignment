package com.nrtl.pizza.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
@ConditionalOnProperty(prefix = "profiler", name = "enabled", havingValue = "true")
public class Profiler {


	@SuppressWarnings("squid:S00112")
	@Around("profileAddOrderCall()")
	public Object profile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		String taskName = getTaskName(proceedingJoinPoint);
		stopWatch.start(taskName);
		boolean isExceptionThrown = false;
		try {
			return proceedingJoinPoint.proceed();
		} catch (RuntimeException e) {
			isExceptionThrown = true;
			throw e;
		} finally {
			stopWatch.stop();
			StopWatch.TaskInfo taskInfo = stopWatch.getLastTaskInfo();
			String profileMessage = taskInfo.getTaskName() + ": " + taskInfo.getTimeMillis() + " ms" +
					(isExceptionThrown ? " (thrown Exception)" : "");
			log.info(profileMessage);
		}
	}

	private String getTaskName(ProceedingJoinPoint proceedingJoinPoint) {
		String packageName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
		String methodName = proceedingJoinPoint.getSignature().getName();
		return packageName + ":" + methodName;
	}

	@Pointcut("execution(* com.nrtl.pizza.controller.OrderController.addOrder(..))")
	public void profileAddOrderCall() {
		//AOP POINTCUT
	}

}