package groupflow.domain.position;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PositionChangeDto {
    private int id; // 식별번호
    private String pcdate; // 적용날짜
    private String enddate; // 끝날짜
    private String pcstartreason; // 적용사유
    private int pno; // 직급테이블 fk
    private int eno;//  사원테이블 fk
    //추가
    private String pname;

    public PositionChangeEntity toEntity(){
        return PositionChangeEntity.builder()
                .pcno(this.id)
                .pcdate(LocalDate.parse(this.pcdate))//하는이유는 포지션체인지엔티티에서
                //자료형이 LocalDate이기때문에 그걸로 저장해야해서 LocalDate.parse쓴다
                //.enddate(LocalDate.parse(this.enddate))
                .pcstartreason(this.pcstartreason)
                .build();
    }

}
