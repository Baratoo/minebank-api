package minebank_api.service;


import minebank_api.domain.Player;
import minebank_api.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final WalletService walletService;

    public PlayerService(PlayerRepository playerRepository, WalletService walletService) {
        this.playerRepository = playerRepository;
        this.walletService = walletService;
    }

    public Player createOrGetPlayer(UUID minecraftUuid, String nickname){
        Player player = playerRepository.findByMinecraftUuid(minecraftUuid).orElseGet(
                () -> playerRepository.save(new Player(nickname, minecraftUuid)));

        walletService.CreateOrGetWallet(player);

        return player;
    }
}
