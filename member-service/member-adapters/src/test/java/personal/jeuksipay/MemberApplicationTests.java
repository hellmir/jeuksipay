package personal.jeuksipay;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import personal.jeuksipay.member.MemberApplication;

@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration(classes = MemberApplication.class)
class MemberApplicationTests {
    @Test
    void contextLoads() {
    }
}
