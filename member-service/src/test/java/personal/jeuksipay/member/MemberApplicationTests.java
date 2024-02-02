package personal.jeuksipay.member;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration(classes = MemberApplication.class)
class MemberApplicationTests {
    @Test
    void contextLoads() {
    }
}
