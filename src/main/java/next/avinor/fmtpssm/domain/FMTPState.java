package next.avinor.fmtpssm.domain;

public enum FMTPState {
    DISABLED,               // L R
    WAITING_ACCEPT,         //   R
    IDLE,                   // L R
    WAITING_CONNECT,        // L
    CONNECTION_PENDING,     // L
    TCP_ESTABLISHED,        // L
    SYSTEM_ID_PENDING,      //   R
    READY_SEND_ID,          //   R
    REJECTED,               // L
    ID_PENDING,             // L R
    PRE_READY,              // L
    READY,                  // L R
    ASSOCIATION_PENDING,    // L
    DATA_READY              // L
}
