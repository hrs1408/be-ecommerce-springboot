package vn.id.nguyenphanhuyhieu.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.id.nguyenphanhuyhieu.ecommerce.dao.ProductDAO;
import vn.id.nguyenphanhuyhieu.ecommerce.model.Product;
import vn.id.nguyenphanhuyhieu.ecommerce.reponsitory.ProductRepository;
import vn.id.nguyenphanhuyhieu.ecommerce.util.BaseRest;
import vn.id.nguyenphanhuyhieu.ecommerce.util.ConstantUtil;
import vn.id.nguyenphanhuyhieu.ecommerce.util.HttpsUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDAO productDAO;

    public String crtOrUpdate(Product product) {
        product = productRepository.saveAndFlush(product);
        return BaseRest.response(HttpsUtils.SUCCESS, product);
    }

    public String deleteById(Long id) {
        Optional<Product> pd = productRepository.findById(id);
        if (pd.isPresent()) {
            pd.get().setDeleted(ConstantUtil.Status.DELETED);

            // update
            productRepository.saveAndFlush(pd.get());
            return BaseRest.response(HttpsUtils.SUCCESS, pd.get());
        }

        return BaseRest.response(HttpsUtils.OBJECT_NOT_EXIST, "");

    }

    public String search(String name, String type, BigDecimal startPrice, BigDecimal endPrice, int offset, int maxResults) {

        List<Product> result = productDAO.search(name, type, startPrice, endPrice, offset, maxResults);
        if (result != null && !result.isEmpty()) {
            return BaseRest.response(HttpsUtils.SUCCESS, result);
        }
        return BaseRest.response(HttpsUtils.OBJECT_NOT_EXIST, "");
    }

    public String getDetailById(long id) {
        Optional<Product> pd = productRepository.findById(id);
        if (pd.isPresent()) {
            return BaseRest.response(HttpsUtils.SUCCESS, pd.get());
        }
        return BaseRest.response(HttpsUtils.OBJECT_NOT_EXIST, "");
    }


}
