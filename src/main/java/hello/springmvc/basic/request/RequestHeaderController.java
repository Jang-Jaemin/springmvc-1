package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


@Slf4j
    @RestController
    public class RequestHeaderController {


    @RequestMapping("/headers")
        public <headerMap, host, response> String headers(
                HttpServletRequest request
                HttpServletResponse response,
                HttpMethod httpMethod,
                Locale locale,
                @RequestHeader MultiValueMap<String, String>
                @RequestHeader("host") String host, headerMap
                @CookieValue(value = "myCookie", required = false){
                    String cookie

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);

        return "ok";
    }
}

//  HttpServletRequest
//  HttpServletResponse
//  HttpMethod: HTTP 메서드를 조회한다. org.springframework.http.HttpMethod Locale: Locale 정보를 조회한다.
//  @RequestHeader MultiValueMap<String, String> headerMap
//  모든 HTTP 헤더를 MultiValueMap 형식으로 조회한다.
//  @RequestHeader("host") String host
//  특정 HTTP 헤더를조회한다. 속성
//  필수값여부: required 기본값속성: defaultValue
//  @CookieValue(value = "myCookie", required = false) String cookie
//  특정쿠키를 조회한다. 속성
//  필수값여부: required 기본값: defaultValue

//  MultiValueMap
//  MAP과유사한데, 하나의 키 에여러 값을 받을수있다.
//  HTTP header, HTTP 쿼리파라미터와 같이 하나의 키에 여러 값을 받을때 사용한다.