package personal.jeuksipay.member.domain.wrapper;

public class WrapperAccessor {
    public static String getEmailValue(Email email) {
        return email.getValue();
    }

    public static String getFullNameValue(FullName fullName) {
        return fullName.getValue();
    }
}
