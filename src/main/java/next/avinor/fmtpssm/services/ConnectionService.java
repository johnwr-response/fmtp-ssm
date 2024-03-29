package next.avinor.fmtpssm.services;

import next.avinor.fmtpssm.domain.Connection;
import next.avinor.fmtpssm.domain.ConnectionEvent;
import next.avinor.fmtpssm.domain.ConnectionState;
import org.springframework.statemachine.StateMachine;

import java.util.UUID;

public interface ConnectionService {
    Connection newConnection();
    StateMachine<ConnectionState, ConnectionEvent> activate(Connection connection);
    StateMachine<ConnectionState, ConnectionEvent> localSetup(StateMachine<ConnectionState, ConnectionEvent> sm);
    StateMachine<ConnectionState, ConnectionEvent> remoteSetup(StateMachine<ConnectionState, ConnectionEvent> sm);
    StateMachine<ConnectionState, ConnectionEvent> remoteIdValid(StateMachine<ConnectionState, ConnectionEvent> sm);
    StateMachine<ConnectionState, ConnectionEvent> localStartup(StateMachine<ConnectionState, ConnectionEvent> sm);
    StateMachine<ConnectionState, ConnectionEvent> remoteStartup(StateMachine<ConnectionState, ConnectionEvent> sm);
    StateMachine<ConnectionState, ConnectionEvent> remoteDisconnect(StateMachine<ConnectionState, ConnectionEvent> sm);
    StateMachine<ConnectionState, ConnectionEvent> localKill(StateMachine<ConnectionState, ConnectionEvent> sm);
}
