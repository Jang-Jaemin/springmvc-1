// 요청매핑 - API 예시

package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

public class MappingClassController {


    @RestController
    @RequestMapping("/mapping/users")
    public class MappingClassController {

        /**
         * produces = "text/plain"
         * produces = {"text/plain", "application/*"}
         * produces = MediaType.TEXT_PLAIN_VALUE
         * produces = "text/plain;charset=UTF-8"
         * HTTP 요청의 Accept 헤더를기반으로미디어타입으로매핑한다. 만약맞지않으면 HTTP 406 상태코드(Not Acceptable)을반환한다.
         * 예시)
         * 요청매핑 - API 예시
         * 회원관리를 HTTP API로만든다생각하고매핑을어떻게하는지알아보자. (실제데이터가넘어가는부분은생략하고 URL 매핑만)
         * 회원관리 API
         * 회원목록조회: GET      /users
         * 회원등록:         POST    /users
         * 회원조회:         GET       /users/{userId}
         * 회원수정:         PATCH  /users/{userId}
         * 회원삭제:         DELETE /users/{userId}
         * MappingClassController     * GET /mapping/users
         */

        @GetMapping
        public String users() {
            return "get users";
        }

        /**
         * POST /mapping/users
         */
        @PostMapping
        public String addUser() {
            return "post user";
        }

        /**
         * GET /mapping/users/{userId}
         */
        @GetMapping("/{userId}")
        public String findUser(@PathVariable String userId) {
            return "get userId=" + userId;
        }

        /**
         * PATCH /mapping/users/{userId}
         */
        @PatchMapping("/{userId}")
        public String updateUser(@PathVariable String userId) {
            return "update userId=" + userId;
        }

        /**
         * DELETE /mapping/users/{userId}
         */
        @DeleteMapping("/{userId}")
        public String deleteUser(@PathVariable String userId) {
            return "delete userId=" + userId;
        }
    }
}
