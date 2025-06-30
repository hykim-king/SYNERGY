package com.pcwk.ehr.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.pcwk.ehr.cmn.WorkDiv;


@Mapper
public interface BoardMapper extends WorkDiv<BoardDTO> {

	int doSave(BoardDTO inVO);

	int doUpdate(BoardDTO inVO);

	int doDelete(int boardCode);

	BoardDTO doSelectOne(int boardCode);

	List<BoardDTO> doRetrieve();

	List<BoardDTO> doRetrieve(BoardMapperDTO dto);

	int getCount();

	void deleteAll();
}
