package groupflow.socket;


import com.fasterxml.jackson.databind.ObjectMapper;
import groupflow.domain.employee.EmployeeDto;
import groupflow.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component@Slf4j
public class ChatHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> sessions = new ArrayList<>();




    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{    //퇴근도장 찍었을때
        sessions.remove(session);
        log.info("myEmployees:"+sessions);

        /*ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString("enter");
        TextMessage message = new TextMessage(json);
        handleTextMessage(session, message);*/
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
       // log.info("msg"+message.getPayload()); //회원번호 일단 만들어는놈..
        ObjectMapper mapper = new ObjectMapper();
            log.info(  message.getPayload() );
        MsgboxDto msgbox = mapper.readValue(message.getPayload(), MsgboxDto.class);
            log.info(  "msgbox : " + msgbox );
        if( msgbox.getType().equals("1") ){//----------type1
            List<String> enos = new ArrayList<>();
            for (WebSocketSession s : sessions) { //sessions 은 chat.js 에 접속한 모든 직원
                int endindex= s.getUri().getPath().split("/").length-1;
                String eno = s.getUri().getPath().split("/")[endindex];
                enos.add(eno);
            }
            for (WebSocketSession key : sessions) { //sessions 는 모든 접속명단, 1:1 채팅명단을 만들것
                MsgboxDto m = MsgboxDto.builder()
                        .type("1").enos(enos)
                        .build();
                String json = mapper.writeValueAsString(m);
                key.sendMessage( new TextMessage( json ) );
            }
        }else if( msgbox.getType().equals("2")  ){//----------type2
          
            for (WebSocketSession s : sessions) { //sessions 은 chat.js 에 접속한 모든 직원

                int endindex= s.getUri().getPath().split("/").length-1;
                String eno = s.getUri().getPath().split("/")[endindex];
                // 메시지 안에 받는사람과 같으면  그사람에게 메시지 전송
                //참고로 msgbox는 js에서 넘어온 json타입의  메세지,타입,toeno,fromeno 을 java 타입으로 바꾼애다
                if( msgbox.getToeno().equals(eno) ||msgbox.getFromeno().equals(eno) ) {
                    log.info("접속은됐는데..");
                    int fromEno= Integer.parseInt(msgbox.getFromeno()) ;

                    MsgboxDto m = MsgboxDto.builder()
                            .type("2")
                            .msg( msgbox.getMsg()  )
                            .toeno(msgbox.getToeno())
                            .fromeno(msgbox.getFromeno())
                            .fromename(msgbox.getFromename())
                            .fromdname(msgbox.getFromdname())
                            .frompname(msgbox.getFrompname())
                            .build();
                    String json = mapper.writeValueAsString(m); //java에서 사용하기위해 타입변환했다가 다시 보내줄떈
                        s.sendMessage( new TextMessage(  json ) );
                }
            }
        }



    }
}
