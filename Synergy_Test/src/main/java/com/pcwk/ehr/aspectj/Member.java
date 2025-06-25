/**
 * Package Name : com.pcwk.ehr.aspectj <br/>
 * 파일명: Member.java <br/>
 */
package com.pcwk.ehr.aspectj;

/**
 * @author user
 *
 */
public interface Member {
	
	int doSave();
	
	int doUpdate();
	
	int doInsert();
	
	int delete();
	
	int doRetrieve(int num);

}
