package minebank_api.controller;

import minebank_api.domain.Player;
import minebank_api.dto.CreatePlayerRequest;
import minebank_api.service.PlayerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class ControllerPlayer {

    private final PlayerService playerService;

    public ControllerPlayer(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public Player createPlayer(@RequestBody CreatePlayerRequest request){
        return playerService.createOrGetPlayer(request.getMinecraftUuid(), request.getNickname());
    }

}
