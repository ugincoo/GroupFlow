package groupflow.domain.employee;

import groupflow.domain.*;
import groupflow.domain.attendance.AttendanceEntity;
import groupflow.domain.department.DepartmentChangeEntity;
import groupflow.domain.leaverequest.LeaveRequestEntity;
import groupflow.domain.position.PositionChangeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employee")
public class EmployeeEntity extends BaseTime { // 직원테이블
    @Id
    private int eno;
    @Column
    private String ename;   //이름
    @Column
    private int esocialno;  //주민등록번호
    @Column
    private String eemail;  //이메일
    @Column
    private String ephone;  //휴대폰번호
    @Column
    private String ephoto;  //사진
    
    @Column
    private LocalDateTime hiredate; //입사일
    
    @Column
    private LocalDateTime eenddate; //퇴사일

    // 직급이동테이블
    @OneToMany(mappedBy = "employeeEntity")
    private List<PositionChangeEntity> positionChangeEntityList;
    
    // 근태테이블
    @OneToMany(mappedBy = "employeeEntity")
    private List<AttendanceEntity> attendanceEntityList;

    // 연차
    @OneToMany(mappedBy = "employeeEntity")
    private List<LeaveRequestEntity> leaveRequestEntityList;

    // 부서이동
    @OneToMany(mappedBy = "employeeEntity")
    private List<DepartmentChangeEntity> departmentChangeEntityList;

    public EmployeeDto toDto(){
        return EmployeeDto.builder()
                .eno(this.eno)
                .ename(this.ename)
                .esocialno(this.esocialno)
                .eemail(this.eemail)
                .ephone(this.ephone)
                .ephoto(this.ephoto)
                .hiredate(
                    this.hiredate.toLocalDate().format(DateTimeFormatter.ofPattern("yy-MM-dd"))
                )
                .eenddate(
                    this.eenddate.toLocalDate().format(DateTimeFormatter.ofPattern("yy-MM-dd"))
                )
                .build();
    }

}
