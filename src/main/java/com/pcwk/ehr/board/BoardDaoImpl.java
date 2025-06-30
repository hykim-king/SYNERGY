package com.pcwk.ehr.board;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDaoImpl {

	private final String NAMESPACE = "com.pcwk.ehr.board.BoardMapper";

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	public int doSave(BoardDTO inVO) {
		return sqlSessionTemplate.insert(NAMESPACE + ".doSave", inVO);
	}

	public int doUpdate(BoardDTO inVO) {
		return sqlSessionTemplate.update(NAMESPACE + ".doUpdate", inVO);
	}

	public int doDelete(int boardCode) {
		return sqlSessionTemplate.delete(NAMESPACE + ".doDelete", boardCode);
	}

	public BoardDTO doSelectOne(int boardCode) {
		return sqlSessionTemplate.selectOne(NAMESPACE + ".doSelectOne", boardCode);
	}

	public List<BoardDTO> doRetrieve() {
		return sqlSessionTemplate.selectList(NAMESPACE + ".doRetrieve");
	}
}