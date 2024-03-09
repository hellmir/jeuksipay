package personal.jeuksipay.member.adapter.in.web;

public class ApiConstant {
    public static final String EMAIL_EXAMPLE = "abcd@abc.com";
    public static final String PASSWORD_VALUE = "비밀번호";
    public static final String PASSWORD_CONFIRM_VALUE = "비밀번호 확인";
    public static final String PASSWORD_EXAMPLE = "Abc1!2@34";
    public static final String ACCESS_TOKEN_VALUE = "엑세스 토큰 값";
    public static final String ACCESS_TOKEN_EXAMPLE = "accessToken";

    public static final String PRINCIPAL_POINTCUT
            = "isAuthenticated() and (( #userId == principal.username ) or hasRole('ROLE_ADMIN'))";
}
