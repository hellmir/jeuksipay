package personal.jeuksipay.member.adapter.in.web.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class SignUpResponse {
    private Long memberId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    public static SignUpResponse of(Long id, LocalDateTime createdAt) {
        return new SignUpResponse(id, createdAt);
    }
}
