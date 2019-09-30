package next.avinor.fmtpssm.domain;

public enum ConnectionEvent {
    ACTIVATE,           // DISABLED -> IDLE             // DISABLED -> WAITING_ACCEPT
    ACTIVATE_OPEN_PORT,           // DISABLED -> IDLE             // DISABLED -> WAITING_ACCEPT
    MTConnect,
    LOCAL_SETUP,
    REMOTE_SETUP,
    REMOTE_ID_VALID,
    LOCAL_STARTUP,
    REMOTE_STARTUP,
    SHUTDOWN,
    DISCONNECT,
    TIMEOUT,
    HEARTBEAT,
    DATA,
    ID_INVALID,
    REMOTE_ACCEPT,
    LOCAL_ACCEPT,
    REJECT,
    KILL,
    Unknown
}
