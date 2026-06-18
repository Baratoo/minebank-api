package minebank_api.repository;

import minebank_api.domain.Player;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByMinecraftUuid(UUID minecraftUuid);

    boolean existsByMinecraftUuid(UUID minecraftUuid);
}
