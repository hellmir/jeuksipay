package personal.jeuksipay.member.application.port.in.usecase;

import personal.jeuksipay.member.application.port.in.command.AddressCommand;
import personal.jeuksipay.member.application.port.in.command.EmailUpdateCommand;

public interface UpdateMemberUseCase {
    void updateAddress(AddressCommand addressCommand);

    void updateEmail(EmailUpdateCommand emailUpdateCommand);
}
