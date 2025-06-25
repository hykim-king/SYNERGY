/* Package Name : com.pcwk.ehr.aspectj <br/>
 * 파일명: AfterAspectJTest.java <br/>
 */
package com.pcwk.ehr.aspectj;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:/com/pcwk/ehr/aspectj/before_applicationContext.xml"})
class BeforeAspectJTest {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	Member member;
	
	@Test
	void beforeAspectJ() {
		log.debug("┌─────────────────────────┐");
		log.debug("│ *beforeAspectJ()*       │");
		log.debug("└─────────────────────────┘");
		
		member.doSave();
		
		member.delete();
		
		member.doUpdate();
		
	}
	
	@Disabled
	@Test
	void test() {
		assertNotNull(context);
		assertNotNull(member);
		log.debug("context:"+context);
		log.debug("member:"+member);
	}
}