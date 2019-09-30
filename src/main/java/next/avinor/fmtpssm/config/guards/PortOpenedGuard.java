package next.avinor.fmtpssm.config.guards;

import lombok.extern.slf4j.Slf4j;
import no.avinor.aims.fmtp.enums.FMTPEvent;
import no.avinor.aims.fmtp.enums.FMTPState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PortOpenedGuard implements Guard<FMTPState, FMTPEvent> {

    @Override
    public boolean evaluate(StateContext<FMTPState, FMTPEvent> context) {
        boolean portOpened = false;
        log.info("PortOpenedGuard result: " + portOpened);
        return portOpened;
    }
}
