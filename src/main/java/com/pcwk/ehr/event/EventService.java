package com.pcwk.ehr.event;

import java.util.List;

import com.pcwk.ehr.cmn.SearchDTO;

public interface EventService {
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

	/**
	 * 목록 조회
	 * 
	 * @param param
	 * @return List<T>
	 */
	List<EventDTO> doRetrieve(SearchDTO param);

	/**
	 * 단건 삭제
	 * 
	 * @param param
	 * @return 성공(1)/실패(0)
	 */
	int doDelete(EventDTO param);

	/**
	 * 수정
	 * 
	 * @param param
	 * @return 성공(1)/실패(0)
	 */
	int doUpdate(EventDTO param);

	/**
	 * 단건조회
	 * 
	 * @param param
	 * @return T
	 */
	EventDTO doSelectOne(EventDTO param);

	/**
	 * 단건등록
	 * 
	 * @param param
	 * @return 1(성공)/0(실패)
	 */
	int doSave(EventDTO param);

}
