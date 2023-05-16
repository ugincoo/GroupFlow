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

    private  int ano;


  public AttendanceEntity attendanceEntity(){
        return AttendanceEntity.builder()
                .ano(this.ano)

                .build();


    }
}


