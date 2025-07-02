/**
 * 
 */
package com.pcwk.ehr.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.event.EventDTO;

@Mapper
public interface EventMapper extends WorkDiv<EventDTO> {

	/**
	 * 조회 count증가(단, 본인글 이외 글만)
	 * 
	 * @param param
	 * @return 반경 건수
	 */
	int updateReadCnt(EventDTO param);

	/**
	 * 다건 등록
	 * 
	 * @return 등록건수
	 */
	int saveAll();

	/**
	 * sequence 조회
	 * 
	 * @return
	 */
	int getEventSeq();

	void deleteAll();

	int getCount();

}