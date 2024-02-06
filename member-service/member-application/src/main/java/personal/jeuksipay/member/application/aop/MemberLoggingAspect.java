package personal.jeuksipay.member.application.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import personal.jeuksipay.common.application.aop.MemoryUtil;
import personal.jeuksipay.member.application.port.in.command.SignUpCommand;

import static personal.jeuksipay.common.application.aop.LogMessage.PERFORMANCE_MEASUREMENT;

@Component
@Aspect
public class MemberLoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(MemberLoggingAspect.class);

    private static final String SIGN_UP_POINTCUT
            = "execution(* personal.jeuksipay.member.application.service.*.*(..)) && args(signUpCommand)";
    private static final String SIGN_UP_START
            = "Beginning to '{}.{}' task by email: '{}', username: '{}'";
    private static final String SIGN_UP_END
            = "'{}.{}' task was executed successfully by email: '{}', username: '{}', ";

    @Around(SIGN_UP_POINTCUT)
    public Object logAroundForSignUpCommand(ProceedingJoinPoint joinPoint, SignUpCommand signUpCommand)
            throws Throwable {
        log.info(SIGN_UP_START,
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(), signUpCommand.getEmail(), signUpCommand.getUsername());

        long beforeMemory = MemoryUtil.usedMemory();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object process = joinPoint.proceed();

        stopWatch.stop();
        long memoryUsage = MemoryUtil.usedMemory() - beforeMemory;

        log.info(SIGN_UP_END + PERFORMANCE_MEASUREMENT,
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(), signUpCommand.getEmail(), signUpCommand.getUsername(),
                stopWatch.getTotalTimeMillis(), memoryUsage);

        return process;
    }
}
