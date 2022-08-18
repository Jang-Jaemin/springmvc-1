package hello.springmvc.basic.requestmapping;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";


    }

    /** HTTP 메서드매핑축약
     * 편리한 축약 애노테이션 (코드보기)
     * * @GetMapping
     * @PostMapping * @PutMapping
     * * @DeleteMapping
     * * @PatchMapping
     */

    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
        //HTTP 메서드를 축약한 애노테이션을 사용하는것이 더 직관적이다.
        // 코드를보면내부에서 @RequestMapping과method를 지정해서 사용하는것을 확인 할 수 있다.
    }

    /** 경로 변수 맵핑
     * PathVariable 사용 ( 경로 변수 맵핑 )
     * * 변수명이 같으면 생략 가능
     *
     * @PathVariable("userId") String userId -> @PathVariable userId
     */

    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mappingPath userId={}", data);
        return "ok";
    }

    /**
     * PathVariable 사용 - 다중 사용
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /** 특정 파라미터 조건 맵핑
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {

        log.info("mappingParam");
        return"ok";
        //특정 파라미터가 있거나 없는 조건을 추가할 수 있다. 잘 사용하지는 않는다.
    }

    /**
     * 특정 헤더로 추가 매핑 ( 헤더 헤더 헤더 )
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )  */
    @GetMapping(value = "/mapping-header", headers = "mode=debug") public String mappingHeader() {

        log.info("mappingHeader");
        return "ok";
        // 파라미터 매핑과 비슷하지만, HTTP 헤더를사용한다.
        // Postman으로 테스트 해야한다.
    }

    /** 미디어타입조건매핑 - HTTP 요청 Content-Type, consume
    * Content-Type 헤더 기반 추가 매핑 Media Type  * consumes="application/json"
    *  * consumes="!application/json"  * consumes="application/*"
    *  * consumes="*\/*"
    *  * MediaType.APPLICATION_JSON_VALUE  */


        @PostMapping(value = "/mapping-consume", consumes = "application/json")
        public String mappingConsumes() {

            log.info("mappingConsumes");
            return "ok";

        }

    /** 미디어타입조건매핑 - HTTP 요청 Accept, produce
     * Accept 헤더 기반 Media Type
     * * produces = "text/html"
     * * produces = "!text/html"
     * * produces = "text/*"
     * produces = "*\/*"
     * */

    @PostMapping(value = "/mapping-produce", produces = "text/html") public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
        //  HTTP 요청의 Accept 헤더를 기반으로 미디어 타입으로 매핑한다.
        //  만약 맞지 않으면 HTTP 406 상태코드(Not Acceptable)을 반환한다.
    }
}

//  매핑정보(한번더)
//  @RestController
//  @Controller는 반환 값이 String이면 뷰 이름으로 인식된다. 그래서뷰를찾고뷰가랜더링된다.
//  @RestController는 반환 값으로 뷰를 찾는것이 아니라, HTTP 메시지 바디에 바로 입력한다. 따라서 실행 결과로 ok 메세지를 받을 수 있다.
//  @ResponseBody와 관련이있는데, 뒤에서더자세히 설명한다.

//  @RequestMapping("/hello-basic")
/// hello-basic URL 호출이오면이메서드가실행되도록매핑한다.
//  대부분의속성을배열[]로제공하므로다중설정이가능하다. {"/hello-basic", "/hello-go"}