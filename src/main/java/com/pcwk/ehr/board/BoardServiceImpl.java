/**
 * 
 */
package com.pcwk.ehr.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author user
 *
 */
@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper boardMapper;
	
	public BoardServiceImpl() {}
	
	@Override
	public int doSave(BoardDTO param) {
		return boardMapper.doSave(param);
	}

	@Override
	public int doUpdate(BoardDTO param) {
		return boardMapper.doUpdate(param);
	}

	@Override
	public int doDelete(BoardDTO param) {
		return boardMapper.doDelete(param.getBoardCode());
	}

	@Override
	public BoardDTO doSelectOne(BoardDTO param) {
		return boardMapper.doSelectOne(param.getBoardCode()); 
	}

	@Override
	public List<BoardDTO> doRetrieve(BoardMapperDTO param) {
		return boardMapper.doRetrieve(param);
	}

	@Override
	public int getCount() {
		return boardMapper.getCount();
	}

	@Override
	public void deleteAll() {
		boardMapper.deleteAll();
	}

	@Override
	public List<BoardDTO> doRetrieve(BoardDTO param) {
		// TODO Auto-generated method stub
		return null;
	}
}

/**
 * 
 */
package com.pcwk.ehr.board;

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

	public int doSave(BoardDTO dto) {
		return boardMapper.doSave(dto);
	}

	public int doUpdate(BoardDTO dto) {
		return boardMapper.doUpdate(dto);
	}

	public int doDelete(BoardDTO dto) {
		return boardMapper.doDelete(dto);
	}

	public BoardDTO doSelectOne(BoardDTO dto) {
		return boardMapper.doSelectOne(dto);
	}

	public List<BoardDTO> doRetrieve(BoardDTO dto) {
		return boardMapper.doRetrieve(dto);
	}

	public int getCount() {
		return boardMapper.getCount();
	}

	public void deleteAll() {
		boardMapper.deleteAll();
	}
}
