package next.avinor.fmtssm.services;

import next.avinor.fmtssm.domain.Connection;
import next.avinor.fmtssm.domain.ConnectionEvent;
import next.avinor.fmtssm.domain.ConnectionState;
import org.springframework.statemachine.StateMachine;

import java.util.UUID;

public interface ConnectionService {
    Connection init();
    StateMachine<ConnectionState, ConnectionEvent> init(UUID connectionId);
    StateMachine<ConnectionState, ConnectionEvent> setup(UUID connectionId);
}
