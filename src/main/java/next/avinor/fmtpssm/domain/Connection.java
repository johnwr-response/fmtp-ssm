package next.avinor.fmtpssm.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Connection {
    private UUID id;
    private ConnectionState state;
    private boolean locallyInitiated;
    private int test;
}
