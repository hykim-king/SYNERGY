package com.pcwk.ehr.drive;

public interface DriveResService {
    /**
     * 시승 신청 저장
     * @param dto 신청 정보 DTO
     * @return 저장 성공 시 1
     * @throws Exception
     */
    int doSave(DriveResDTO dto) throws Exception;
}