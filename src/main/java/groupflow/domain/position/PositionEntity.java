package groupflow.domain.position;

import groupflow.domain.position.PositionChangeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "eposition")
public class PositionEntity { // 직급테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pno;        // 식별번호
    @Column
    private String pname;   //직급명
    @Column
    private int yearno;     //연차개수

    @OneToMany(mappedBy = "positionEntity")
    private List<PositionChangeEntity> positionChangeEntityList = new ArrayList<>();


}
