/**
 * 
 */
package com.pcwk.ehr.boardservice;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;

	public BoardServiceImpl() {
	}

	@Override
	public void deleteAll() throws SQLException {
		boardMapper.deleteAll();
	}

	@Override
	public int getCount() throws SQLException {
		return boardMapper.getCount();
	}

	@Override
	public int saveAll() {
		return boardMapper.saveAll();
	}

	@Override
	public List<BoardDTO> doRetrieve(DTO param) {
		return boardMapper.doRetrieve(param);
	}

	@Override
	public int doDelete(BoardDTO param) {
		return boardMapper.doDelete(param);
	}

	@Override
	public int doUpdate(BoardDTO param) {
		return boardMapper.doUpdate(param);
	}

	@Override
	public List<BoardDTO> getAll() {
		return boardMapper.getAll();
	}

	@Override
	public BoardDTO doSelectOne(BoardDTO param) throws SQLException {
		return boardMapper.doSelectOne(param);
	}

	@Override
	public int doSave(BoardDTO param) throws SQLException {
		return boardMapper.doSave(param);
	}

	@Override
	public BoardDTO doSelectOne(List<BoardDTO> param) {
		return boardMapper.doSelectOne(param);
	}

	@Override
	public int doInsert(BoardDTO dto) {
		return boardMapper.doInsert(dto);
	}

}
