package com.pcwk.ehr.drive;

public interface DriveResService {
    int doSave(DriveResDTO dto);
    DriveResDTO doSelectOne(DriveResDTO dto);
    int doUpdate(DriveResDTO dto);
    int doDelete(DriveResDTO dto);

}