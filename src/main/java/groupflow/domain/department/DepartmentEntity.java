package groupflow.domain.department;

import groupflow.domain.department.DepartmentChangeEntity;
import groupflow.domain.employee.EmployeeDto;
import groupflow.domain.leaverequest.LeaveRequestEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "department")
public class DepartmentEntity { // 부서테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int  dno;
    @Column
    private String dname;

    @OneToMany(mappedBy = "departmentEntity")
    @Builder.Default
    private List<DepartmentChangeEntity> departmentChangeEntityList  = new ArrayList<>();

    @OneToMany(mappedBy = "departmentEntity")
    @Builder.Default
    private List<LeaveRequestEntity> leaveRequestEntityList  = new ArrayList<>();


    public DepartmentDto toDto(){
        DepartmentDto departmentDto = DepartmentDto.builder()
                .dno(this.dno)
                .dname(this.dname)
                .build();
        return departmentDto;
    }
}
