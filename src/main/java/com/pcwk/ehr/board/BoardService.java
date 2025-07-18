package com.pcwk.ehr.board;

import java.util.List;

import com.pcwk.ehr.cmn.SearchDTO;

public interface BoardService {
	/**
	 * 조회 count증가(단, 본인글 이외 글만)
	 *
	 * @param param
	 * @return 반경 건수
	 */
	int updateReadCnt(BoardDTO param);

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
	int getBoardSeq();

	void deleteAll();

	int getCount();

	/**
	 * 목록 조회
	 *
	 * @param param
	 * @return List<T>
	 */
	List<BoardDTO> doRetrieve(SearchDTO param);

	/**
	 * 단건 삭제
	 *
	 * @param param
	 * @return 성공(1)/실패(0)
	 */
	int doDelete(BoardDTO param);

	/**
	 * 수정
	 *
	 * @param param
	 * @return 성공(1)/실패(0)
	 */
	int doUpdate(BoardDTO param);

	/**
	 * 단건조회
	 *
	 * @param param
	 * @return T
	 */
	BoardDTO doSelectOne(BoardDTO param);

	/**
	 * 단건등록
	 *
	 * @param param
	 * @return 1(성공)/0(실패)
	 */
	int doSave(BoardDTO param);

	List<BoardDTO> doMyDiary(BoardDTO boardDTO);

}
