package groupflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import groupflow.domain.employee.EmployeeDto;
import groupflow.service.EmployeeService;
import groupflow.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthSuccessFailHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    // ObjectMapper : jackson vs
    // @Autowired // 사용 불가 @Component안들어있기 때문에
    private ObjectMapper mapper = new ObjectMapper();

    //@Autowired EmployeeService employeeService;
    @Autowired LoginService loginService;

    @Override // 인수 : request , response, authentication:인증성공정보
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("authentication success : " + authentication);
        EmployeeDto employeeDto = (EmployeeDto)authentication.getPrincipal();
       String json = mapper.writeValueAsString(employeeDto);

        // ajax 전송
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json"); // ? @ResponseBody 사용 안햇을 때는 직접 작용
        response.getWriter().print(json);
    }

    @Override // 인수 : request , response , exception:예외[인증실패한 예외객체]
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("authentication failure : " + exception);

        String json = mapper.writeValueAsString(false);

        // ajax 전송
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json"); // ? @ResponseBody 사용 안햇을 때는 직접 작용
        response.getWriter().print(json);
    }
}
