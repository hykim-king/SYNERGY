package com.pcwk.ehr.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.cmn.WorkDiv;

@Mapper
public interface BoardMapper extends WorkDiv<BoardDTO> {

	int doSave(BoardDTO dto);

	int doUpdate(BoardDTO dto);

	int doDelete(BoardDTO dto);

	BoardDTO doSelectOne(BoardDTO dto);

	List<BoardDTO> doRetrieve(BoardDTO dto);

	int getCount();

	void deleteAll();
}
