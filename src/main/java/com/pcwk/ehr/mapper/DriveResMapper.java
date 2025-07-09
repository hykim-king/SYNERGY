// DriveResMapper.java
package com.pcwk.ehr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pcwk.ehr.car.CarDTO;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.drive.DriveResDTO;
import com.pcwk.ehr.retailer.RetailerDTO;

@Mapper
public interface DriveResMapper extends WorkDiv<DriveResDTO> {
    /** 1) 제조사 목록 조회 */
    List<String> mfList();

    /** 2) 선택된 제조사의 제품명 목록 조회 */
    List<CarDTO> productList(@Param("carMf") String carMf);

    /** 3) 제조사+제품명으로 car_code 조회 */
    int getCarCode(
        @Param("carMf") String carMf,
        @Param("productName") String productName
    );

    /** 4) 제품명으로 업체 목록 조회 */
    List<RetailerDTO> retailerList(@Param("productName") String productName);

    //결과 화면
    CarDTO getCarInfoByCode(int carCode);
    RetailerDTO getRetailerInfoByCode(int retailerCode);

    public List<DriveResDTO> doRetrieveByUser(DriveResDTO dto);
    
    List<DriveResDTO> doRetrievePaging(DTO search) throws Exception;
    
    int getTotalCount(DTO dto);
	
    

}
