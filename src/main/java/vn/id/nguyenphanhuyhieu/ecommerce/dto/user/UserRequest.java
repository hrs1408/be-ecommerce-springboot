package vn.id.nguyenphanhuyhieu.ecommerce.dto.user;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
}
