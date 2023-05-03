package groupflow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Department")
public class DepartmentEntity { // 부서테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int  dno;
    @Column
    private String dname;

    @OneToMany(mappedBy = "departmentEntity")
    private List<DepartmentChangeEntity> departmentChangeEntityList;
}
