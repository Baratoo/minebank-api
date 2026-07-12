package minebank_api.controller;

import minebank_api.domain.MarketItem;
import minebank_api.domain.MarketPriceHistory;
import minebank_api.domain.Player;
import minebank_api.dto.CreateMarketItemRequest;
import minebank_api.dto.MarketItemHistoryResponse;
import minebank_api.dto.MarketItemSummaryResponse;
import minebank_api.repository.MarketItemRepository;
import minebank_api.repository.MarketPriceHistoryRepository;
import minebank_api.service.MarketItemService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/market")
public class ControllerMarketItem {

    private final MarketItemService marketItemService;
    private final MarketItemRepository marketItemRepository;
    private final MarketPriceHistoryRepository marketPriceHistoryRepository;

    public ControllerMarketItem(MarketItemService marketItemService,  MarketItemRepository marketItemRepository, MarketPriceHistoryRepository marketPriceHistoryRepository) {
        this.marketItemService = marketItemService;
        this.marketItemRepository = marketItemRepository;
        this.marketPriceHistoryRepository = marketPriceHistoryRepository;
    }

    @PostMapping("/item")
    public MarketItem postItem(@RequestBody CreateMarketItemRequest marketItemRequest){
        return marketItemService.getOrCreateMarketItem(marketItemRequest.getMinecraftMaterial(),
                marketItemRequest.getDisplayName(),
                marketItemRequest.getBasePrice(),
                marketItemRequest.getStock());
    }

    @GetMapping("/item/{minecraftMaterial}")
    public MarketItem getItem(@PathVariable String minecraftMaterial){
        return marketItemService.getMarketItem(minecraftMaterial);
    }

    @GetMapping("/items")
    public List<MarketItem> getItems(){
        return marketItemService.getAllMarketItems();
    }

    @GetMapping("/item/{minecraftMaterial}/history")
    public List<MarketItemHistoryResponse> getHistory(@PathVariable String minecraftMaterial){
        MarketItem marketItem = marketItemService.getMarketItem(minecraftMaterial);
        return marketPriceHistoryRepository.findAllByMarketItemOrderByCreatedAtDesc(marketItem).stream()
                .map(marketPriceHistory -> new MarketItemHistoryResponse(
                        marketPriceHistory.getType(),
                        marketPriceHistory.getOldPrice(),
                        marketPriceHistory.getNewPrice(),
                        marketPriceHistory.getQuantity(),
                        marketPriceHistory.getCreatedAt()
                )).toList();
    }

    @GetMapping("/item/{minecraftMaterial}/summary")
    public MarketItemSummaryResponse getSumarry(@PathVariable String minecraftMaterial){
        MarketItem marketItem = marketItemService.getMarketItem(minecraftMaterial);
        MarketPriceHistory marketPriceHistory = marketPriceHistoryRepository.findFirstByMarketItemOrderByCreatedAtDesc(marketItem)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        return new MarketItemSummaryResponse(marketItem.getMinecraftMaterial(),
                marketItem.getCurrentPrice(),
                marketItem.getStock(),
                marketItem.getDemandIndex(),
                marketItem.getSupplyIndex(),
                marketPriceHistory.getOldPrice().subtract(marketPriceHistory.getNewPrice()),
                marketItem.getUpdatedAt());
    }
}
