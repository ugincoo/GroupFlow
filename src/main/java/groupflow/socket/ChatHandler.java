package groupflow.socket;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
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
        log.info("ss:"+sessions);
        log.info("uri:"+session.getUri().getPath());
        log.info("uri:"+session.getUri().getPath().split("/"));
        int endindex= session.getUri().getPath().split("/").length-1;
        log.info("uri:"+session.getUri().getPath().split("/")[endindex]);
        String eno = session.getUri().getPath().split("/")[endindex];
        log.info("eno:"+eno);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(eno);
        TextMessage message= new TextMessage(json);

        handleTextMessage(session, message);
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

        List<String> enos = new ArrayList<>();

        for (WebSocketSession s : sessions) { //sessions 는 모든 접속명단, 1:1 채팅명단을 만들것
            int endindex= s.getUri().getPath().split("/").length-1;
            String eno = s.getUri().getPath().split("/")[endindex];
            enos.add(eno);
        }


        ObjectMapper mapper = new ObjectMapper();

        for (WebSocketSession key : sessions) { //sessions 는 모든 접속명단, 1:1 채팅명단을 만들것

            String json = mapper.writeValueAsString(enos);
            key.sendMessage( new TextMessage( json ) );

        }

    }
}
