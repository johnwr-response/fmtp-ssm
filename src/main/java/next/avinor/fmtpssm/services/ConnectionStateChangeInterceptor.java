package next.avinor.fmtpssm.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import next.avinor.fmtpssm.domain.ConnectionEvent;
import next.avinor.fmtpssm.domain.ConnectionState;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class ConnectionStateChangeInterceptor extends StateMachineInterceptorAdapter<ConnectionState, ConnectionEvent> {
    @Override
    public void preStateChange(State<ConnectionState, ConnectionEvent> state, Message<ConnectionEvent> message,
                               Transition<ConnectionState, ConnectionEvent> transition, StateMachine<ConnectionState, ConnectionEvent> stateMachine) {

        Optional.ofNullable(message).ifPresent(msg -> {
            Optional.ofNullable(UUID.class.cast(msg.getHeaders().getOrDefault(ConnectionServiceImpl.CONNECTION_ID_HEADER, UUID.randomUUID())))
                    .ifPresent(connectionId -> {
                        log.debug("Set something here...?");
                    });
        });
    }
}
