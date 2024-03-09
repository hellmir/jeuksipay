package personal.jeuksipay.member.application.port.out;

import personal.jeuksipay.member.domain.Member;

public interface FindMemberPort {
    Member findMemberById(Long memberId);

    Member findMemberByEmailOrUsername(String emailOrUsername);

    void checkDuplicateUsername(String username);

    void checkDuplicateEmail(String email);

    void checkDuplicatePhone(String phone);
}
