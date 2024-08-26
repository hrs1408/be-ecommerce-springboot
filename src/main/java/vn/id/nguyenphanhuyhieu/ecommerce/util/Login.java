package vn.id.nguyenphanhuyhieu.ecommerce.util;

import lombok.Getter;

public class Login {
    @Getter
    private final Token accessToken;

    @Getter
    private final Token refreshToken;
    private static final Long REFRESH_TOKEN_VALIDITY = 60L * 24;
    private static final Long ACCESS_TOKEN_VALIDITY = 60L;

    private Login(Token accessToken, Token refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static Login of(Long userId, String accessSecret, String refreshSecret) {
        return new Login(
                Token.of(userId, ACCESS_TOKEN_VALIDITY, accessSecret),
                Token.of(userId, REFRESH_TOKEN_VALIDITY, refreshSecret)
        );
    }
}
