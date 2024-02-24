package personal.jeuksipay.member.application.port.in.usecase;

import personal.jeuksipay.member.application.port.in.command.AddressCommand;
import personal.jeuksipay.member.application.port.in.command.EmailUpdateCommand;
import personal.jeuksipay.member.application.port.in.command.PasswordUpdateCommand;
import personal.jeuksipay.member.application.port.in.command.PhoneUpdateCommand;

public interface UpdateMemberUseCase {
    void updateAddress(AddressCommand addressCommand);

    void updateEmail(EmailUpdateCommand emailUpdateCommand);

    void updatePhone(PhoneUpdateCommand phoneUpdateCommand);

    void updatePassword(PasswordUpdateCommand passwordUpdateCommand);
}
