/**
 * 
 */
package com.pcwk.ehr.eventService;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.event.EventDTO;
import com.pcwk.ehr.mapper.EventMapper;

@Service
public class EventServiceImpl implements EventService {

	Logger log = LogManager.getLogger(getClass());

	@Autowired
	EventMapper mapper;

	public EventServiceImpl() {
	}

	@Override
	public List<EventDTO> doRetrieve(DTO param) {
		return mapper.doRetrieve(param);
	}

	@Override
	public List<EventDTO> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public EventDTO selectOne(String ecode) {
		return mapper.selectOne(ecode);
	}

	@Override
	public void doInsert(EventDTO param) {
		mapper.doInsert(param);
	}

	@Override
	public void doUpdate(EventDTO param) {
		mapper.doUpdate(param);
	}

	@Override
	public void doDelete(EventDTO param) {
		mapper.doDelete(param);
	}

}
