package com.pcwk.ehr.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.car.CarDTO;
import com.pcwk.ehr.cmn.WorkDiv;


@Mapper
public interface CarMapper extends WorkDiv<CarDTO>{

	Integer getCount();

	void deleteAll();
   
}
