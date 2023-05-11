package groupflow.domain.position;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class PositionDto {
    private int pno;        // 식별번호
    private String pname;   //직급명
    private int yearno;     //연차개수

    // 직급목록 출력시 toDto
    public PositionEntity toEntity(){
        return PositionEntity.builder()
                .pno(this.pno)
                .pname(this.pname)
                .yearno(this.yearno)
                .build();
    }
}
