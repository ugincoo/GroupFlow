package groupflow.domain.attendance;

import groupflow.domain.BaseTime;
import groupflow.domain.employee.EmployeeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data@AllArgsConstructor@NoArgsConstructor@Builder
@Table(name = "attendance")
public class AttendanceEntity extends BaseTime { // 근태테이블 사원fk  출근  퇴근

    //private String CheckinTime;
    //private String CheckoutTime;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int ano;

    @ManyToOne
    @JoinColumn(name="eno")//디비 필드명
    @ToString.Exclude
    private EmployeeEntity employeeEntity;



    public AttendanceDto toDto() {

/*        this.cdate.toLocalDate().format( DateTimeFormatter.ofPattern( "yy-MM-dd"))
        this.udate.toLocalDate().format( DateTimeFormatter.ofPattern( "yy-MM-dd"))*/
        return AttendanceDto.builder()
                .id(this.ano)
                .eno(this.employeeEntity.getEno())
                .cdate(this.cdate.format( DateTimeFormatter.ofPattern( "yy-MM-dd a hh:mm:ss")))
                .udate(this.udate.format( DateTimeFormatter.ofPattern( "yy-MM-dd a hh:mm:ss")))
                .build();
    }
}