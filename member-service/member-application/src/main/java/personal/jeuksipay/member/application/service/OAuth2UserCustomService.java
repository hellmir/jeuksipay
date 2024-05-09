package personal.jeuksipay.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import personal.jeuksipay.member.application.port.in.usecase.OAuth2UserCustomUseCase;
import personal.jeuksipay.member.application.port.out.FindMemberPort;
import personal.jeuksipay.member.application.port.out.SignUpPort;
import personal.jeuksipay.member.application.port.out.UpdateMemberPort;
import personal.jeuksipay.member.domain.Member;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService implements OAuth2UserCustomUseCase {
    private final FindMemberPort findMemberPort;
    private final UpdateMemberPort updateMemberPort;
    private final SignUpPort signUpPort;

    private static final String OAUTH_EMAIL_ATTRIBUTE = "email";
    private static final String OAUTH_NAME_ATTRIBUTE = "name";


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        saveOrUpdate(oAuth2User);

        return oAuth2User;
    }

    private void saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String oauthEmail = (String) attributes.get(OAUTH_EMAIL_ATTRIBUTE);
        String oauthName = (String) attributes.get(OAUTH_NAME_ATTRIBUTE);

        try {
            Member member = findMemberPort.findMemberByEmail(oauthEmail);
            member.updateOauthName(oauthName);
            updateMemberPort.updateMember(member);
        } catch (EntityNotFoundException e) {
            Member member = Member.of(oauthEmail, oauthName);
            signUpPort.saveOauthMember(member);
        }
    }
}

