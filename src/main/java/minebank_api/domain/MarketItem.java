package minebank_api.domain;

import jakarta.persistence.*;
import minebank_api.repository.MarketItemRepository;
import minebank_api.repository.MarketPriceHistoryRepository;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "marketItens")
public class MarketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String minecraftMaterial;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal currentPrice;

    @Column(nullable = false)
    private Integer stock;

    //Demanda
    @Column(nullable = false)
    private Integer demandIndex;

    //Oferta
    @Column(nullable = false)
    private Integer supplyIndex;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public MarketItem() {}

    public MarketItem(String minecraftMaterial, String displayName, BigDecimal basePrice, Integer stock) {
        this.minecraftMaterial = minecraftMaterial;
        this.displayName = displayName;
        this.basePrice = basePrice;
        this.currentPrice = basePrice;
        this.stock = stock;
        this.demandIndex = 0;
        this.supplyIndex = 0;
        this.updatedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getMinecraftMaterial() {
        return minecraftMaterial;
    }

    public String getDisplayName() {
        return displayName;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer getDemandIndex() {
        return demandIndex;
    }

    public Integer getSupplyIndex() {
        return supplyIndex;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void buy(Integer quantity) {
        if (quantity <= 0){
            throw new RuntimeException("Quantidade Inválida");
        }
        if (quantity > this.stock) {
            throw new RuntimeException("Estoque insuficiente");
        }

        this.stock -= quantity;
        this.demandIndex += quantity;

        //por agora 2%
        BigDecimal factor = BigDecimal.valueOf(1 + (quantity * 0.02));
        this.currentPrice = this.currentPrice.multiply(factor);
        this.updatedAt = LocalDateTime.now();
    }

    public void sell(Integer quantity) {
        if (quantity <= 0){
            throw new RuntimeException("Quantidade Inválida");
        }

        this.stock += quantity;
        this.supplyIndex += quantity;

        //por agora 2%
        BigDecimal factor = BigDecimal.valueOf(1 - (quantity * 0.02));
        if (factor.compareTo(new BigDecimal("0.50")) < 0) {
            factor = new BigDecimal("0.50");
        }
        this.currentPrice = this.currentPrice.multiply(factor);
        this.updatedAt = LocalDateTime.now();
    }
}
