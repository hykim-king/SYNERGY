package com.pcwk.ehr.boardservice;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.cmn.DTO;

public interface BoardService {
	int getCount() throws SQLException;

	int saveAll();

	List<BoardDTO> doRetrieve(DTO param);

	int doDelete(BoardDTO param);

	int doUpdate(BoardDTO param);

	List<BoardDTO> getAll();

	void deleteAll() throws SQLException;

	BoardDTO doSelectOne(BoardDTO param) throws SQLException;

	int doSave(BoardDTO param) throws SQLException;

	BoardDTO doSelectOne(List<BoardDTO> boards);

	int doInsert(BoardDTO dto);
}