package minebank_api.controller;

import minebank_api.domain.Player;
import minebank_api.dto.BuyMarketItemRequest;
import minebank_api.repository.PlayerRepository;
import minebank_api.service.MarketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/market")
public class ControllerMarket {
    private final MarketService marketService;
    private final PlayerRepository playerRepository;

    public ControllerMarket(MarketService marketService, PlayerRepository playerRepository) {
        this.marketService = marketService;
        this.playerRepository = playerRepository;
    }

    @PostMapping("/buy")
    public ResponseEntity<String>  buyItem(@RequestBody BuyMarketItemRequest BuyRequest){
        Player player = playerRepository.findByMinecraftUuid(BuyRequest.getMinecraftUuid())
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));

        marketService.buyItem(player, BuyRequest.getMinecraftMaterial(), BuyRequest.getQuantity());

        return ResponseEntity.ok("Sucesso ao Comprar item");
    }

    @PostMapping("/sell")
    public ResponseEntity<String>  sellItem(@RequestBody BuyMarketItemRequest BuyRequest){
        Player player = playerRepository.findByMinecraftUuid(BuyRequest.getMinecraftUuid())
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));

        marketService.sellItem(player, BuyRequest.getMinecraftMaterial(), BuyRequest.getQuantity());

        return ResponseEntity.ok("Sucesso ao Vender item");
    }

}
