package personal.jeuksipay.member.application.port.out;

import personal.jeuksipay.member.domain.Member;

public interface SignUpPort {
    void saveMember(Member member);
}
