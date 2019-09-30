package next.avinor.fmtpssm.config.actions;

import lombok.extern.slf4j.Slf4j;
import no.avinor.aims.fmtp.enums.FMTPEvent;
import no.avinor.aims.fmtp.enums.FMTPState;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServerActivateAction implements Action<FMTPState, FMTPEvent> {
    private static FMTPEvent event = FMTPEvent.ACTIVATE;
    public static final String SESSION_ID_HEADER = "session_id";
    @Override
    public void execute(StateContext<FMTPState, FMTPEvent> context) {
        log.info("Event : " + event + " triggered");
        log.info("Server mode. Go to WAITING_ACCEPT ");
        context.getStateMachine().sendEvent(MessageBuilder.withPayload(event)
                .setHeader(SESSION_ID_HEADER, context.getMessageHeader(SESSION_ID_HEADER))
                .build());

    }
}
