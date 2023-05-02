package groupflow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "DepartmentChange")
public class DepartmentChangeEntity { // 부서이동테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dcno; // 식별번호
    @Column
    private LocalDateTime dcstartdate; // 적용날짜
    @Column
    private LocalDateTime dcenddate; // 끝날짜
    @Column
    private String dcendreason; // 부서변경사유

    @OneToOne
    @JoinColumn(name="dno")
    private DepartmentEntity departmentEntity; // 부서테이블FK
        
    @OneToOne
    @JoinColumn(name="eno")
    private EmployeeEntity employeeEntity; // 사원테이블FK
}
