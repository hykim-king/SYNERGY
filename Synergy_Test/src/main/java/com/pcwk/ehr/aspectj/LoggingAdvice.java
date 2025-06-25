/**
 * Package Name : com.pcwk.ehr.aspectj <br/>
 * 파일명: LogingAdvice.java <br/>
 */
package com.pcwk.ehr.aspectj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

public class LoggingAdvice {
	Logger log = LogManager.getLogger(getClass());

	public void logging(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();

		String methodName = signature.getName(); // 메소드 이름
		log.debug("methodName:" + methodName);

		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ *logging()*                                             │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}

}
