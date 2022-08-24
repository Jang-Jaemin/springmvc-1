package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * * content-type: application/json
 * */
@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request,
                                  HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", data.getUsername(),
                data.getAge());
        response.getWriter().write("ok");
    }
/**
 * < requestBodyJsonV2 - @RequestBody 문자변환 >
 * @RequestBody
 * HttpMessageConverter 사용 -> StringHttpMessageConverter 적용  *
 * @ResponseBody
 * - 모든 메서드에 @ResponseBody 적용  * - 메시지 바디 정보 직접 반환(view 조회X)
 * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
 * */

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }
    /** < requestBodyJsonV3 - @RequestBody 객체변환 >
     * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content- type: application/json)
     *  */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }
    // < requestBodyJsonV4 - HttpEntity >
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData data = httpEntity.getBody();
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }

    // < requestBodyJsonV5 >
    /* @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content- type: application/json)
     * @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용 (Accept: application/json)
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data;

        //  @ResponseBody
        //  응답의경우에도@ResponseBody를 사용하면 해당 객체를 HTTP 메시지바디에 직접 넣어줄수있다. 물론 이 경우에도 HttpEntity를 사용해도 된다.
        //  @RequestBody 요청
        //  JSON 요청  HTTP 메시지컨버터 객체 @ResponseBody 응답
        //  객체  HTTP 메시지컨버터  JSON 응답
    }
}
//  HttpServletRequest를 사용해서 직접 HTTP 메시지바디에서데이터를읽어와서, 문자로변환한다.
//  문자로된 JSON 데이터를 Jackson 라이브러리인 objectMapper를 사용해서 자바 객체로 변환한다.
//  @RequestBody 객체파라미터
//  @RequestBody HelloData data
//  @RequestBody에 직접 만든 객체를 지정할 수 있다.
//  HttpEntity, @RequestBody를 사용하면 HTTP 메시지컨버터가 HTTP 메시지바디의내용을우리가 원하는문자나객체등으로변환해준다.
//  HTTP 메시지컨버터는문자뿐만아니라 JSON도 객체로 변환해주는데, 우리가방금 V2에서했던작업을 대신처리해준다.
//  자세한 내용은 뒤에 HTTP 메시지컨버터에서 다룬다.

//  < @RequestBody는 생략 불가능 >
//  HelloData에@RequestBody를생략하면@ModelAttribute가 적용되어버린다.
//  HelloData data   @ModelAttribute HelloData data
//  따라서생략하면 HTTP 메시지바디가 아니라 요청파라미터를 처리하게된다.
//  < 주의 >
//   HTTP 요청시에 content-type이 application/json인지꼭! 확인 해야한다.
//   그래야 JSON을처리할수 있는 HTTP 메시지컨버터가실행된다.