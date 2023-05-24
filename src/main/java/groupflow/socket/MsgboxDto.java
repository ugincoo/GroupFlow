package groupflow.socket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsgboxDto {

    private String type; // 1 : 상태메시지 2:메시지
    private String msg; // 메시지
    private String toeno; // 받는사람
    private String fromeno; // 보내는사람
    private List<String> enos; // tyep1 일때 서버가 클라이언트들에게 보내는 접속명단 ;


}
