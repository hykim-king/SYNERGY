package com.pcwk.ehr.boardmapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.cmn.WorkDiv;

/**
 * 게시판 관련 Mapper 인터페이스
 */
@Mapper
public interface BoardMapper extends WorkDiv<BoardDTO> {

	/**
	 * 게시글 전체 건수
	 * 
	 * @return 게시글 수
	 */
	int getCount();

	/**
	 * 게시글 전체 조회 (단순 조회)
	 * 
	 * @return 게시글 목록
	 */
	List<BoardDTO> getAll();

	/**
	 * 검색 조건으로 게시글 조회
	 * 
	 * @param param 검색 DTO
	 * @return 조회된 게시글 목록
	 */
	List<BoardDTO> doRetrieve(BoardMapperDTO param);
	

	/**
	 * 전체 게시글 삭제 (테스트용)
	 */
	void deleteAll() throws SQLException;

	/**
	 * 전체 게시글 저장 (예시용/테스트용)
	 * 
	 * @return 저장된 건수
	 */
	int saveAll();

}