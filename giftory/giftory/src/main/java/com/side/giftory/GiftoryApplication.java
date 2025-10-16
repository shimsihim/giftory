package com.side.giftory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 엔티티 라이프사이클 이벤트 감지 → @CreatedDate/@LastModifiedDate 자동 적용
public class GiftoryApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(GiftoryApplication.class, args);

//        Spring Framework 6.1부터는 DispatcherServlet이 기본적으로 NoHandlerFoundException을 발생시키도록 설정되지만
//        이전 버전의 경우 기본 404 처리 대신, 예외 기반으로 커스텀 처리를 하려면 아래 설정 필요
//        DispatcherServlet dispatcherServlet = (DispatcherServlet) ctx.getBean("dispatcherServlet");
//        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }

    @Override // war 배포 설정 , extends SpringBootServletInitializer도 포함
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GiftoryApplication.class);
    }

}
