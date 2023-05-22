package groupflow.domain.evaluation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity@Table(name = "equestion")
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class EquestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eqno; //식별번호

    @Column( nullable = false )
    private String eqtitle; // 평가항목

    @Column( nullable = false )
    private String equestion; // 문항

    @Column
    @ColumnDefault("true")
    private boolean eqstate; // 문항을 선택했는지 확인

    public EquestionDto toDto(){
        return EquestionDto.builder()
                .eqno(this.eqno)
                .eqtitle(this.eqtitle)
                .equestion(this.equestion)
                .build();
    }

}
