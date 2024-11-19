package personal.jeuksipay.member.adapter.in.web.cookie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CookieManagerTest {
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    private static final String NAME = "testName";
    private static final String VALUE = "testValue";
    private static final int MAX_AGE = 3_600;

    private static final Object ORIGINAL_OBJECT = "testObject";

    @DisplayName("쿠키를 추가한다.")
    @Test
    void addCookie() {
        // given
        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);

        // when
        CookieManager.addCookie(response, NAME, VALUE, MAX_AGE);

        // then
        verify(response).addCookie(cookieCaptor.capture());

        assertThat(cookieCaptor.getValue().getName()).isEqualTo(NAME);
        assertThat(cookieCaptor.getValue().getValue()).isEqualTo(VALUE);
        assertThat(cookieCaptor.getValue().getMaxAge()).isEqualTo(MAX_AGE);
    }

    @DisplayName("쿠키를 삭제한다.")
    @Test
    void deleteCookie() {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);

        Cookie existingCookie = new Cookie(NAME, VALUE);
        existingCookie.setMaxAge(MAX_AGE);
        Cookie[] cookies = {existingCookie};

        when(request.getCookies()).thenReturn(cookies);

        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);

        // when
        CookieManager.deleteCookie(request, response, NAME);

        // then
        verify(response).addCookie(cookieCaptor.capture());
        Cookie modifiedCookie = cookieCaptor.getValue();

        assertThat(modifiedCookie.getName()).isEqualTo(NAME);
        assertThat(modifiedCookie.getValue()).isEmpty();
        assertThat(modifiedCookie.getMaxAge()).isZero();
    }

    @DisplayName("쿠키값을 직렬화한다.")
    @Test
    void serialize() {
        // given, when
        String serializedObject = CookieManager.serialize(ORIGINAL_OBJECT);

        // then
        assertThat(serializedObject).isNotEqualTo(ORIGINAL_OBJECT);

        Pattern base64Pattern = Pattern.compile("^[A-Za-z0-9+/]+={0,2}$");
        assertThat(base64Pattern.matcher(serializedObject).matches()).isTrue();
    }

    @DisplayName("쿠키값을 역직렬화한다.")
    @Test
    void deserialize() {
        // given
        String serializedObject = CookieManager.serialize(ORIGINAL_OBJECT);
        Cookie cookie = new Cookie(NAME, serializedObject);

        // when
        String deserializedObject = CookieManager.deserialize(cookie, String.class);

        // then
        assertThat(deserializedObject).isEqualTo(ORIGINAL_OBJECT);
    }
}