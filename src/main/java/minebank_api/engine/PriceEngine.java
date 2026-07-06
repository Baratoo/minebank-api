package minebank_api.engine;

import minebank_api.domain.MarketItem;
import minebank_api.domain.TransactionType;
import org.springframework.boot.web.embedded.netty.NettyWebServer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;

@Component
public class PriceEngine {
    private static final BigDecimal MIN_FACTOR = new BigDecimal("0.50");
    private static final BigDecimal MAX_FACTOR = new BigDecimal("1.50");

    public BigDecimal calculateNewPrice(MarketItem item, Integer quantity, TransactionType type) {
        BigDecimal currentPrice = item.getCurrentPrice();

        BigDecimal factor = calculateFactor(quantity, type);

        BigDecimal newPrice = currentPrice.multiply(factor);

        BigDecimal minPrice =  item.getBasePrice().multiply(MIN_FACTOR);
        BigDecimal maxPrice =  item.getBasePrice().multiply(MAX_FACTOR);

        if (newPrice.compareTo(minPrice) < 0) {
            newPrice = minPrice;
        }
        if (newPrice.compareTo(maxPrice) > 0) {
            newPrice = maxPrice;
        }

//        return newPrice;
        return newPrice.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateFactor(Integer quantity, TransactionType type) {
        BigDecimal factor = BigDecimal.valueOf(quantity).multiply(new BigDecimal("0.02"));

        if (type == TransactionType.MARKET_BUY) {
            return BigDecimal.ONE.add(factor);
        }

        if (type == TransactionType.MARKET_SELL) {
            return BigDecimal.ONE.subtract(factor);
        }

        throw new RuntimeException("Tipo de transação não encontrada");
    }



}
