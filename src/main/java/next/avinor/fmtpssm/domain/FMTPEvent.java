package next.avinor.fmtpssm.domain;

public enum FMTPEvent {
    ACTIVATE,           // DISABLED -> IDLE             // DISABLED -> WAITING_ACCEPT
    ACTIVATE_OPEN_PORT,           // DISABLED -> IDLE             // DISABLED -> WAITING_ACCEPT
    Unknown             // Placeholder. Not to be used!
}
