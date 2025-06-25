/**
 * Package Name : com.pcwk.ehr.aspectj <br/>
 * 파일명: AroundAdvice.java <br/>
 */
package com.pcwk.ehr.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;

import com.pcwk.ehr.cmn.PLog;

public class AroundAdvice implements PLog {

	public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {
		Object retObj = null;
		log.debug("┌─────────────────────────┐");
		log.debug("│ ***aroundLog()***       │");
		// 클래스 명
		String className = pjp.getTarget().getClass().getName();
		// 메소드 명
		String methodName = pjp.getSignature().getName();

		retObj = pjp.proceed();
		log.debug("│ ***className()***       │" + className);
		log.debug("│ ***methodName()***      │" + methodName);
		log.debug("└─────────────────────────┘");

		return retObj;

	}
}
