package groupflow.config;

import groupflow.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginService loginService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.userDetailsService() : DetailsService가 구현된 서비스 대입
        auth.userDetailsService(loginService).passwordEncoder( new BCryptPasswordEncoder());
    }

    // configure : http 보안관련담당메소드
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
/*                .csrf() // 사이트간 요청위조 방지 [post,put] http 불가능
                    .ignoringAntMatchers("http://localhost:3000/login") // login url은 csrf 무시
                    .ignoringAntMatchers("/employee/login") // login url은 csrf 무시*/
                /*.and()*/
                .authorizeHttpRequests()
                    .antMatchers("/offlist").hasRole("DIRECTOR")
                    .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                        .loginPage("/login")    // 로그인페이지로 사용할 url
                        .loginProcessingUrl("/employee/login")   // 로그인 처리할 매핑 url
                        .defaultSuccessUrl("/allemployee") // 로그인성공했을때
                        .failureUrl("/login") // 로그인실패시매핑url
                        .usernameParameter("eno") // 로그인시 사용될 계정 아이디 필드명 -> 직원이름으로 사용
                        .passwordParameter("ename")   // 로그인시 사용될 계정 비밀번호 필드명 -> 사번으로 사용
                .and()
                    .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/employee/logout"))     // 로그아웃처리 요청할 매핑 url
                .logoutSuccessUrl("/login")         // 로그아웃처리 성공시 매핑 url
                .invalidateHttpSession(true);       // 세션초기화X

        http.csrf().disable();
    }

}
