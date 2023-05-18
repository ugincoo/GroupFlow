package groupflow.domain.department;

import groupflow.domain.employee.EmployeeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "departmentchange")
public class DepartmentChangeEntity { // 부서이동테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dcno; // 식별번호

    @Column
    private LocalDate dcstartdate; // 적용날짜

    @Column
    private LocalDate dcenddate; // 끝날짜

    @Column
    private String dcstartreason; // 부서변경사유

    @ManyToOne
    @JoinColumn(name="dno")
    @ToString.Exclude
    private DepartmentEntity departmentEntity ; // 부서테이블FK
        
    @ManyToOne
    @JoinColumn(name="eno")
    @ToString.Exclude
    private EmployeeEntity employeeEntity; // 사원테이블FK


    public DepartmentChangeDto todto(){
        return DepartmentChangeDto.builder()
                .dcno(this.dcno)
                .dno(this.departmentEntity.getDno())
                .eno(this.employeeEntity.getEno())
                .dcstartreason(this.dcstartreason.toString())
                .dcenddate(this.dcenddate.toString())
                .dcstartreason(this.dcstartreason)
                .build();
    }
}
