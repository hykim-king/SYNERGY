package com.pcwk.ehr.event;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.cmn.WorkDiv;

@Mapper
public interface EventMapper extends WorkDiv<EventDTO> {
	/**
	 * 이벤트 등록
	 * 
	 * @param dto
	 * @return 등록 건수
	 */
	int doInsert(EventDTO dto);

	/**
	 * 이벤트 수정
	 * 
	 * @param dto
	 * @return 수정 건수
	 */
	int doUpdate(EventDTO dto);

	/**
	 * 이벤트 삭제
	 * 
	 * @param dto
	 * @return 삭제 건수
	 */
	int doDelete(EventDTO dto);

	/**
	 * 단건 조회
	 * 
	 * @param ecode
	 * @return 이벤트 DTO
	 */
	EventDTO selectOne(String ecode);

	/**
	 * 전체 조회
	 * 
	 * @return 이벤트 리스트
	 */
	List<EventDTO> selectAll();

	/**
	 * 검색 조건 조회
	 * 
	 * @param param
	 * @return 이벤트 리스트
	 */
	List<EventDTO> doRetrieve(DTO param);
}