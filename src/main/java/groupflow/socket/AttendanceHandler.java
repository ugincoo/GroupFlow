package groupflow.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AttendanceHandler extends TextWebSocketHandler {
    private  static List<WebSocketSession> myEmployees=new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{ //출근도장 찍었을때
        log.info("afterConnectionEstablished:"+session);
        myEmployees.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{    //퇴근도장 찍었을때
        myEmployees.remove(session);
    }

}
