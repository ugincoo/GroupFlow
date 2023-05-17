package groupflow.controller;

import groupflow.domain.employee.EmployeeDto;
import groupflow.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/login/confirm")
public class LoginController {

    @Autowired
    private LoginService loginService;

    // 로그인정보 반환
    @GetMapping("")
    public EmployeeDto loginInfo(){
        return loginService.loginInfo();
    }



}
