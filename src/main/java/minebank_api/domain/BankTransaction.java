package minebank_api.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bankTransaction")
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Player fromPlayerId;

    @ManyToOne
    @JoinColumn
    private Player toPlayerId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    public BankTransaction() {}

    public BankTransaction(Player fromPlayerId, Player toPlayerId, BigDecimal amount, TransactionType type) {
        this.fromPlayerId = fromPlayerId;
        this.toPlayerId = toPlayerId;
        this.amount = amount;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }

    public Player getFromPlayerId() {
        return fromPlayerId;
    }

    public Player getToPlayerId() {
        return toPlayerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getType() {
        return type.getDescription();
    }
}
