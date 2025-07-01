/**
 * 
 */
package com.pcwk.ehr.board;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.mapper.BoardMapper;

/**
 * @author user
 *
 */
@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper boardMapper;
	
	public BoardServiceImpl() {	}

	@Override
	public int doSave(BoardDTO dto) {
		return boardMapper.doSave(dto);
	}

	@Override
	public int doUpdate(BoardDTO dto) {
		return boardMapper.doUpdate(dto);
	}

	@Override
	public int doDelete(BoardDTO dto) {
		return boardMapper.doDelete(dto);
	}

	@Override
	public BoardDTO doSelectOne(BoardDTO dto) {
		return boardMapper.doSelectOne(dto);
	}

	@Override
	public List<BoardDTO> doRetrieve(BoardDTO dto) {
		return boardMapper.doRetrieve(dto);
	}

	@Override
	public int getCount() throws SQLException {
		return boardMapper.getCount();
	}

	@Override
	public void deleteAll() throws SQLException {
		boardMapper.deleteAll();
	}
}
