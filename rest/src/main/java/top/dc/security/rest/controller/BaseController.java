package top.dc.security.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import top.dc.security.rest.model.out.WebResult;

/**
 * @author wenfeng.zhu
 */
public abstract class BaseController {
    public static ResponseEntity<WebResult> ok(String msg, Object data) {
        return ResponseEntity.ok(WebResult.ok(msg, data));
    }

    public static ResponseEntity<WebResult> invalid(String msg) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(WebResult.invalid(msg));
    }

    public static ResponseEntity<WebResult> login() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(WebResult.login());
    }

    public static ResponseEntity<WebResult> forbidden() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(WebResult.forbidden());
    }

    public static ResponseEntity<WebResult> error(String msg) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(WebResult.error(msg));
    }
}
