package vn.id.nguyenphanhuyhieu.ecommerce.dto.user;


import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    private String status;
}
