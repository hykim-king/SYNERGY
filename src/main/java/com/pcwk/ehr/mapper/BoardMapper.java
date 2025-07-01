package com.pcwk.ehr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.cmn.WorkDiv;

@Mapper
public interface BoardMapper extends WorkDiv<BoardDTO> {

	int doSave(BoardDTO inVO);

	int doUpdate(BoardDTO inVO);

	int doDelete(int boardCode);

	BoardDTO doSelectOne(int boardCode);

	List<BoardDTO> doRetrieve();

	List<BoardDTO> doRetrieve(BoardDTO search);

	int getCount();

	void deleteAll();
}
