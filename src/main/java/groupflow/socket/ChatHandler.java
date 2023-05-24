package groupflow.socket;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
        if( msgbox.getType().equals("1") ){
            List<String> enos = new ArrayList<>();
            for (WebSocketSession s : sessions) { //sessions 는 모든 접속명단, 1:1 채팅명단을 만들것
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
        }else if( msgbox.getType().equals("2")  ){
            for (WebSocketSession s : sessions) { //sessions 는 모든 접속명단, 1:1 채팅명단을 만들것
                int endindex= s.getUri().getPath().split("/").length-1;
                String eno = s.getUri().getPath().split("/")[endindex];
                // 메시지 안에 받는사람과 같으면  그사람에게 메시지 전송
                if( msgbox.getToeno().equals(eno)  ) {
                    MsgboxDto m = MsgboxDto.builder()
                            .type("2").msg( msgbox.getMsg()  )
                            .build();
                    String json = mapper.writeValueAsString(m);
                        s.sendMessage( new TextMessage(  json ) );
                }
            }
        }



    }
}
