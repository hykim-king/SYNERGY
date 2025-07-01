package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.pcwk.ehr.car.CarDTO;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.drive.DriveResDTO;


@Mapper
public interface CarMapper {
    int add(CarDTO car);                 // INSERT
    List<CarDTO> getAll();
    CarDTO getOne(int carCode);
    List<CarDTO> doRetrieveByCarMfList(List<String> carMfs);
    int getCount();
}
