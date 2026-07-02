package minebank_api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferRequest {
    private UUID fromPlayerUUID;
    private UUID toPlayerUUID;
    private BigDecimal amount;

    public UUID getFromPlayerUUID() {
        return fromPlayerUUID;
    }

    public UUID getToPlayerUUID() {
        return toPlayerUUID;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
