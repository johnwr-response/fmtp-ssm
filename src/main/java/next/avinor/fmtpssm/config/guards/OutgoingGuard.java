package next.avinor.fmtpssm.config.guards;

import lombok.extern.slf4j.Slf4j;
import next.avinor.fmtpssm.domain.ConnectionEvent;
import next.avinor.fmtpssm.domain.ConnectionState;
import next.avinor.fmtpssm.services.ConnectionServiceImpl;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OutgoingGuard implements Guard<ConnectionState, ConnectionEvent> {

    @Override
    public boolean evaluate(StateContext<ConnectionState, ConnectionEvent> stateContext) {
        log.info("Inside OutgoingGuard");
        boolean direction = false;
        return stateContext.getMessageHeader(ConnectionServiceImpl.CONNECTION_ID_HEADER) != null;
    }
}
