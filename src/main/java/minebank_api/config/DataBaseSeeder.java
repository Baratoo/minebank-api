package minebank_api.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import minebank_api.domain.MarketItem;
import minebank_api.repository.MarketItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Responsavel por alimentar o banco com os itens
@Component
public class DataBaseSeeder implements CommandLineRunner {

    private final MarketItemRepository marketItemRepository;
    private final ObjectMapper objectMapper;

    public DataBaseSeeder(MarketItemRepository marketItemRepository, ObjectMapper objectMapper) {
        this.marketItemRepository = marketItemRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (marketItemRepository.count() == 0) {
            System.out.println("[MineBankAPI] Banco de dados está vazio, buscando itens...");

            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://raw.githubusercontent.com/PrismarineJS/minecraft-data/master/data/pc/1.20/items.json"))
                        .GET().build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                //lista de itens da api
                List<Map<String, Object>> prismarineItems = objectMapper.readValue(response.body(), new TypeReference<>() {
                });

                //lista vazia
                List<MarketItem> itensToSave = new ArrayList<>();

                for (Map<String, Object> item : prismarineItems) {

                    String materialName = ((String) item.get("name")).toUpperCase();
                    //BigDecimal basePrice = BigDecimal.valueOf(10.0);
                    BigDecimal basePrice = BigDecimal.valueOf(calcularPrecoBase(materialName));
                    Integer stock = calcularEstoqueInicial(basePrice);

                    MarketItem marketItem = new MarketItem(
                            materialName,
                            materialName,
                            basePrice,
                            stock
                    );

                    itensToSave.add(marketItem);
                }
                marketItemRepository.saveAll(itensToSave);
                System.out.println("[MineBank API] SUCESSO! " + itensToSave.size() + " itens do Minecraft cadastrados automaticamente!");
            } catch (Exception e) {
                System.err.println("[MineBankAPI] ERRO: " + e.getMessage());
            }
        } else {
           System.out.println("[MineBank API] O banco já possui itens cadastrados.");
        }
    }


    private Double calcularPrecoBase(String nomeMaterial) {
        // 1. Itens intocáveis (Ignoram as regras de multiplicador)
        if (nomeMaterial.equals("NETHER_STAR") || nomeMaterial.equals("ELYTRA") || nomeMaterial.equals("DRAGON_EGG") || nomeMaterial.equals("BEACON")) {
            return 50000.0;
        }

        // 2. Determinar o valor da MATÉRIA-PRIMA base
        double valorBase = 10.0; // Valor padrão para o resto do jogo

        if (nomeMaterial.contains("NETHERITE")) valorBase = 5000.0;
        else if (nomeMaterial.contains("DIAMOND") || nomeMaterial.contains("EMERALD")) valorBase = 1000.0;
        else if (nomeMaterial.contains("GOLD")) valorBase = 500.0;
        else if (nomeMaterial.contains("IRON")) valorBase = 200.0;
        else if (nomeMaterial.contains("COPPER")) valorBase = 50.0;
        else if (nomeMaterial.contains("COAL") || nomeMaterial.contains("REDSTONE") || nomeMaterial.contains("LAPIS")) valorBase = 25.0;
        else if (nomeMaterial.contains("BEEF") || nomeMaterial.contains("PORKCHOP") || nomeMaterial.contains("APPLE") || nomeMaterial.contains("CARROT")) valorBase = 15.0;
        else if (nomeMaterial.contains("WOOD") || nomeMaterial.contains("LOG") || nomeMaterial.contains("PLANKS") || nomeMaterial.contains("STONE")) valorBase = 5.0;
        else if (nomeMaterial.contains("DIRT") || nomeMaterial.contains("SAND") || nomeMaterial.contains("GRAVEL") || nomeMaterial.contains("GRASS")) valorBase = 0.5;

        // 3. Aplicar o MULTIPLICADOR de Crafting/Tipo de item
        double multiplicador = 1.0;

        // Blocos
        if (nomeMaterial.endsWith("_BLOCK")) multiplicador = 9.0;
            // Armaduras
        else if (nomeMaterial.endsWith("_CHESTPLATE")) multiplicador = 8.0;
        else if (nomeMaterial.endsWith("_LEGGINGS")) multiplicador = 7.0;
        else if (nomeMaterial.endsWith("_HELMET")) multiplicador = 5.0;
        else if (nomeMaterial.endsWith("_BOOTS")) multiplicador = 4.0;
            // Ferramentas e Armas
        else if (nomeMaterial.endsWith("_PICKAXE") || nomeMaterial.endsWith("_AXE")) multiplicador = 3.0;
        else if (nomeMaterial.endsWith("_SWORD") || nomeMaterial.endsWith("_HOE")) multiplicador = 2.0;
        else if (nomeMaterial.endsWith("_SHOVEL")) multiplicador = 1.0;
            // Minérios Brutos
        else if (nomeMaterial.endsWith("_ORE")) multiplicador = 1.5; // Minério vale um pouco mais que a barra fundida por causa do Silk Touch

        return valorBase * multiplicador;
    }

    private Integer calcularEstoqueInicial(BigDecimal precoFinal) {
        double preco = precoFinal.doubleValue();

        // Tiers de Escassez baseados no preço final do item
        if (preco >= 10000.0) return 10;    // Muito Raros (ex: Blocos de Netherite, Nether Star)
        if (preco >= 2000.0) return 50;     // Raros (ex: Barras de Netherite, Peitoral de Diamante)
        if (preco >= 500.0) return 200;     // Incomuns (ex: Diamantes, Blocos de Ouro)
        if (preco >= 100.0) return 1000;    // Normais (ex: Ferro, Ferramentas de Pedra)
        if (preco >= 10.0) return 5000;     // Fartos (ex: Comidas, Madeira)

        return 10000; // Itens absurdamente baratos e comuns (Terra, Areia, Cascalho)
    }
}
