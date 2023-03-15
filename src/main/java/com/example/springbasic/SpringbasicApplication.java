package com.example.springbasic;

import com.example.controller.HelloController;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * Spring Container 란?
 * Business Objects (POJO) + Configuration Metadata 를 조합하여
 * 내부의 Bean 이라고 하는 Object 를 구성해서 서버 어플리케이션으로 만들어줌
 */

//@SpringBootApplication
public class SpringbasicApplication {

    public static void main(String[] args) {
        System.out.println("hello container less standard alone");

        // Tomcat 서버 생성
        ServletWebServerFactory servletWebServerFactory = new TomcatServletWebServerFactory();
        // servlet container 생성
        WebServer webServer = servletWebServerFactory.getWebServer(servletContext -> {
            // servlet context 정보 초기화 작업
            // hellocontroller 생성
            HelloController helloController = new HelloController();

            // servlet context 에 servlet 추가
            servletContext.addServlet("helloController", new HttpServlet(){
               @Override
               protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
                   // 매핑 : 웹 요청에 들어있는 정보를 활용하여 어떤 로직을 수행하는 코드를 실행할 것인가
                   // 바인딩 : 웹 요청과 응답을 다루는 오브젝트를 사용하지 않음, 웹 요청 정보를 변환하여 사용
                   // - 웹 요청을 가지고 이것을 처리하는 로직 코드에서 사용할 수 있도록 새로운 형태의 타입으로 변환
                   // MVC 바인딩은 더 많은 처리과정이 존재함

                   // HTTP 3가지 요소
                   if(req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
                       String name = req.getParameter("name");
                       String result = helloController.hello(name);

                       // 상태코드는 서블릿 에러가 나지 않는 이상 생략 가능
                       resp.setContentType(MediaType.TEXT_HTML_VALUE);
                       resp.setHeader("Content-Type", "text/plain");
                       resp.getWriter().println(result);

                   } else {
                       resp.setStatus(HttpStatus.NOT_FOUND.value());
                   }
               }
            }).addMapping("/hello"); // Dispatcher Servlet 없이 Servlet에서 바로 url Mapping

            // 전체 url을 받을 수 있는 Dispatcher servlet의 예시
            //  -> 이렇게 바꿀 예정 : SpringbasicDispatcherApplicatoin에서 만듦
            servletContext.addServlet("dispatcherServlet", new HttpServlet(){
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
                    // 매핑 : 웹 요청에 들어있는 정보를 활용하여 어떤 로직을 수행하는 코드를 실행할 것인가
                    // 바인딩 : 웹 요청과 응답을 다루는 오브젝트를 사용하지 않음, 웹 요청 정보를 변환하여 사용
                    // - 웹 요청을 가지고 이것을 처리하는 로직 코드에서 사용할 수 있도록 새로운 형태의 타입으로 변환
                    // MVC 바인딩은 더 많은 처리과정이 존재함

                    // HTTP 3가지 요소
                    if(req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
                        String name = req.getParameter("name");
                        String result = helloController.hello(name);

                        // 상태코드는 서블릿 에러가 나지 않는 이상 생략 가능
                        resp.setContentType(MediaType.TEXT_HTML_VALUE);
                        resp.setHeader("Content-Type", "text/plain");
                        resp.getWriter().println(result);

                    } else {
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }
                }
            }).addMapping("/*");
        });

        webServer.start();
    }

}
