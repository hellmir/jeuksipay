package personal.jeuksipay.member.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import personal.jeuksipay.common.application.aop.LoggingAspect;
import personal.jeuksipay.common.excpeption.GlobalExceptionHandler;

@Configuration
@Import({LoggingAspect.class, GlobalExceptionHandler.class})
public class CommonConfig {
}
