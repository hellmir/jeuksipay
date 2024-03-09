package personal.jeuksipay.member.domain.wrapper;

public class WrapperAccessor {
    public static String getEmailValue(Email email) {
        return email.getValue();
    }

    public static String getFullNameValue(FullName fullName) {
        return fullName.getValue();
    }

    public static String getUsernameValue(Username username) {
        return username.getValue();
    }

    public static String getPhoneValue(Phone phone) {
        return phone.getValue();
    }
}
