package minebank_api.repository;

import minebank_api.domain.Player;
import minebank_api.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet>findByPlayer(Player player);
}
