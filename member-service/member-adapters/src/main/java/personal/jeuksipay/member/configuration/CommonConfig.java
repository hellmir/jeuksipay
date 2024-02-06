package personal.jeuksipay.member.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import personal.jeuksipay.common.application.aop.LoggingAspect;

@Configuration
@Import(LoggingAspect.class)
public class CommonConfig {
}
