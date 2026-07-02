package minebank_api.service;

import minebank_api.domain.*;
import minebank_api.repository.MarketItemRepository;
import minebank_api.repository.MarketPriceHistoryRepository;
import minebank_api.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class MarketService {
    private final MarketItemService marketItemService;
    private final MarketItemRepository marketItemRepository;
    private final WalletService walletService;
    private final WalletRepository walletRepository;
    private final SystemPlayerService systemPlayerService;
    private  MarketPriceHistoryRepository marketPriceHistoryRepository;

    public MarketService(MarketItemService marketItemService, MarketItemRepository marketItemRepository,
                         WalletService walletService, WalletRepository walletRepository,
                         SystemPlayerService systemPlayerService, MarketPriceHistoryRepository marketPriceHistoryRepository) {
        this.marketItemService = marketItemService;
        this.marketItemRepository = marketItemRepository;
        this.walletService = walletService;
        this.walletRepository = walletRepository;
        this.systemPlayerService = systemPlayerService;
        this.marketPriceHistoryRepository = marketPriceHistoryRepository;
    }

    public MarketItem buyItem(Player player, String minecraftMaterial, Integer quantity) {
        MarketItem item = marketItemRepository.findByMinecraftMaterial(minecraftMaterial)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        Wallet wallet = walletService.getWalletByPlayer(player);

        BigDecimal total = (item.getCurrentPrice().multiply(new BigDecimal(quantity)));
        BigDecimal oldPrice = item.getCurrentPrice();

        if (wallet.getBalance().compareTo(total) < 0){
            throw new RuntimeException("Saldo insuficiente");
        }

        Player market = systemPlayerService.getOrCreateMarketPlayer();
        walletService.transfer(player, market, total, TransactionType.MARKET_BUY);
//        wallet.debit(total);
        item.buy(quantity);
        marketItemRepository.save(item);
        marketPriceHistoryRepository.save(new MarketPriceHistory(item, oldPrice, item.getCurrentPrice(), quantity, TransactionType.MARKET_BUY));
        return item;
    }

    public MarketItem sellItem(Player player, String minecraftMaterial, Integer quantity) {
        MarketItem item = marketItemRepository.findByMinecraftMaterial(minecraftMaterial)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        Player market = systemPlayerService.getOrCreateMarketPlayer();
        Wallet marketWallet = walletService.getWalletByPlayer(market);

        BigDecimal total = (item.getCurrentPrice().multiply(new BigDecimal(quantity)));
        BigDecimal oldPrice = item.getCurrentPrice();

        if (marketWallet.getBalance().compareTo(total) < 0){
            throw new RuntimeException("Mercado está sem Dinheiro para Comprar! ");
        }

        walletService.transfer(market, player, total, TransactionType.MARKET_SELL);
        item.sell(quantity);
        marketItemRepository.save(item);
        marketPriceHistoryRepository.save(new MarketPriceHistory(item, oldPrice, item.getCurrentPrice(), quantity, TransactionType.MARKET_SELL));
        return item;
    }
}
