package minebank_api.config;

import minebank_api.service.SystemPlayerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupDataLoader implements CommandLineRunner {

    private final SystemPlayerService systemPlayerService;

    public StartupDataLoader(SystemPlayerService systemPlayerService) {
        this.systemPlayerService = systemPlayerService;
    }

     @Override
     public void run(String... args){
        systemPlayerService.getOrCreateMarketPlayer();
        System.out.println("Mercado Criado/Verificado com Sucesso! ");
     }

}
