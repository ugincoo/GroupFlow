package groupflow.domain.evaluation;

import groupflow.domain.BaseTime;
import groupflow.domain.employee.EmployeeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.time.format.DateTimeFormatter;

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

    public EvaluationDto toDto(){
        return EvaluationDto.builder()
                .evno(this.evno)
                .evopnion(this.evopnion)
                .evaluatorEno(this.evaluatorEmployeeEntity.getEno())
                .targetEno(this.targetEmployeeEntity.getEno())
                .cdate(this.cdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd a HH:mm:ss")))
                .udate(this.udate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd a HH:mm:ss")))
                .build();
    }

}
