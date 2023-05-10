package groupflow.domain.department;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentChangeDto {

    private int dcno; // 식별번호
    private String dcstartdate; // 적용날짜
    private String dcenddate; // 끝날짜
    private String dcstartreason; // 부서변경사유
    private int dno ; // 부서테이블FK
    private int eno ; // 사원테이블FK

    public DepartmentChangeEntity toEntity() {
        return DepartmentChangeEntity.builder()
                .dcno(this.dcno)
                .dcstartdate(this.toEntity().getDcstartdate())
                .dcenddate(this.toEntity().getDcenddate())
                .dcstartreason(this.dcstartreason)
                .build();
    }
}
