package minebank_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StatementResponse {
    private String fromPlayer;
    private String toPlayer;
    private BigDecimal amount;
    private String type;
    private LocalDateTime createdAt;

    public StatementResponse(String fromPlayer, String toPlayer, BigDecimal amount, String type, LocalDateTime createdAt) {
        this.fromPlayer = fromPlayer;
        this.toPlayer = toPlayer;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }

    public String getFromPlayer() {
        return fromPlayer;
    }

    public String getToPlayer() {
        return toPlayer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
