package next.avinor.fmtssm.services;

import lombok.RequiredArgsConstructor;
import next.avinor.fmtssm.domain.Connection;
import next.avinor.fmtssm.domain.ConnectionEvent;
import next.avinor.fmtssm.domain.ConnectionState;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConnectionServiceImpl implements ConnectionService {
    public static final String CONNECTION_ID_HEADER = "connection_id";

    private final StateMachineFactory<ConnectionState, ConnectionEvent> stateMachineFactory;
    private final ConnectionStateChangeInterceptor connectionStateChangeInterceptor;

    @Override
    public Connection init() {
        return Connection.builder().id(UUID.randomUUID()).state(ConnectionState.NEW).build();
    }

    @Override
    public StateMachine<ConnectionState, ConnectionEvent> init(UUID connectionId) {
        StateMachine<ConnectionState, ConnectionEvent> sm = build(UUID.randomUUID());
        sendEvent(connectionId, sm, ConnectionEvent.INIT);
        return sm;
    }

    @Override
    public StateMachine<ConnectionState, ConnectionEvent> setup(UUID connectionId) {
        StateMachine<ConnectionState, ConnectionEvent> sm = build(UUID.randomUUID());
        sendEvent(connectionId, sm, ConnectionEvent.SETUP);
        return sm;
    }



    private void sendEvent(UUID connectionId, StateMachine<ConnectionState, ConnectionEvent> sm, ConnectionEvent event){
        Message msg = MessageBuilder.withPayload(event)
                .setHeader(CONNECTION_ID_HEADER, connectionId)
                .build();

        sm.sendEvent(msg);
    }
    private StateMachine<ConnectionState, ConnectionEvent> build(UUID connectionId){
        Connection connection = Connection.builder().id(connectionId).build();
        StateMachine<ConnectionState, ConnectionEvent> sm = stateMachineFactory.getStateMachine(connection.getId());

        sm.stop();

        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(connectionStateChangeInterceptor);
                    sma.resetStateMachine(new DefaultStateMachineContext<>(connection.getState(), null, null, null));
                });

        sm.start();

        return sm;
    }
}
