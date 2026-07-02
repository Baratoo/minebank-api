package minebank_api.repository;

import minebank_api.domain.MarketItem;
import minebank_api.domain.MarketPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarketPriceHistoryRepository extends JpaRepository<MarketPriceHistory, Long> {

    List<MarketPriceHistory> findAllByMarketItemOrderByCreatedAtDesc(MarketItem marketItem);

    Optional<MarketPriceHistory> findFirstByMarketItemOrderByCreatedAtDesc(MarketItem marketItem);
}
