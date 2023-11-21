package petplaza.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration // 스프링 컨테이너에 빈 등록 [@Component]
public class SpringMvcViewConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // super.addViewControllers(registry); // 기존의 view 아키텍쳐 사용 안함
        registry.addViewController("/{spring:\\w+}").setViewName("forward:/");
        registry.addViewController("/**/{spring:\\w+}").setViewName("forward:/"); // spring응 resouce를
        // spring 2.6 이상 [해당 패텅 경로 못찾음]
        registry.addViewController( "/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}").setViewName("forward:/");

    }
}
    /*

        원래는 스프링 MVC 아키텍처에서는 CONTROLLER가 VIEW 반환하는 작업 진행
        - 문제점 : 리액트와 스프링이 통합되었을 때.
            리액트 라우터(Link, get), 스프링(get)
        - 스프링 안에 리액트가 포함되므로 get요청시 스프링 매핑 우선
        - 문제해결 : get 요청시 view 찾을 때 controller가 resources 가서 찾으라고 설정
        
        // 1. 스프링 설정 클래스 생성
        // 2. extends WebMvcConfigureAdapter : MVC 아키텍쳐 설정 커스텀 해주는 클래스
        // 3. 오른쪽클릭 -> 생성 -> 오버라이딩
        // 4. addViewControllers 메소드 오버라이딩 / 재정의 하기


    */


