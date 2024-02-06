package personal.jeuksipay.common.application.aop;

public class MemoryUtil {
    public static long usedMemory() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }
}
