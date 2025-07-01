/**
 * 
 */
package com.pcwk.ehr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.event.EventDTO;

@Mapper
public interface EventMapper extends WorkDiv<EventDTO> {

	/**
	 * 조건 검색
	 * 
	 * @param param 검색조건 포함 DTO
	 * @return 검색 결과 리스트
	 */
	List<EventDTO> doRetrieve(DTO param);

	/**
	 * 전체 이벤트 조회
	 * 
	 * @return 이벤트 전체 목록
	 */
	List<EventDTO> selectAll();

	/**
	 * 단건 이벤트 조회
	 * 
	 * @param ecode 이벤트 코드
	 * @return 해당 이벤트 DTO
	 */
	EventDTO selectOne(String ecode);

	/**
	 * 이벤트 등록
	 * 
	 * @param param 등록할 이벤트 DTO
	 */
	void doInsert(EventDTO param);

	/**
	 * 이벤트 수정
	 * 
	 * @param param 수정할 이벤트 DTO
	 * @return 수정 건 수
	 */
	int doUpdate(EventDTO param);

	/**
	 * 이벤트 삭제
	 * 
	 * @param param 삭제할 이벤트 DTO
	 * @return 삭제 건 수
	 */
	int doDelete(EventDTO param);

}