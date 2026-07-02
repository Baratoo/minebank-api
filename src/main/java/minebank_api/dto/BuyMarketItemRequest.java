package minebank_api.dto;

import java.util.UUID;

public class BuyMarketItemRequest {
    private UUID minecraftUuid;
    private String minecraftMaterial;
    private Integer quantity;

    public UUID getMinecraftUuid() {
        return minecraftUuid;
    }

    public String getMinecraftMaterial() {
        return minecraftMaterial;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
