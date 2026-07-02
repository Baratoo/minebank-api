package minebank_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MarketItemSummaryResponse {
    private String minecraftMaterial;
    private BigDecimal currentPrice;
    private Integer Stock;
    private Integer demandIndex;
    private Integer supplyIndex;
    private BigDecimal lastVariation;
    private LocalDateTime updatedAt;

    public MarketItemSummaryResponse(String minecraftMaterial, BigDecimal currentPrice, Integer stock, Integer demandIndex, Integer supplyIndex, BigDecimal lastVariation, LocalDateTime updatedAt) {
        this.minecraftMaterial = minecraftMaterial;
        this.currentPrice = currentPrice;
        this.Stock = stock;
        this.demandIndex = demandIndex;
        this.supplyIndex = supplyIndex;
        this.lastVariation = lastVariation;
        this.updatedAt = updatedAt;
    }

    public String getMinecraftMaterial() {
        return minecraftMaterial;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public Integer getStock() {
        return Stock;
    }

    public Integer getDemandIndex() {
        return demandIndex;
    }

    public Integer getSupplyIndex() {
        return supplyIndex;
    }

    public BigDecimal getLastVariation() {
        return lastVariation;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
