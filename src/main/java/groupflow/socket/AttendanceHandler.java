package groupflow.socket;

import groupflow.domain.employee.EmployeeDto;
import groupflow.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class AttendanceHandler extends TextWebSocketHandler {
    private  static Map<WebSocketSession,String> myEmployees=new HashMap<>();




    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{ //출근도장 찍었을때

        log.info("uri:"+session.getUri().getPath());
        log.info("uri:"+session.getUri().getPath().split("/"));
        int endindex= session.getUri().getPath().split("/").length-1;
        log.info("uri:"+session.getUri().getPath().split("/")[endindex]);
        String eno = session.getUri().getPath().split("/")[endindex];
        log.info("eno:"+eno);
        myEmployees.put(session,eno);
        log.info("myEmployees:"+myEmployees);

        TextMessage message = new TextMessage("enter");
        handleTextMessage(session, message);

    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{    //퇴근도장 찍었을때
        myEmployees.remove(session);
        log.info("myEmployees:"+myEmployees);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 접속 명단 만들기
        log.info("msgenter");
        List<String> enolist = new ArrayList<>();
        for(WebSocketSession key : myEmployees.keySet()){
            System.out.println("key : " + key);
            System.out.println("value : " + myEmployees.get(key));
            enolist.add(myEmployees.get(key));
        }
        //메세지보내기
        for(WebSocketSession key : myEmployees.keySet()){
            key.sendMessage(new TextMessage(enolist.toString()));
        }
    }
}
