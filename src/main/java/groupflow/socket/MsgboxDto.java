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
    private String toeno; // 받는사람의 eno
    private String fromeno; // 보내는사람의 eno
    private String fromename; //받는사람이 답장이 올때 이름을 뽑아내야하기위해


    private String fromdname; //받는사람이 답장이 올때 부서을 뽑아내야하기위해
    private String frompname; //받는사람이 답장이 올때 직급를 뽑아내야하기위해
    private List<String> enos; // tyep1 일때 서버가 클라이언트들에게 보내는 접속명단 ;


}
