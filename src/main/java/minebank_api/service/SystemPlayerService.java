package minebank_api.service;

import minebank_api.domain.Player;
import minebank_api.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SystemPlayerService {
    public static final UUID MARKET_UUID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    public static final String MARKET_NAME = "MERCADO";

    private final PlayerRepository playerRepository;
    private final WalletService walletService;

    public SystemPlayerService(PlayerRepository playerRepository, WalletService walletService) {
        this.playerRepository = playerRepository;
        this.walletService = walletService;
    }

    public Player getOrCreateMarketPlayer() {
        Player market = playerRepository.findByMinecraftUuid(MARKET_UUID).
                orElseGet(() -> playerRepository.save(new Player(MARKET_NAME, MARKET_UUID)));

        walletService.CreateOrGetWallet(market);

        return market;
    }

}
