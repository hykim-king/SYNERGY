package com.pcwk.ehr.drive;

import com.pcwk.ehr.drive.DriveResDTO;
import java.util.List;

public interface DriveResService {
    int doSave(DriveResDTO dto);
    DriveResDTO doSelectOne(DriveResDTO dto);
    int doUpdate(DriveResDTO dto);
    int doDelete(DriveResDTO dto);

}