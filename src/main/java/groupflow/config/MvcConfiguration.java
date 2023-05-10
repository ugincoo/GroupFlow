package groupflow.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration // 컴포넌트 등록
public class MvcConfiguration extends WebMvcConfigurerAdapter {  // 미리 구성된 스프링 Mvc 설정 연결 클래스


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController( "/{spring:\\w+}").setViewName("forward:/");
        registry.addViewController( "/**/{spring:\\w+}").setViewName("forward:/");
        registry.addViewController( "/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}").setViewName("forward:/");

    }
}
