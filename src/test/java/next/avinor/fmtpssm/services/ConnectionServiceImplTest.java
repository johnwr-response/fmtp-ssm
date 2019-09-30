package next.avinor.fmtpssm.services;

import lombok.extern.slf4j.Slf4j;
import next.avinor.fmtpssm.domain.Connection;
import next.avinor.fmtpssm.domain.ConnectionEvent;
import next.avinor.fmtpssm.domain.ConnectionState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ConnectionServiceImplTest {

    @Autowired
    ConnectionService connectionService;

    @Test
    void initConnection() {
        Connection connection = connectionService.newConnection();
        assertEquals(ConnectionState.DISABLED, connection.getState());
    }
    @Test
    void init() {
        Connection connection = connectionService.newConnection();
        System.out.println("State: " + connection.getState());
        assertEquals(ConnectionState.DISABLED, connection.getState());
        StateMachine<ConnectionState, ConnectionEvent> sm = connectionService.activate(connection);
        System.out.println("State: " + sm.getState().getId());
        assertEquals(ConnectionState.IDLE, sm.getState().getId());
    }

    @Test
    void happyPath() {
        Connection connection = connectionService.newConnection();
        StateMachine<ConnectionState, ConnectionEvent> sm = connectionService.activate(connection);
        log.info("State: " + sm.getState().getId());
        assertEquals(ConnectionState.IDLE, sm.getState().getId());
        connectionService.localSetup(sm);
        log.info("State: " + sm.getState().getId());
        assertEquals(ConnectionState.CONNECTION_PENDING, sm.getState().getId());
        connectionService.remoteSetup(sm);
        log.info("State: " + sm.getState().getId());
        assertEquals(ConnectionState.ID_PENDING, sm.getState().getId());
        connectionService.remoteIdValid(sm);
        log.info("State: " + sm.getState().getId());
        assertEquals(ConnectionState.READY, sm.getState().getId());
        connectionService.localStartup(sm);
        log.info("State: " + sm.getState().getId());
        assertEquals(ConnectionState.ASSOCIATION_PENDING, sm.getState().getId());
        connectionService.remoteStartup(sm);
        log.info("State: " + sm.getState().getId());
        assertEquals(ConnectionState.DATA_READY, sm.getState().getId());
        connectionService.remoteDisconnect(sm);
        log.info("State: " + sm.getState().getId());
        assertEquals(ConnectionState.IDLE, sm.getState().getId());
        connectionService.localKill(sm);
        log.info("State: " + sm.getState().getId());
        assertEquals(ConnectionState.SHUTDOWN, sm.getState().getId());
    }
}
