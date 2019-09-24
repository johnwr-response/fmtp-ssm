package next.avinor.fmtpssm.config;

import next.avinor.fmtpssm.domain.ConnectionEvent;
import next.avinor.fmtpssm.domain.ConnectionState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.UUID;

@SpringBootTest
class StateMachineConfigTest {
    @Autowired
    StateMachineFactory<ConnectionState, ConnectionEvent> factory;
    @Test
    void testNewStateMachine() {
        StateMachine<ConnectionState, ConnectionEvent> sm = factory.getStateMachine(UUID.randomUUID());
        sm.start();
        System.out.println(sm.getState().toString());
        sm.sendEvent(ConnectionEvent.INIT);
        System.out.println(sm.getState().toString());
        sm.sendEvent(ConnectionEvent.LOCAL_SETUP);
        System.out.println(sm.getState().toString());
        sm.sendEvent(ConnectionEvent.DISCONNECT);
        System.out.println(sm.getState().toString());
    }

}
