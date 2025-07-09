package com.pcwk.ehr.board;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	Logger log = LogManager.getLogger(getClass());
	@Autowired
	BoardMapper mapper;

	public BoardServiceImpl() {
	}

	@Override
	public List<BoardDTO> doRetrieve(SearchDTO param) {
		// TODO Auto-generated method stub
		return mapper.doRetrieve(param);
	}

	@Override
	public int doDelete(BoardDTO param) {
		return mapper.doDelete(param);
	}

	@Override
	public int doUpdate(BoardDTO param) {
		return mapper.doUpdate(param);
	}

	@Override
	public int doSave(BoardDTO param) {
		if (param.getId() == null || param.getId().isEmpty()) {
			param.setId(param.getRegId()); // 등록자와 동일하게
		}
		if (param.getNickname() == null || param.getNickname().isEmpty()) {
			param.setNickname("익명");
		}
		return mapper.doSave(param);
	}

	@Override
	public BoardDTO doSelectOne(BoardDTO param) {
		// 단건 조회 + 조회 COUNT 증가
		int flag = mapper.updateReadCnt(param);
		log.debug("flag:{}", flag);
		return mapper.doSelectOne(param);
	}

	@Override
	public int updateReadCnt(BoardDTO param) {
		return mapper.updateReadCnt(param);
	}

	@Override
	public int saveAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBoardSeq() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}