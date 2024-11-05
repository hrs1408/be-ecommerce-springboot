package vn.id.nguyenphanhuyhieu.ecommerce.dao;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonDAO {
    @Autowired
    protected EntityManager entityManager;
}
