package next.avinor.fmtpssm.config.actions;

import next.avinor.fmtpssm.domain.ConnectionEvent;
import next.avinor.fmtpssm.domain.ConnectionState;
import next.avinor.fmtpssm.services.ConnectionServiceImpl;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class RemoteIdValidAction implements Action<ConnectionState, ConnectionEvent> {
    @Override
    public void execute(StateContext<ConnectionState, ConnectionEvent> stateContext) {
        System.out.println("Remote ID(valid) was called!!!");
        stateContext.getStateMachine().sendEvent(MessageBuilder.withPayload(ConnectionEvent.REMOTE_ID_VALID)
                .setHeader(ConnectionServiceImpl.CONNECTION_ID_HEADER, stateContext.getMessageHeader(ConnectionServiceImpl.CONNECTION_ID_HEADER))
                .build());
    }
}
