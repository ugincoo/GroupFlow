package groupflow.service;

import groupflow.domain.attendance.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    //출근퇴근---------------------------------------------------------------------------------------------

    public  boolean gowork( int eno, String ename){
        attendanceRepository.save()

        return result;
    }




}





