package personal.jeuksipay.member.domain.exception.message;

public class InvalidExceptionMessage {
    public static final String INVALID_EMAIL_EXCEPTION = "이메일 주소 형식이 잘못되었습니다. 예: abcd@abc.com";

    public static final String INVALID_USERNAME_EXCEPTION
            = "아이디는 4~16자의 영소문자와 숫자만으로 구성되어야 합니다. 예: tester1";

    public static final String INVALID_PASSWORD_EXCEPTION
            = "비밀번호는 8자 이상이어야 하며, 하나 이상의 대문자와 소문자, 숫자, 특수문자를 포함해야 합니다. 예: Test1234!@";

    public static final String INVALID_FULL_NAME_EXCEPTION = "성명은 2~5자의 한글만 가능합니다. 예: 홍길동";
    public static final String INVALID_PHONE_EXCEPTION = "전화번호 형식이 잘못되었습니다. 예: 01012345678";

    public static final String INVALID_CITY_EXCEPTION = "지역 정보는 2~5자의 한글만 가능합니다. 예: 서울특별시";
    public static final String INVALID_STREET_EXCEPTION = "도로명 주소에는 한글과 숫자만 포함되어야 합니다. 예: 테헤란로 2길 5";
    public static final String INVALID_ZIPCODE_EXCEPTION = "우편번호는 5자리의 숫자로 구성되어야 합니다. 예: 06142";
    public static final String INVALID_DETAILED_ADDRESS_EXCEPTION
            = "상세 주소에는 한글과 숫자만 포함되어야 합니다. 예: 길동아파트 101동 102호";

    public static final String INVALID_ROLE_EXCEPTION = "회원 분류가 유효하지 않습니다. 입력된 회원 분류: ";
}
