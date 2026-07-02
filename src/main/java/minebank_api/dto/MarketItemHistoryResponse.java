package minebank_api.dto;

import minebank_api.domain.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MarketItemHistoryResponse {
    private String type;
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
    private Integer quantity;
    private BigDecimal variation;
    private LocalDateTime updatedAt;

    public MarketItemHistoryResponse(String type, BigDecimal oldPrice, BigDecimal newPrice, Integer quantity, LocalDateTime updatedAt) {
        this.type = type;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.quantity = quantity;
        this.variation = newPrice.subtract(oldPrice);
        this.updatedAt = updatedAt;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getVariation() {
        return variation;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
