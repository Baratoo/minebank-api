package minebank_api.service;

import minebank_api.domain.MarketItem;
import minebank_api.repository.MarketItemRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MarketItemService {
    private final MarketItemRepository marketItemRepository;

    public MarketItemService(MarketItemRepository marketItemRepository) {
        this.marketItemRepository = marketItemRepository;
    }

    public MarketItem getOrCreateMarketItem(String minecraftMaterial, String displayName, BigDecimal basePrice, Integer stock) {
        MarketItem marketItem = marketItemRepository.findByMinecraftMaterial(minecraftMaterial)
                .orElseGet(() -> marketItemRepository.save(new MarketItem(minecraftMaterial, displayName, basePrice, stock)));

        return marketItem;
    }

    public MarketItem getMarketItem(String minecraftMaterial) {
        return marketItemRepository.findByMinecraftMaterial(minecraftMaterial)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }

    public List<MarketItem> getAllMarketItems() {
        return marketItemRepository.findAll();
    }

}
