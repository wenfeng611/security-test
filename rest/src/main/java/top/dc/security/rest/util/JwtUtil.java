package top.dc.security.rest.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wenfeng.zhu
 */
@Slf4j
public class JwtUtil {
    public static String encode(String k, String p, int seconds) {

        Key key = Keys.hmacShaKeyFor(k.getBytes(Charset.forName("UTF-8")));

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, seconds);
        Date expireDate = cal.getTime();

        String compact = Jwts.builder()
                .claim("body",p)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expireDate)
                .compact();
        return compact;
    }

    public static String decode(String k, String t) {

        String res = null;

        try {
            Key key = Keys.hmacShaKeyFor(k.getBytes(Charset.forName("UTF-8")));

            Object body = Jwts.parser()
                    .setSigningKey(key)
                    .parse(t)
                    .getBody();
            res = ((Claims) body).get("body", String.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

}
