package com.pcwk.ehr.event;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.mapper.EventMapper;

@Service
public class EventServiceImpl implements EventService {
	Logger log = LogManager.getLogger(getClass());
	@Autowired
	EventMapper mapper;

	public EventServiceImpl() {
	}

	@Override
	public List<EventDTO> doRetrieve(SearchDTO param) {
		// TODO Auto-generated method stub
		return mapper.doRetrieve(param);
	}

	@Override
	public int doDelete(EventDTO param) {
		return mapper.doDelete(param);
	}

	@Override
	public int doUpdate(EventDTO param) {
		return mapper.doUpdate(param);
	}

	@Override
	public int doSave(EventDTO param) {
		return mapper.doSave(param);
	}

	@Override
	public EventDTO doSelectOne(EventDTO param) {
		// 단건 조회 + 조회 COUNT 증가
		String loginUserId = param.getRegId(); // 이 값이 null이면 조회수 증가 안됨
	    int flag = mapper.updateReadCnt(param);
	    log.debug("flag:{}", flag);

	    return mapper.doSelectOne(param);
	}

	@Override
	public int updateReadCnt(EventDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int saveAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEventSeq() {
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