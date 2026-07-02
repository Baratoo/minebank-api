package minebank_api.repository;

import minebank_api.domain.BankTransaction;
import minebank_api.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

    List<BankTransaction> findByFromPlayerIdOrToPlayerIdOrderByCreatedAtDesc(Player fromPlayer, Player toPlayer);
}
