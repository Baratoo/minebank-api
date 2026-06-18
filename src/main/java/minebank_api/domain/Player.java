package minebank_api.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "minecraft_uuid", nullable = false, unique = true)
    private UUID minecraftUuid;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createdAt;

    public Player() {
    }

    public Player(String nickname, UUID minecraftUuid) {
        this.nickname = nickname;
        this.minecraftUuid = minecraftUuid;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public UUID getMinecraftUuid() {
        return minecraftUuid;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
