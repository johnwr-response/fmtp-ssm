package next.avinor.fmtpssm.config.actions;

import next.avinor.fmtpssm.domain.ConnectionEvent;
import next.avinor.fmtpssm.domain.ConnectionState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class SomeAction implements Action<ConnectionState, ConnectionEvent> {
    @Override
    public void execute(StateContext<ConnectionState, ConnectionEvent> stateContext) {
        System.out.println("Some action was called!!!");
    }
}
