package com.pcwk.ehr.mapper;
import com.pcwk.ehr.cmn.WorkDiv;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.retailer.RetailerDTO;
@Mapper
public interface Retailermapper extends WorkDiv<RetailerDTO>{
    List<RetailerDTO> getAll();
    RetailerDTO getOne(int retailer_code);
    List<RetailerDTO> doRetrieveByCarMfList(@Param("carMfList") List<String> carMfList);
    int getCount();
    int deleteAll() throws SQLException;
}