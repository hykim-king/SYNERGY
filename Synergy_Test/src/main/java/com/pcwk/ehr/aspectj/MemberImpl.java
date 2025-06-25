/**
 * Package Name : com.pcwk.ehr.aspectj <br/>
 * 파일명: MemberImpl.java <br/>
 */
package com.pcwk.ehr.aspectj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MemberImpl implements Member {
	Logger log = LogManager.getLogger(getClass());

	@Override
	public int doSave() {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ *doSave()*                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		return 0;
	}

	@Override
	public int doUpdate() {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ *doUpdate()*                                            │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		return 0;
	}

	@Override
	public int doInsert() {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ *doInsert()*                                            │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		return 0;
	}

	@Override
	public int delete() {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ *delete()*                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		return 0;
	}

	@Override
	public int doRetrieve(int num) {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ *doRetrieve()*                                          │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		return 0;
	}

}
