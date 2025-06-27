package com.pcwk.ehr.board;

import java.util.List;

public interface BoardService {

	int doSave(BoardDTO dto);

	int doUpdate(BoardDTO dto);

	int doDelete(BoardDTO dto);

	BoardDTO doSelectOne(BoardDTO dto);

	List<BoardDTO> doRetrieve(BoardDTO dto);

	int getCount();

	void deleteAll();
}
