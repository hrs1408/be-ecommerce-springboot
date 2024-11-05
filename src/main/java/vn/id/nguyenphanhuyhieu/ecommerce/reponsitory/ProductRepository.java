package vn.id.nguyenphanhuyhieu.ecommerce.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.id.nguyenphanhuyhieu.ecommerce.model.Product;
import vn.id.nguyenphanhuyhieu.ecommerce.util.ConstantUtil;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT pd FROM Product pd WHERE id = :id AND deleted = "+ ConstantUtil.Status.NOT_DELETED_YET +" ")
    Optional<Product> findById(Long id);
}
