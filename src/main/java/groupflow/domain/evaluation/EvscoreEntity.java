package groupflow.domain.evaluation;

import lombok.*;

import javax.persistence.*;

@Entity@Table(name = "evscore")
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class EvscoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int esno; // 식별번호

    @ManyToOne
    @JoinColumn(name = "evno" , nullable = false)
    @ToString.Exclude
    private EvaluationEntity evaluationEntity; // 평가테이블fk

    @ManyToOne
    @JoinColumn(name = "eqno" , nullable = false)
    @ToString.Exclude
    private EquestionEntity equestionEntity; // 문항테이블fk

    @Column( nullable = false )
    private int eqscore; // 점수
}
