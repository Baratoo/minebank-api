package minebank_api.controller;


import minebank_api.domain.BankTransaction;
import minebank_api.domain.Player;
import minebank_api.domain.TransactionType;
import minebank_api.domain.Wallet;
import minebank_api.dto.BalanceResponse;
import minebank_api.dto.StatementResponse;
import minebank_api.dto.TransferRequest;
import minebank_api.repository.BankTransactionRepository;
import minebank_api.repository.PlayerRepository;
import minebank_api.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wallets")
public class ControllerWallet {
    private final WalletService walletService;
    private final PlayerRepository playerRepository;
    private final BankTransactionRepository bankTransactionRepository;

    public ControllerWallet(WalletService walletService, PlayerRepository playerRepository,  BankTransactionRepository bankTransactionRepository) {
        this.walletService = walletService;
        this.playerRepository = playerRepository;
        this.bankTransactionRepository = bankTransactionRepository;
    }

    @GetMapping("/player/{uuidPlayer}/balance")
    public BalanceResponse getBalance(@PathVariable UUID uuidPlayer){
        Player player = playerRepository.findByMinecraftUuid(uuidPlayer)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));

        Wallet wallet = walletService.getWalletByPlayer(player);

        return new BalanceResponse(player.getId(), player.getNickname(), wallet.getBalance());
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest transferRequest){
        Player fromPlayer = playerRepository.findByMinecraftUuid(transferRequest.getFromPlayerUUID())
                .orElseThrow(() -> new RuntimeException("Jogador From não encontrado"));
        Player toPlayer = playerRepository.findByMinecraftUuid(transferRequest.getToPlayerUUID())
                .orElseThrow(() -> new RuntimeException("Jogador From não encontrado"));

        walletService.transfer(fromPlayer, toPlayer, transferRequest.getAmount(), TransactionType.TRANSFER);

        return "Transferencia realizada com sucesso";
    }

    @GetMapping("player/{uuidPlayer}/statement")
    public List<StatementResponse> getStatemente(@PathVariable UUID uuidPlayer) {
        Player player = playerRepository.findByMinecraftUuid(uuidPlayer).orElseThrow(() -> new RuntimeException("Jogador From não encontrado"));

        return bankTransactionRepository.findByFromPlayerIdOrToPlayerIdOrderByCreatedAtDesc(player, player)
                .stream().map(transaction -> new StatementResponse(
                        transaction.getFromPlayerId().getNickname(),
                        transaction.getToPlayerId().getNickname(),
                        transaction.getAmount(),
                        transaction.getType(),
                        transaction.getCreatedAt()
                )).toList();
    }
}
