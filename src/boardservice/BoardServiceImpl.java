/**
 * 
 */
package com.pcwk.ehr.boardservice;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.boardmapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	BoardMapper boardMapper;

	@Override
	public int doSave(BoardDTO board) throws SQLException {
		return boardMapper.doSave(board);
	}

	@Override
	public int doUpdate(BoardDTO board) throws SQLException {
		return boardMapper.doUpdate(board);
	}

	@Override
	public int doDelete(BoardDTO board) throws SQLException {
		return boardMapper.doDelete(board);
	}

	@Override
	public BoardDTO doSelectOne(BoardDTO board) throws SQLException {
		return boardMapper.doSelectOne(board);
	}

	@Override
	public List<BoardDTO> doRetrieve(BoardDTO board) {
		return boardMapper.doRetrieve(board);
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