package next.avinor.fmtpssm.services;

import lombok.RequiredArgsConstructor;
import next.avinor.fmtpssm.domain.Connection;
import next.avinor.fmtpssm.domain.ConnectionEvent;
import next.avinor.fmtpssm.domain.ConnectionState;
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
    public Connection newConnection() {
        return Connection.builder().id(UUID.randomUUID()).state(ConnectionState.NEW).build();
    }

    @Override
    public StateMachine<ConnectionState, ConnectionEvent> init(Connection connection) {
        StateMachine<ConnectionState, ConnectionEvent> sm = build(connection);
        sendEvent(connection, sm, ConnectionEvent.INIT);
        return sm;
    }

    @Override
    public StateMachine<ConnectionState, ConnectionEvent> localSetup(StateMachine<ConnectionState, ConnectionEvent> sm) {
        sm.sendEvent(ConnectionEvent.LOCAL_SETUP);
        return sm;
    }

    @Override
    public StateMachine<ConnectionState, ConnectionEvent> remoteSetup(StateMachine<ConnectionState, ConnectionEvent> sm) {
        sm.sendEvent(ConnectionEvent.REMOTE_SETUP);
        return sm;
    }

    @Override
    public StateMachine<ConnectionState, ConnectionEvent> remoteIdValid(StateMachine<ConnectionState, ConnectionEvent> sm) {
        sm.sendEvent(ConnectionEvent.REMOTE_ID_VALID);
        return sm;
    }

    @Override
    public StateMachine<ConnectionState, ConnectionEvent> localStartup(StateMachine<ConnectionState, ConnectionEvent> sm) {
        sm.sendEvent(ConnectionEvent.LOCAL_STARTUP);
        return sm;
    }

    @Override
    public StateMachine<ConnectionState, ConnectionEvent> remoteStartup(StateMachine<ConnectionState, ConnectionEvent> sm) {
        sm.sendEvent(ConnectionEvent.REMOTE_STARTUP);
        return sm;
    }

    @Override
    public StateMachine<ConnectionState, ConnectionEvent> remoteDisconnect(StateMachine<ConnectionState, ConnectionEvent> sm) {
        sm.sendEvent(ConnectionEvent.DISCONNECT);
        return sm;
    }


    private void sendEvent(Connection connection, StateMachine<ConnectionState, ConnectionEvent> sm, ConnectionEvent event){
        Message msg = MessageBuilder.withPayload(event)
                .setHeader(CONNECTION_ID_HEADER, connection.getId())
                .build();

        sm.sendEvent(msg);
    }
    private StateMachine<ConnectionState, ConnectionEvent> build(Connection connection){
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
