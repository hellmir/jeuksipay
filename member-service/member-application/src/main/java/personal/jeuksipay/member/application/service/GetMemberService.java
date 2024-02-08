package personal.jeuksipay.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import personal.jeuksipay.common.application.UseCase;
import personal.jeuksipay.member.application.port.in.usecase.GetMemberUseCase;
import personal.jeuksipay.member.application.port.out.FindMemberPort;
import personal.jeuksipay.member.domain.security.CustomUserDetails;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@RequiredArgsConstructor
@UseCase
@Transactional(isolation = READ_COMMITTED, readOnly = true, timeout = 10)
public class GetMemberService implements GetMemberUseCase, UserDetailsService {
    private final FindMemberPort findMemberPort;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return new CustomUserDetails(findMemberPort.findMemberById(Long.parseLong(memberId)));
    }
}
