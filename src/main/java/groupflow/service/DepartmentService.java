package groupflow.service;

import groupflow.domain.department.DepartmentDto;
import groupflow.domain.department.DepartmentEntity;
import groupflow.domain.department.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<DepartmentDto> allDepartments() {
        List<DepartmentDto> departmentDtoList = new ArrayList<>();

        List<DepartmentEntity> departmentEntityList = departmentRepository.findAll();
        if ( departmentEntityList.size() > 0 ) {
            departmentEntityList.forEach(d->{
                departmentDtoList.add( d.toDto() );
            });
        }
        return departmentDtoList;
    }
}
