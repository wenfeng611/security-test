package top.dc.security.rest.config;


import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import top.dc.security.rest.controller.BaseController;
import top.dc.security.rest.model.out.WebResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wenfeng.zhu
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResult> global(Exception e) {

        if (e instanceof MethodArgumentNotValidException) {

            List<String> msgs = new ArrayList<>();

            ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().forEach(
                    x -> msgs.add(x.getDefaultMessage())
            );

            return BaseController.invalid(Joiner.on(",").join(msgs));
        }

        if (e instanceof AccessDeniedException) {
            return BaseController.forbidden();
        }
        log.error(Throwables.getStackTraceAsString(e));
        return BaseController.error("server error");
    }
}
