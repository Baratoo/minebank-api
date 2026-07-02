package minebank_api.dto;

import java.math.BigDecimal;

public class BalanceResponse {
    private Long playerId;
    private String nickname;
    private BigDecimal balance;

    public BalanceResponse(Long playerId, String nickname, BigDecimal balance) {
        this.playerId = playerId;
        this.nickname = nickname;
        this.balance = balance;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public String getNickName() {
        return nickname;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
