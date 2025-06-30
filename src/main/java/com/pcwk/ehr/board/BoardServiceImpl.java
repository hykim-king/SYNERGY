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
