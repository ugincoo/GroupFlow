package groupflow.domain.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
@Builder
public class DepartmentDto {
    private int dno;        //부서번호
    private String dname;   //부서명

    public DepartmentEntity toEntity(){
        return DepartmentEntity.builder()
                .dno(this.dno)
                .dname(this.dname)
                .build();

    }

}
