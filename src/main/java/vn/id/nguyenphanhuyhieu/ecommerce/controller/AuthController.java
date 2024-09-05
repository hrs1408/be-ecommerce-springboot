package vn.id.nguyenphanhuyhieu.ecommerce.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.id.nguyenphanhuyhieu.ecommerce.dto.authentication.LoginRequest;
import vn.id.nguyenphanhuyhieu.ecommerce.dto.authentication.LoginResponse;
import vn.id.nguyenphanhuyhieu.ecommerce.dto.authentication.RefreshResponse;
import vn.id.nguyenphanhuyhieu.ecommerce.dto.user.UserRequest;
import vn.id.nguyenphanhuyhieu.ecommerce.model.ResponseObject;
import vn.id.nguyenphanhuyhieu.ecommerce.service.UserService;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService service;

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseObject> register(@Valid @RequestBody UserRequest request) {
        if (!Objects.equals(request.getPassword(), request.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("400", "Password do not match", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("200", "Success", service.registerUser(request))
        );
    }

    @GetMapping(value = "/refresh-token")
    public ResponseEntity<ResponseObject> refresh(@CookieValue("refresh_token") String refreshToken) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("200", "Success", new RefreshResponse(service.refresh(refreshToken)))
        );
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseObject> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        var login = service.login(request);
        Cookie cookie = new Cookie("refresh_token", login.getRefreshToken().getToken());
        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("200", "Login success", new LoginResponse(login.getAccessToken().getToken(), login.getRefreshToken().getToken()))
        );
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<ResponseObject> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("refresh_token", "");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/api");
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("200", "Logout success", "")
        );
    }

    @GetMapping(value = "/user")
    public ResponseEntity<ResponseObject> user(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            token = token.replace("Bearer ", "");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("200", "Success", service.getUserFromToken(token))
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("404", "User not found", "")
        );
    }
}
