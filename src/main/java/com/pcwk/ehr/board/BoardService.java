package com.pcwk.ehr.board;

import java.sql.SQLException;
import java.util.List;

public interface BoardService {
	int doSave(BoardDTO dto);

	int doUpdate(BoardDTO dto);

	int doDelete(BoardDTO dto); // 여기만 BoardDTO 파라미터로 유지해도 무방

	BoardDTO doSelectOne(BoardDTO dto); // dto.getBoardCode() 활용

	List<BoardDTO> doRetrieve(BoardDTO search);

	int getCount() throws SQLException;

	void deleteAll() throws SQLException;
}