package groupflow.domain;

import lombok.*;

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

    @ManyToOne
    @JoinColumn(name="dno")
    @ToString.Exclude
    private DepartmentEntity departmentEntity; // 부서테이블FK
        
    @ManyToOne
    @JoinColumn(name="eno")
    @ToString.Exclude
    private EmployeeEntity employeeEntity; // 사원테이블FK
}
