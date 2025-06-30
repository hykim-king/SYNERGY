package com.pcwk.ehr.board;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface BoardDao {

	int doSave(BoardDTO dto); 

	int doUpdate(BoardDTO dto); 

	int doDelete(int boardCode); 

	BoardDTO doSelectOne(int boardCode); 

	List<BoardDTO> doRetrieve(BoardMapperDTO dto); 

	int getCount();

	void deleteAll();
}
