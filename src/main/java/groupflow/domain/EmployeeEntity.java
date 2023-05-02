package groupflow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Employee")
public class EmployeeEntity extends BaseTime { // 직원테이블
    @Id
    private String eno;
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
    //private  hiredate; basetime //입사일 -> basetime
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

}
