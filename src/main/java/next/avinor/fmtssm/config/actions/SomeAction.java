package next.avinor.fmtssm.config.actions;

import next.avinor.fmtssm.domain.ConnectionEvent;
import next.avinor.fmtssm.domain.ConnectionState;
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
