package personal.jeuksipay.member.adapter.in.web.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import personal.jeuksipay.member.application.port.in.AuthenticationResult;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class SignInResponse {
    private final List<String> roleDescriptions;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime loggedInAt;

    private final String accessToken;

    public static SignInResponse from(AuthenticationResult authenticationResult) {
        return SignInResponse.builder()
                .roleDescriptions(authenticationResult.getRoleDescriptions())
                .loggedInAt(authenticationResult.getLastLoggedInAt())
                .accessToken(authenticationResult.getAccessToken())
                .build();
    }
}