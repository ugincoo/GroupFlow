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
@Table(name = "PositionChange")
public class PositionChangeEntity { // 직급변경테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pcno; // 식별번호
    @Column
    private LocalDateTime pcdate; // 적용날짜
    @Column
    private LocalDateTime enddate; // 끝날짜
    @Column
    private String endreason; // 끝날사유
    @OneToOne
    @JoinColumn(name="pno")
    private PositionEntity positionEntity; // 직급테이블 [ pk , 대리차장~ , 연차개수 ]

    @OneToOne
    @JoinColumn(name="eno")
    private EmployeeEntity employeeEntity; // 사원테이블 [ pk , ]
}
