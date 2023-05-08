package groupflow.service;
import groupflow.domain.department.DepartmentRepository;
import groupflow.domain.employee.EmployeeRepository;
import groupflow.domain.leaverequest.LeaveRequestDto;
import groupflow.domain.leaverequest.LeaveRequestEntity;
import groupflow.domain.leaverequest.LeaveRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LeaveRequestService {
    @Autowired
    LeaveRequestRepository leaveRequestRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeRepository employeeRepository;



}
