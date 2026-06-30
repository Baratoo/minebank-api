package minebank_api.domain;

import minebank_api.repository.MarketItemRepository;

import java.math.BigDecimal;

public class MarketItemService {
    private final MarketItemRepository marketItemRepository;

    public MarketItemService(MarketItemRepository marketItemRepository) {
        this.marketItemRepository = marketItemRepository;
    }

    public MarketItem getOrCreateMarketItem(String minecraftMaterial, String displayName, BigDecimal basePrice, Integer stock) {
        MarketItem marketItem = marketItemRepository.findByMinecraftMaterial(minecraftMaterial)
                .orElse(() -> marketItemRepository.save(new MarketItem(minecraftMaterial, displayName, basePrice, stock)));
    }

}
