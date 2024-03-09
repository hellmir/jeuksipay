package personal.jeuksipay.member.adapter.in.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import personal.jeuksipay.common.excpeption.ErrorResponse;
import personal.jeuksipay.common.excpeption.LogMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException {
        ErrorResponse errorResponse;

        if (e instanceof UsernameNotFoundException) {
            log.warn(LogMessage.LOG_WARN_MESSAGE, e.getMessage(), e);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            errorResponse = ErrorResponse.builder()
                    .statusCode(HttpServletResponse.SC_UNAUTHORIZED)
                    .message(e.getMessage())
                    .build();
        } else {
            log.error(LogMessage.LOG_ERROR_MESSAGE, e.getMessage(), e);

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            errorResponse = ErrorResponse.builder()
                    .statusCode(HttpServletResponse.SC_FORBIDDEN)
                    .message(e.getMessage())
                    .build();
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        response.flushBuffer();
    }
}
