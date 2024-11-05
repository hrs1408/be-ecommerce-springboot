package vn.id.nguyenphanhuyhieu.ecommerce.dao;

import jakarta.persistence.TemporalType;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.id.nguyenphanhuyhieu.ecommerce.model.Product;
import vn.id.nguyenphanhuyhieu.ecommerce.util.CommonUtil;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class ProductDAO extends CommonDAO {

    public List<Product> search(String name, String type, BigDecimal startPrice, BigDecimal endPrice, int offset, int maxResults) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT pd FROM Product pd ");
        sql.append(" WHERE 1 = 1 ");
        if (!CommonUtil.isEmpty(name)) {
            sql.append(" AND LOWER(name) LIKE LOWER('%'||:name||'%') ");
        }
        if (!CommonUtil.isEmpty(type)) {
            sql.append(" AND LOWER(type) LIKE LOWER('%'||:type||'%') ");
        }
        // case 1
        if (startPrice != null && endPrice != null) {
            sql.append(" AND price >= :startPrice AND price <= :endPrice ");
        }
        // case 2
        else if (startPrice != null) {
            sql.append(" AND price >= :startPrice ");
        } else if (endPrice != null) {
            sql.append(" AND price <= :endPrice ");
        }
        TypedQuery<Product> query = super.entityManager.createQuery(sql.toString(), Product.class);
        if (!CommonUtil.isEmpty(name)) {
            query.setParameter("name", name);
        }
        if (!CommonUtil.isEmpty(type)) {
            query.setParameter("type", type);
        }
        if (startPrice != null) {
            query.setParameter("startPrice", startPrice);
        }
        if (endPrice != null) {
            query.setParameter("endPrice", endPrice);
        }
        query.setMaxResults(maxResults);
        query.setFirstResult(offset);
        return query.getResultList();
    }
}
