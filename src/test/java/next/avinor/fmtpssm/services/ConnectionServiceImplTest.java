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

    Connection connection;

    @BeforeEach
    void setUp() {
        connection = Connection.builder().state(ConnectionState.NEW).build();
    }

    @Test
    void initConnection() {
        connection = connectionService.newConnection();
        assertEquals(ConnectionState.NEW, connection.getState());
    }
    @Test
    void init() {
        connection = connectionService.newConnection();
        System.out.println("State: " + connection.getState());
        assertEquals(ConnectionState.NEW, connection.getState());
        StateMachine<ConnectionState, ConnectionEvent> sm = connectionService.init(connection);
        System.out.println("State: " + sm.getState().getId());
        assertEquals(ConnectionState.IDLE, sm.getState().getId());
    }

    @Test
    void setup() {
        connection = connectionService.newConnection();
        StateMachine<ConnectionState, ConnectionEvent> sm = connectionService.init(connection);
        System.out.println("State: " + sm.getState().getId());
        assertEquals(ConnectionState.IDLE, sm.getState().getId());
        connectionService.localSetup(sm);
        System.out.println("State: " + sm.getState().getId());
        assertEquals(ConnectionState.CONNECTION_PENDING, sm.getState().getId());
    }
}
