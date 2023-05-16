package groupflow.domain.attendance;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceDto {

    private  int id;
    private String cdate;
    private String udate;
    private int eno;
  public AttendanceEntity attendanceEntity(){
        return AttendanceEntity.builder()
                .ano(this.id)

                .build();


    }
}


