package minebank_api.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallet")
public class Wallet {

    private static final String MARKET_PLAYER_NICKNAME = "MERCADO";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "player_id", nullable = false, unique = true)
    private Player player;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "update_at", nullable = false)
    private LocalDateTime updatedAt;

    public Wallet(){

    }

    public Wallet(Player player){
        this.player = player;
//        this.Balance = BigDecimal.ZERO;
        if (player.getNickname().equals(MARKET_PLAYER_NICKNAME)) {
            this.balance = new BigDecimal(100000.00);
        } else{
            this.balance = new BigDecimal(1500.00);
        }
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void credit(BigDecimal amount){
        this.balance = this.balance.add(amount);
        this.updatedAt = LocalDateTime.now();
    }

    public void debit(BigDecimal amount){
        this.balance = this.balance.subtract(amount);
        this.updatedAt = LocalDateTime.now();
    }

}
