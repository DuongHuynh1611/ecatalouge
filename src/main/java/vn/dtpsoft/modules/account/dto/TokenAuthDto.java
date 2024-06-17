package vn.dtpsoft.modules.account.dto;

import lombok.Data;
@Data
public class TokenAuthDto {
    private String token;
    private String type = "Bearer";
    private Long expirationInMs;

    public TokenAuthDto(String token, Long expirationInMs) {
        this.token = token;
        this.expirationInMs = expirationInMs;
    }
}
