package personal.jeuksipay.member.adapter.in.web.cookie;

import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

public class CookieManager {
    public static void addCookie(HttpServletResponse response, String nameToAdd, String value, int maxAge) {
        Cookie cookie = new Cookie(nameToAdd, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String nameToDelete) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return;
        }

        for (Cookie cookie : cookies) {
            if (isCookieToDelete(nameToDelete, cookie.getName())) {
                deleteTargetCookie(cookie);
                response.addCookie(cookie);
            }
        }
    }

    private static boolean isCookieToDelete(String nameToDelete, String cookieName) {
        return nameToDelete.equals(cookieName);
    }

    private static void deleteTargetCookie(Cookie cookie) {
        cookie.setValue("");
        cookie.setPath("/");
        cookie.setMaxAge(0);
    }

    public static String serialize(Object object) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(object));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue())));
    }
}
