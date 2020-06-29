package top.dc.security.rest.model.out;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * rest 请求返回外层结构
 *
 * @author wenfeng.zhu
 */
@Data
@Accessors(chain = true)
public class WebResult implements Serializable {

    private static final int SUCC = 200;
    private static final int INVALID = 400;
    private static final int LOGIN = 401;
    private static final int FORBIDDEN = 403;

    private static final int ERROR = 500;

    private int code;
    private String msg;
    private Object data;

    public static WebResult ok(String msg, Object data) {
        return new WebResult()
                .setCode(SUCC)
                .setMsg(msg)
                .setData(data);
    }

    public static WebResult invalid(String msg) {
        return new WebResult()
                .setCode(INVALID)
                .setMsg(msg);
    }

    public static WebResult error(String msg) {
        return new WebResult()
                .setCode(ERROR)
                .setMsg(msg);
    }

    public static WebResult login() {
        return new WebResult()
                .setCode(LOGIN)
                .setMsg("Please login.");
    }

    public static WebResult forbidden() {
        return new WebResult()
                .setCode(FORBIDDEN)
                .setMsg("You're not allowed to access the data.");
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
