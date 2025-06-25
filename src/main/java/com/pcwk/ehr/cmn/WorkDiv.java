/**
 * Package Name : com.pcwk.ehr.cmn <br/>
 * Class Name: WorkDiv.java <br/>
 * Description:  <br/>
 * Modification imformation : <br/> 
 * ------------------------------------------<br/>
 * 최초 생성일 : 2025-06-25<br/>
 *
 * ------------------------------------------<br/>
 * @author :user
 * @since  :2024-09-09
 * @version: 0.5
 */
package com.pcwk.ehr.cmn;

import java.util.List;

/**
 * @author user
 *
 */
public interface WorkDiv<T> {
	
	List<T> doRetrieve(DTO param);
	
	int doDelete(T param);

	int doUpdate(T param);
	
	T doSelectOne(T param);
	
	int doSave(T param);
}
