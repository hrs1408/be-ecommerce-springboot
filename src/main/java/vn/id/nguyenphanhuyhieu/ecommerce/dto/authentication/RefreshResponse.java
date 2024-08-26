package vn.id.nguyenphanhuyhieu.ecommerce.dto.authentication;

import lombok.Data;

@Data
public class RefreshResponse {
    private String token;

    public RefreshResponse(String token) {
        this.token = token;
    }
}
