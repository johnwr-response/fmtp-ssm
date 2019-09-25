package next.avinor.fmtpssm.config.actions;

import lombok.extern.slf4j.Slf4j;
import next.avinor.fmtpssm.domain.ConnectionEvent;
import next.avinor.fmtpssm.domain.ConnectionState;
import next.avinor.fmtpssm.services.ConnectionServiceImpl;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DisconnectAction implements Action<ConnectionState, ConnectionEvent> {
    private static ConnectionEvent event = ConnectionEvent.DISCONNECT;

    @Override
    public void execute(StateContext<ConnectionState, ConnectionEvent> stateContext) {
        log.info("Event : " + event + " executed");
        stateContext.getStateMachine().sendEvent(
                MessageBuilder.withPayload(event)
                        .setHeader(ConnectionServiceImpl.CONNECTION_ID_HEADER, stateContext.getMessageHeader(ConnectionServiceImpl.CONNECTION_ID_HEADER))
                        .build()
        );
    }
}
