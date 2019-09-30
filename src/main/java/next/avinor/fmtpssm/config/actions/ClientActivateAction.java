package next.avinor.fmtpssm.config.actions;

import lombok.extern.slf4j.Slf4j;
import next.avinor.fmtpssm.domain.FMTPEvent;
import next.avinor.fmtpssm.domain.FMTPState;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientActivateAction implements Action<FMTPState, FMTPEvent> {
    private static FMTPEvent event = FMTPEvent.ACTIVATE;
    public static final String SESSION_ID_HEADER = "session_id";
    @Override
    public void execute(StateContext<FMTPState, FMTPEvent> context) {
        log.info("Event : " + event + " triggered");
        log.info("Client mode. No further actions");
        context.getStateMachine().sendEvent(MessageBuilder.withPayload(event)
                .setHeader(SESSION_ID_HEADER, context.getMessageHeader(SESSION_ID_HEADER))
                .build());

    }
}
