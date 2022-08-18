package hello.springmvc.basic;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogTestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";

        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info(" info log={}", name);
        log.warn(" warn log={}", name);
        log.error("error log={}", name);

        //로그를 사용하지 않아도 a+b 계산 로직이 먼저 실행됨, 이런 방식으로 사용하면 X
        log.debug("String concat log=" + name);

        return "ok";
    }

}

//  간단한 로깅 알아보기
//  앞으로 로그를 사용할것이기 때문에, 이번 시간에는 로그에 대해서 간단히 알아보자.

//  운영시스템에서는 System.out.println() 같은 시스템 콘솔을 사용해서 필요한 정보를 출력하지않고, 별도의 로깅 라이브러리를 사용해서 로그를 출력한다.
//  참고로 로그 관련라이브러리도 많고, 깊게 들어가면 끝이없기 때문에, 여기서는 최소한의 사용방법만 알아본다.

//  로깅 라이브러리
//  스프링부트라이브러리를 사용하면 스프링부트로 깅라이브러리( spring-boot-starter-logging)가 함께 포함된다.
//  스프링부트로깅라이브러리는 기본으로 다음로깅라이브러리를 사용한다.

//  로그라이브러리는 Logback, Log4J, Log4J2 등등 수 많은 라이브러리가 있는데, 그것을 통합해서 인터페이스로 제공하는것이 바로 SLF4J 라이브러리다.
//  쉽게이야기해서 SLF4J는인터페이스이고, 그구현체로 Logback 같은로그라이브러리를선택하면된다.
//  실무에서는 스프링부트가 기본으로 제공하는 Logback을 대부분 사용한다.

//  로그선언
//  private Logger log = LoggerFactory.getLogger(getClass());
//  private static final Logger log = LoggerFactory.getLogger(Xxx.class) @Slf4j: 롬복사용가능

//  로그호출
//  log.info("hello")
//  System.out.println("hello")
//  시스템콘솔로직접출력하는것보다로그를사용하면다음과같은장점이있다. 실무에서는항상로그를 사용해야한다.

//  매핑정보
//  @RestController
//  @Controller는 반환값이 String이면 뷰 이름으로 인식된다.
//  그래서뷰를찾고뷰가랜더링된다.
//  @RestController는 반환 값으로 뷰를 찾는것이 아니라, HTTP 메시지 바디에 바로 입력한다.
//  따라서실행결과로 ok 메세지를받을수있다. @ResponseBody와 관련이있는다.

//  테스트
//  로그가출력되는포멧확인
//  시간, 로그레벨, 프로세스 ID, 쓰레드명, 클래스명, 로그메시지 로그레벨설정을변경해서출력결과를보자.
//  LEVEL: TRACE > DEBUG > INFO > WARN > ERROR 개발서버는 debug 출력
//  운영서버는 info 출력 @Slf4j로 변경

//  올바른로그사용법
//  log.debug("data="+data)
//  로그출력레벨을 info로 설정해도 해당코드에 있는 "data=" + data가 실제 실행 이되어버린다. 결과적으로문자더하기연산이발생한다.

//  log.debug("data={}", data)
//  로그출력레벨을 info로 설정하면 아무 일도 발생하지 않는다. 따라서 앞과 같은 의미없는 연산이 발생하지 않는다.


