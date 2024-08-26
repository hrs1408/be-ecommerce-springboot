package vn.id.nguyenphanhuyhieu.ecommerce.dto.authentication;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
