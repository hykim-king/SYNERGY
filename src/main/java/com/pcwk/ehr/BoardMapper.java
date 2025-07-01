package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.WorkDiv;

@Mapper
public interface BoardMapper extends WorkDiv<BoardDTO> {

	int saveAll();

	List<BoardDTO> getAll();

	void deleteAll() throws SQLException;

	int getCount() throws SQLException;

	BoardDTO doSelectOne(List<BoardDTO> param);

	List<BoardDTO> doRetrieve(DTO param);

	int doInsert(BoardDTO dto);

	List<BoardDTO> doRetrieve(BoardDTO dto);
	
	int doUpdate(BoardDTO dto);

}
