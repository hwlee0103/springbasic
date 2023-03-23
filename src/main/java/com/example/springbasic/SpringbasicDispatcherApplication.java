package com.example.springbasic;

import com.example.controller.HelloServletController;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Spring Container 란?
 * Business Objects (POJO) + Configuration Metadata 를 조합하여
 * 내부의 Bean 이라고 하는 Object 를 구성해서 서버 어플리케이션으로 만들어줌
 */

public class SpringbasicDispatcherApplication {

    public static void main(String[] args) {
        System.out.println("hello container less standard alone");

        // 스프링 컨테이너 생성
        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext() { // java는 맨 앞에 this가 생략되어있음
            @Override
            protected void onRefresh() {
                // 필수 - Application Context 초기화 작업
                super.onRefresh(); // 삭제 금지 - 얘를 가지고 spring이 상위로 타고올라가면서 동작시켜줌

                // Sevlet Container 생성 및 초기화
                ServletWebServerFactory servletWebServerFactory = new TomcatServletWebServerFactory();
                WebServer webServer = servletWebServerFactory.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcherServlet"
                                    , new DispatcherServlet(this))
                            .addMapping("/*"); // 전체 매핑 - 모든 url을 받을 수 있도록
                });

                // tomcat servlet container 실행
                webServer.start();
            }
        };

        // 매핑되는 Bean/객체 를 등록 - class 정보를 주면 등록
        // 스프링 컨테이너 초기화 작업, Template method 패턴으로 구현
        applicationContext.registerBean(HelloServletController.class);
        applicationContext.refresh(); // 등록 시 항상 등록된 정보를 다시 불러들여야함

    }

}
