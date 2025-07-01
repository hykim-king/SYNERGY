
package com.pcwk.ehr.board;

import java.sql.SQLException;
import java.util.List;

public interface BoardService {

	int doSave(BoardDTO dto);

	int doUpdate(BoardDTO dto);

	int doDelete(BoardDTO dto);

	BoardDTO doSelectOne(BoardDTO dto);

	List<BoardDTO> doRetrieve(BoardDTO dto);

	int getCount() throws SQLException;

	void deleteAll() throws SQLException;
}