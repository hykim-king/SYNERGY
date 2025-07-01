package com.pcwk.ehr.eventService;

import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.event.EventDTO;

public interface EventService {

	/**
	 * 조건 검색 (ex: 키워드, 작성자, 날짜 등)
	 */
	List<EventDTO> doRetrieve(DTO param);

	/**
	 * 전체 이벤트 목록 조회
	 */
	List<EventDTO> selectAll();

	/**
	 * 단건 이벤트 조회
	 */
	EventDTO selectOne(String ecode);

	/**
	 * 이벤트 등록
	 */
	void doInsert(EventDTO param);

	/**
	 * 이벤트 수정
	 */
	void doUpdate(EventDTO param);

	/**
	 * 이벤트 삭제
	 */
	void doDelete(EventDTO param);
}
