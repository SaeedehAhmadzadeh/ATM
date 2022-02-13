package com.egs.atmservice.log;

import com.egs.atmservice.utils.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SysLogAspect {
    private final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);
    private final ObjectMapper objectMapper;

    public SysLogAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @After("allControllersMethods()")
    public void requestLogs(JoinPoint joinPoint) {
        String remoteAddress = HttpUtil.getRemoteAddress();
        Object[] args = joinPoint.getArgs();
        String request = "";
        String userAgent = HttpUtil.getUserAgent();

        try {
            if (args.length > 0) {
                int i = args.length - 1;
                request = toJsonString(args[i]);
            }
        } catch (Throwable e) {
            logger.warn(getLogger(
                    joinPoint.getSignature()) + " - From: " + remoteAddress + " - Exception about serialize: " + e
                    .getCause() + " " + e.getMessage());
        }

        logger.info(getLogger(joinPoint
                .getSignature()) + " - From: " + remoteAddress + " - Request: " + request + " - User-Agent: " + userAgent);
    }

    @AfterReturning(pointcut = "allControllersMethods()", returning = "returnValue")
    public void responseLogs(JoinPoint joinPoint, Object returnValue) {
        String remoteAddress = HttpUtil.getRemoteAddress();
        if (returnValue != null) {
            String json = "";
            try {
                json = toJsonString(returnValue);
            } catch (Throwable e) {
                logger.warn(getLogger(
                        joinPoint.getSignature()) + " - From: " + remoteAddress + " - Exception about serialize: " + e
                        .getCause() + " " + e.getMessage());
            }

            logger.info(getLogger(joinPoint.getSignature()) + " - From: " + remoteAddress + " - Response: " + json);
        }
    }

    @AfterThrowing(pointcut = "allControllersMethods()", throwing = "ex")
    public void exceptionLogs(Throwable ex) {
        String remoteAddress = HttpUtil.getRemoteAddress();
        logger.warn(" - From: " + remoteAddress + " - Exception{ message: " + ex.getMessage() + "}");
    }

    @Pointcut("execution(* com.egs.atmservice.controller.ATMController*.*(..))")
    public void allControllersMethods() {
    }

    private String getLogger(Signature signature) {
        String logger = signature.toString().split(" ")[1];
        logger = logger.split("\\(")[0];
        return logger;
    }

    private String toJsonString(Object object) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer();
        return objectWriter.writeValueAsString(object);
    }

}
