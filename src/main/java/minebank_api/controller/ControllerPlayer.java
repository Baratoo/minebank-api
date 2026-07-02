package minebank_api.controller;

import minebank_api.domain.Player;
import minebank_api.dto.CreatePlayerRequest;
import minebank_api.repository.PlayerRepository;
import minebank_api.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/players")
public class ControllerPlayer {

    private final PlayerService playerService;
    private final PlayerRepository playerRepository;

    public ControllerPlayer(PlayerService playerService, PlayerRepository playerRepository) {
        this.playerService = playerService;
        this.playerRepository = playerRepository;
    }

    @PostMapping
    public Player createPlayer(@RequestBody CreatePlayerRequest request){
        return playerService.createOrGetPlayer(request.getMinecraftUuid(), request.getNickname());
    }

    @GetMapping("/list")
    public List<Player> getPlayers(){
        return playerRepository.findAll();
    }

}
