package groupflow.domain.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeDto {
    private int nno;
    private String content;

    public NoticeEntity toEntity(){
        return NoticeEntity.builder()
                .nno(this.nno)
                .content(this.content)
                .build();
    }

}
