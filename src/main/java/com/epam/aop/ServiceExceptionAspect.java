package com.epam.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
@Aspect
@Component
public class ServiceExceptionAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExceptionAspect.class);

    @Around("execution(* com.epam.facade..*(..))")
    public Object handleServiceExceptions(ProceedingJoinPoint pjp) throws Throwable{
        try {
            return pjp.proceed();
        } catch (RuntimeException re) {
            LOGGER.error("error in {} {}",
                    pjp.getTarget().getClass().getSimpleName(),
                    pjp.getSignature().getName(), re);
        }
        return null;
    }
}
