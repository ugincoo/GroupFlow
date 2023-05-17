package groupflow.domain.evaluation;

import groupflow.domain.BaseTime;
import groupflow.domain.employee.EmployeeEntity;
import lombok.*;

import javax.persistence.*;

@Entity@Table(name = "evaluation")
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class EvaluationEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int evno; // 식별번호

    @ManyToOne
    @JoinColumn(name="evaluatoreno" , nullable = false )
    @ToString.Exclude
    private EmployeeEntity evaluatorEmployeeEntity; // 평가자fk

    @ManyToOne
    @JoinColumn(name="targeteno" , nullable = false )
    @ToString.Exclude
    private EmployeeEntity targetEmployeeEntity; // 평가대상자fk
    
    @Column( nullable = false )
    private String evopnion; // 평가의견

    // BaseTime
    // cdate 평가날짜 
    // udate 평가수정날짜

}
