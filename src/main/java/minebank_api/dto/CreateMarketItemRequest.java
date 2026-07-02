package minebank_api.dto;

import java.math.BigDecimal;

public class CreateMarketItemRequest {
    private String minecraftMaterial;
    private String displayName;
    private BigDecimal basePrice;
    private Integer stock;

    public String getMinecraftMaterial() {
        return minecraftMaterial;
    }

    public String getDisplayName() {
        return displayName;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public Integer getStock() {
        return stock;
    }
}
