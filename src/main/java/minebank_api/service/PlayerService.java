package minebank_api.service;


import minebank_api.domain.Player;
import minebank_api.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    public Player createOrGetPlayer(UUID minecraftUuid, String nickname){
        return playerRepository.findByMinecraftUuid(minecraftUuid)
            .orElseGet(() -> playerRepository.save(new Player(nickname, minecraftUuid)));
    }
}
