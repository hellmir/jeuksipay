package personal.jeuksipay.common.application.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import static personal.jeuksipay.common.application.aop.LogMessage.PERFORMANCE_MEASUREMENT;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    private static final String STRING_VALUE_POINTCUT
            = "execution(* personal.jeuksipay..service.*.*(..))  && args(stringValue, ..)";
    private static final String LONG_VALUE_POINTCUT
            = "execution(* personal.jeuksipay..service.*.*(..))  && args(longValue, ..)";

    private static final String START
            = "Beginning to '{}.{}' task by {}: '{}'";
    private static final String END
            = "'{}.{}' task was executed successfully by {}: '{}', ";

    private static final String NO_VALUE_POINTCUT
            = "execution(* personal.jeuksipay..service.*.*())";
    private static final String NO_VALUE_START
            = "Beginning to '{}.{}' task";
    private static final String NO_VALUE_END
            = "'{}.{}' task was executed successfully, ";

    private static final String PAGEABLE_AND_ID_POINTCUT
            = "execution(* personal.jeuksipay..service.*.*(..)) && args(pageable, longValue)";
    private static final String PAGEABLE_AND_ID_START
            = "Beginning to '{}.{}' task by page: '{}', {}: '{}'";
    private static final String PAGEABLE_AND_ID_END
            = "'{}.{}' task was executed successfully by page: '{}', {}: '{}', ";

    @Around(STRING_VALUE_POINTCUT)
    public Object logAroundForStringValue(ProceedingJoinPoint joinPoint, String stringValue) throws Throwable {
        String parameterName = ((CodeSignature) joinPoint.getSignature()).getParameterNames()[0];

        log.info(START,
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(), parameterName, stringValue);

        long beforeMemory = MemoryUtil.usedMemory();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object process = joinPoint.proceed();

        stopWatch.stop();
        long memoryUsage = MemoryUtil.usedMemory() - beforeMemory;

        log.info(END + PERFORMANCE_MEASUREMENT,
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(), parameterName, stringValue,
                stopWatch.getTotalTimeMillis(), memoryUsage);

        return process;
    }

    @Around(LONG_VALUE_POINTCUT)
    public Object logAroundForLongValue(ProceedingJoinPoint joinPoint, Long longValue) throws Throwable {
        String parameterName = ((CodeSignature) joinPoint.getSignature()).getParameterNames()[0];

        log.info(START,
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(), parameterName, longValue);

        long beforeMemory = MemoryUtil.usedMemory();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object process = joinPoint.proceed();

        stopWatch.stop();
        long memoryUsage = MemoryUtil.usedMemory() - beforeMemory;

        log.info(END + PERFORMANCE_MEASUREMENT,
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(), parameterName, longValue,
                stopWatch.getTotalTimeMillis(), memoryUsage);

        return process;
    }

    @Around(NO_VALUE_POINTCUT)
    public Object logAroundForNoValue(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(NO_VALUE_START,
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName());

        long beforeMemory = MemoryUtil.usedMemory();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object process = joinPoint.proceed();

        stopWatch.stop();
        long memoryUsage = MemoryUtil.usedMemory() - beforeMemory;

        log.info(NO_VALUE_END + PERFORMANCE_MEASUREMENT,
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                stopWatch.getTotalTimeMillis(), memoryUsage);

        return process;
    }

    @Around(PAGEABLE_AND_ID_POINTCUT)
    public Object logAroundForPageableAndId(ProceedingJoinPoint joinPoint, Pageable pageable, Long longValue)
            throws Throwable {
        String parameterName = ((CodeSignature) joinPoint.getSignature()).getParameterNames()[1];

        log.info(PAGEABLE_AND_ID_START,
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(), pageable.getPageNumber(), parameterName, longValue);

        long beforeMemory = MemoryUtil.usedMemory();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object process = joinPoint.proceed();

        stopWatch.stop();
        long memoryUsage = MemoryUtil.usedMemory() - beforeMemory;

        log.info(PAGEABLE_AND_ID_END + PERFORMANCE_MEASUREMENT,
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(), pageable.getPageNumber(), parameterName, longValue,
                stopWatch.getTotalTimeMillis(), memoryUsage);

        return process;
    }
}
