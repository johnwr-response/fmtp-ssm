package next.avinor.fmtssm.config.guards;

import next.avinor.fmtssm.domain.ConnectionEvent;
import next.avinor.fmtssm.domain.ConnectionState;
import next.avinor.fmtssm.services.ConnectionServiceImpl;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Component
public class SetupGuard implements Guard<ConnectionState, ConnectionEvent> {
    @Override
    public boolean evaluate(StateContext<ConnectionState, ConnectionEvent> stateContext) {
        return stateContext.getMessageHeader(ConnectionServiceImpl.CONNECTION_ID_HEADER) != null;
    }
}
