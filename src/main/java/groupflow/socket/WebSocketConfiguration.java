package groupflow.socket;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.websocket.server.PathParam;


@Configuration
@EnableWebSocket
@Slf4j
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Autowired
    AttendanceHandler attendanceHandler;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(attendanceHandler,"/commute/{id}").setAllowedOriginPatterns("*");

    }
}
