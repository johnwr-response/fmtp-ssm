package next.avinor.fmtpssm.config.guards;

import lombok.extern.slf4j.Slf4j;
import next.avinor.fmtpssm.domain.FMTPEvent;
import next.avinor.fmtpssm.domain.FMTPState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientStateOnlyGuard implements Guard<FMTPState, FMTPEvent> {

    @Override
    public boolean evaluate(StateContext<FMTPState, FMTPEvent> context) {
        final boolean isClient = false;
        log.info("ClientStateOnlyGuard result: " + isClient);
        return isClient;
    }
}
