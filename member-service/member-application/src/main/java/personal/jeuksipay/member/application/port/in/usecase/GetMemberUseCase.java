package personal.jeuksipay.member.application.port.in.usecase;

import personal.jeuksipay.member.domain.Member;

public interface GetMemberUseCase {
    Member getMember(Long memberId);
}
