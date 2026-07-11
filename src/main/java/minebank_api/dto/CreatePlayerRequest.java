package minebank_api.dto;

import java.util.UUID;

public class CreatePlayerRequest {
    private UUID minecraftUuid;
    private String nickname;

    public UUID getMinecraftUuid() {
        return minecraftUuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setMinecraftUuid(UUID minecraftUuid) {
        this.minecraftUuid = minecraftUuid;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
