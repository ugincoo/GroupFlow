package groupflow.domain.position;

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
@Table(name = "positionchange")
public class PositionChangeEntity { // 직급변경테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pcno; // 식별번호
    @Column
    private LocalDate pcdate; // 적용날짜
    @Column
    private LocalDate enddate; // 끝날짜
    @Column
    private String pcstartreason; // 적용사유

    @ManyToOne
    @JoinColumn(name="pno")
    @ToString.Exclude
    private PositionEntity positionEntity; // 직급테이블 [ pk , 대리차장~ , 연차개수 ]

    @ManyToOne
    @JoinColumn(name="eno")
    @ToString.Exclude
    private EmployeeEntity employeeEntity; // 사원테이블 [ pk , ]
}
