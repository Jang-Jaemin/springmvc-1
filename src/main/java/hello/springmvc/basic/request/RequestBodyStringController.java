package hello.springmvc.basic.request;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        response.getWriter().write("ok");
    }

    /**
     * Input, Output 스트림, Reader - requestBodyStringV2
     * InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
     * * OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    /** HttpEntity - requestBodyStringV3
     * HttpEntity: HTTP header, body 정보를 편리하게 조회
     * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     * <p>
     * 응답에서도 HttpEntity 사용 가능
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);
        return new HttpEntity<>("ok");
    }
}
//  HTTP 요청메시지 - 단순텍스트
//  서블릿에서 학습한 내용을 떠올려보자.
//  HTTP message body에 데이터를 직접 담아서 요청 HTTP API에서 주로 사용, JSON, XML, TEXT 데이터형식은주로 JSON 사용
//  POST, PUT, PATCH
//  요청 파라미터와 다르게, HTTP 메시지 바디를 통해 데이터가 직접 넘어오는 경우는@RequestParam, @ModelAttribute를 사용할 수 없다. (물론 HTML Form 형식으로전달되는경우는요청파라미터로 인정된다.)
//  먼저가 장단순한 텍스트메시지를 HTTP 메시지바디에 담아서 전송하고, 읽어보자. HTTP 메시지 바디의 데이터를InputStream을 사용해서 직접 읽을수 있다.

//  스프링 MVC는 다음 파라미터를 지원한다. HttpEntity: HTTP header, body 정보를 편리하게 조회
//  메시지바디 정보를 직접조회
//  요청 파라미터를 조회하는 기능과 관계없음 @RequestParam X, @ModelAttribute X HttpEntity는 응답에도 사용가능
//  메시지 바디정보 직접반환 헤더 정보 포함가능 view 조회X
//  HttpEntity를 상속받은 다음 객체들도 같은 기능 을제공한다. RequestEntity
//  HttpMethod, url 정보가추가, 요청에서 사용 ResponseEntity
//  HTTP 상태코드 설정가능, 응답에서 사용
//  return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED

// 기타
// 스프링MVC 내부에서 HTTP 메시지바디를읽어서문자나객체로변환해서전달해주는데,
// 이때 HTTP 메시지컨버터( HttpMessageConverter)라는기능을사용한다.
