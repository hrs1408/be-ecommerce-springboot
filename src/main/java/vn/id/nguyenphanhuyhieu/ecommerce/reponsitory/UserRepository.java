package vn.id.nguyenphanhuyhieu.ecommerce.reponsitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.id.nguyenphanhuyhieu.ecommerce.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
   User findByUsername(String username);
   Optional<User> findByEmail(String email);
}
