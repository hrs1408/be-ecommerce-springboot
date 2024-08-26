package vn.id.nguyenphanhuyhieu.ecommerce.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.id.nguyenphanhuyhieu.ecommerce.dto.authentication.LoginRequest;
import vn.id.nguyenphanhuyhieu.ecommerce.dto.user.UserRequest;
import vn.id.nguyenphanhuyhieu.ecommerce.dto.user.UserResponse;
import vn.id.nguyenphanhuyhieu.ecommerce.model.CustomUserDetails;
import vn.id.nguyenphanhuyhieu.ecommerce.model.User;
import vn.id.nguyenphanhuyhieu.ecommerce.reponsitory.UserRepository;
import vn.id.nguyenphanhuyhieu.ecommerce.util.Login;
import vn.id.nguyenphanhuyhieu.ecommerce.util.Token;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final String accessTokenSecret;
    private final String refreshTokenSecret;

    public UserService(
            @Value("${application.security.access-token-secret}") String accessTokenSecret,
            @Value("${application.security.refresh-token-secret}") String refreshTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
        this.refreshTokenSecret = refreshTokenSecret;
    }

    public Login login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid username");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid password");
        }
        return Login.of(user.getId(), this.accessTokenSecret, this.refreshTokenSecret);
    }

    public UserResponse registerUser(UserRequest request) {
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        user.setStatus("ACTIVE");
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    public UserResponse getUserFromToken(String token) {
        Long id = Token.from(token, accessTokenSecret);
        return modelMapper.map(userRepository.findById(id), UserResponse.class);
    }

    public String refresh(String refreshToken) {
        return Token.refresh(refreshToken, refreshTokenSecret);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }
}
