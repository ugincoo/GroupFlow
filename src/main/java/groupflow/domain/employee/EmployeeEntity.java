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
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employee")
public class EmployeeEntity { // 직원테이블
    @Id
    private int eno;
    @Column
    private String ename;   //이름
    @Column
    private long esocialno;  //주민등록번호
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
    @Builder.Default
    private List<PositionChangeEntity> positionChangeEntityList=new ArrayList<>();
    
    // 근태테이블
    @OneToMany(mappedBy = "employeeEntity")
    @Builder.Default
    private List<AttendanceEntity> attendanceEntityList=new ArrayList<>();

    // 연차
    @OneToMany(mappedBy = "employeeEntity")
    @Builder.Default
    private List<LeaveRequestEntity> leaveRequestEntityList=new ArrayList<>();

    // 부서이동
    @OneToMany(mappedBy = "employeeEntity")
    @Builder.Default
    private List<DepartmentChangeEntity> departmentChangeEntityList=new ArrayList<>();

    public EmployeeDto toDto(){
        EmployeeDto employeeDto = EmployeeDto.builder()
                .eno(this.eno)
                .ename(this.ename)
                .esocialno(this.esocialno)
                .eemail(this.eemail)
                .ephone(this.ephone)
                .ephoto(this.ephoto)
                .hiredate(
                        this.hiredate.toLocalDate().format(DateTimeFormatter.ofPattern("yy-MM-dd"))
                )
                .build();
            if ( this.eenddate != null){
                employeeDto.setEenddate( this.eenddate.toLocalDate().format(DateTimeFormatter.ofPattern("yy-MM-dd") ) );
            }
        return employeeDto;

    }

}
