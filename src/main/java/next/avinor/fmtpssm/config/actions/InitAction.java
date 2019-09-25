package next.avinor.fmtpssm.config.actions;

import lombok.extern.slf4j.Slf4j;
import next.avinor.fmtpssm.domain.ConnectionEvent;
import next.avinor.fmtpssm.domain.ConnectionState;
import next.avinor.fmtpssm.services.ConnectionServiceImpl;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class InitAction implements Action<ConnectionState, ConnectionEvent> {
    private static ConnectionEvent event = ConnectionEvent.INIT;

    @Override
    public void execute(StateContext<ConnectionState, ConnectionEvent> stateContext) {
        log.info("Event : " + event + " executed");
        stateContext.getStateMachine().sendEvent(MessageBuilder.withPayload(event)
                .setHeader(ConnectionServiceImpl.CONNECTION_ID_HEADER, stateContext.getMessageHeader(ConnectionServiceImpl.CONNECTION_ID_HEADER))
                .build());

/*
        // Code to simulate random connection failures
        if (new Random().nextInt(10) < 8) {
            System.out.println("Connection Initialized");
            stateContext.getStateMachine().sendEvent(MessageBuilder.withPayload(ConnectionEvent.INIT)
                    .setHeader(ConnectionServiceImpl.CONNECTION_ID_HEADER, stateContext.getMessageHeader(ConnectionServiceImpl.CONNECTION_ID_HEADER))
                    .build());

        } else {
            System.out.println("Connection Declined! Stay vigilant!");
            stateContext.getStateMachine().sendEvent(MessageBuilder.withPayload(ConnectionEvent.KILL)
                    .setHeader(ConnectionServiceImpl.CONNECTION_ID_HEADER, stateContext.getMessageHeader(ConnectionServiceImpl.CONNECTION_ID_HEADER))
                    .build());
        }
*/
    }
}
