package next.avinor.fmtssm.services;

import next.avinor.fmtssm.domain.Connection;
import next.avinor.fmtssm.domain.ConnectionEvent;
import next.avinor.fmtssm.domain.ConnectionState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import static org.junit.jupiter.api.Assertions.*;

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
    void init() {
        Connection connection = connectionService.init();
        assertEquals(ConnectionState.NEW, connection.getState());
        StateMachine<ConnectionState, ConnectionEvent> sm = connectionService.init(connection.getId());
        assertEquals(ConnectionState.IDLE, sm.getState().getId());
    }

    @Test
    void setup() {
        Connection connection = connectionService.init();
        StateMachine<ConnectionState, ConnectionEvent> sm = connectionService.init(connection.getId());
        assertEquals(ConnectionState.IDLE, sm.getState().getId());
        sm = connectionService.setup(connection.getId());
        assertEquals(ConnectionState.CONNECTION_PENDING, sm.getState().getId());
    }
}
