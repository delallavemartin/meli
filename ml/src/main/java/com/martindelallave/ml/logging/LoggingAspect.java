package com.martindelallave.ml.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

  @Around("@within(com.martindelallave.ml.logging.LogExecution) || @annotation(com.martindelallave.ml.logging.LogExecution)")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();

    logger.info("Starting method: {}", joinPoint.getSignature());
    Object result = joinPoint.proceed();

    long executionTime = System.currentTimeMillis() - start;
    logger.info("Completed method: {} in {} ms", joinPoint.getSignature(), executionTime);

    return result;
  }

  @AfterThrowing(pointcut = "@within(com.martindelallave.ml.logging.LogExecution) || @annotation(com.martindelallave.ml.logging.LogExecution)", throwing = "exception")
  public void logException(org.aspectj.lang.JoinPoint joinPoint, Throwable exception) {
    logger.error("Exception in method: {} with arguments: {}. Exception: {}",
            joinPoint.getSignature(),
            joinPoint.getArgs(),
            exception.getMessage(),
            exception);
  }
}
