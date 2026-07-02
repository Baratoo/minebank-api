package minebank_api.repository;

import minebank_api.domain.MarketItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketItemRepository extends JpaRepository<MarketItem,Long> {

    Optional<MarketItem> findByMinecraftMaterial(String minecraftMaterial);

    boolean existsByMinecraftMaterial(String minecraftMaterial);
}
