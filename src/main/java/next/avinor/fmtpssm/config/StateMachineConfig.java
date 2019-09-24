package next.avinor.fmtpssm.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import next.avinor.fmtpssm.domain.ConnectionEvent;
import next.avinor.fmtpssm.domain.ConnectionState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Slf4j
@RequiredArgsConstructor
@EnableStateMachineFactory
@Configuration
public class StateMachineConfig  extends StateMachineConfigurerAdapter<ConnectionState, ConnectionEvent> {

    private final Action<ConnectionState, ConnectionEvent> initAction;
    private final Action<ConnectionState, ConnectionEvent> killAction;
    private final Action<ConnectionState, ConnectionEvent> localSetupAction;
    private final Action<ConnectionState, ConnectionEvent> remoteSetupAction;
    private final Action<ConnectionState, ConnectionEvent> disconnectAction;
    private final Action<ConnectionState, ConnectionEvent> idValidAction;
    private final Action<ConnectionState, ConnectionEvent> idInvalidAction;
    private final Action<ConnectionState, ConnectionEvent> someAction;

    private final Guard<ConnectionState, ConnectionEvent> setupGuard;

    @Override
    public void configure(StateMachineStateConfigurer<ConnectionState, ConnectionEvent> states) throws Exception {
        states.withStates()
                .initial(ConnectionState.NEW)
                .states(EnumSet.allOf(ConnectionState.class))
                .end(ConnectionState.SHUTDOWN);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ConnectionState, ConnectionEvent> transitions) throws Exception {
        transitions
                .withExternal().source(ConnectionState.NEW).target(ConnectionState.IDLE).event(ConnectionEvent.INIT)
                    .action(initAction)
                    .guard(setupGuard)
                .and().withExternal().source(ConnectionState.IDLE).target(ConnectionState.SHUTDOWN).event(ConnectionEvent.KILL)
                    .action(killAction)
                .and().withExternal().source(ConnectionState.IDLE).target(ConnectionState.CONNECTION_PENDING).event(ConnectionEvent.LOCAL_SETUP)
                    .action(localSetupAction)
                .and().withExternal().source(ConnectionState.CONNECTION_PENDING).target(ConnectionState.ID_PENDING).event(ConnectionEvent.REMOTE_SETUP)
                    .action(remoteSetupAction)
                .and().withExternal().source(ConnectionState.CONNECTION_PENDING).target(ConnectionState.IDLE).event(ConnectionEvent.DISCONNECT)
                    .action(disconnectAction)
                .and().withExternal().source(ConnectionState.ID_PENDING).target(ConnectionState.READY).event(ConnectionEvent.ID_VALID)
                    .action(idValidAction)
                .and().withExternal().source(ConnectionState.ID_PENDING).target(ConnectionState.IDLE).event(ConnectionEvent.ID_INVALID)
                    .action(idInvalidAction)
                .and().withExternal().source(ConnectionState.READY).target(ConnectionState.ASSOCIATION_PENDING).event(ConnectionEvent.STARTUP)
                    .action(someAction)
                .and().withExternal().source(ConnectionState.READY).target(ConnectionState.IDLE).event(ConnectionEvent.DISCONNECT)
                    .action(someAction)
                .and().withExternal().source(ConnectionState.ASSOCIATION_PENDING).target(ConnectionState.DATA_READY).event(ConnectionEvent.STARTUP)
                    .action(someAction)
                .and().withExternal().source(ConnectionState.ASSOCIATION_PENDING).target(ConnectionState.IDLE).event(ConnectionEvent.DISCONNECT)
                    .action(someAction)
                .and().withExternal().source(ConnectionState.ASSOCIATION_PENDING).target(ConnectionState.READY).event(ConnectionEvent.SHUTDOWN)
                    .action(someAction)
                .and().withExternal().source(ConnectionState.DATA_READY).target(ConnectionState.READY).event(ConnectionEvent.SHUTDOWN)
                    .action(someAction)
                .and().withExternal().source(ConnectionState.DATA_READY).target(ConnectionState.ASSOCIATION_PENDING).event(ConnectionEvent.SHUTDOWN)
                    .action(someAction)
                .and().withExternal().source(ConnectionState.DATA_READY).target(ConnectionState.IDLE).event(ConnectionEvent.DISCONNECT)
                    .action(someAction)
        ;
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<ConnectionState, ConnectionEvent> config) throws Exception {
        StateMachineListenerAdapter<ConnectionState, ConnectionEvent> adapter = new StateMachineListenerAdapter<>(){
            @Override
            public void stateChanged(State<ConnectionState, ConnectionEvent> from, State<ConnectionState, ConnectionEvent> to) {
                log.info(String.format("stateChanged(from: %s, to: %s)", from, to));
            }
        };

        config.withConfiguration()
                .listener(adapter);
    }}
