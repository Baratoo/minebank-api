package minebank_api.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "MarketPriceHistory")
public class MarketPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "market_item", nullable = false)
    private MarketItem marketItem;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal oldPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal newPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createdAt;

    public MarketPriceHistory() {}

    public MarketPriceHistory(MarketItem marketItem, BigDecimal oldPrice, BigDecimal newPrice, Integer quantity, TransactionType type) {
        this.marketItem = marketItem;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.quantity = quantity;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public MarketItem getMarketItem() {
        return marketItem;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getType() {
        return type.getDescription();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
