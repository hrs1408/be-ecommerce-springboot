package vn.id.nguyenphanhuyhieu.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import vn.id.nguyenphanhuyhieu.ecommerce.model.Product;
import vn.id.nguyenphanhuyhieu.ecommerce.service.ProductService;
import vn.id.nguyenphanhuyhieu.ecommerce.util.ConstantUtil;

import java.math.BigDecimal;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/crtOrUpdate")
    public String crtOrUpdProduct(@RequestBody Product product) {
        // valid
        return productService.crtOrUpdate(product);
    }

    @GetMapping(value = "/search")
    public String searchProduct(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "type", required = false) String type,
                                @RequestParam(value = "startPrice", required = false) BigDecimal startPrice,
                                @RequestParam(value = "endPrice", required = false) BigDecimal endPrice,
                                @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        page = (page < 1) ? 1 : page;
        int maxResults = ConstantUtil.DefaulValue.PAGE_SIZE;
        int offset = (page - 1) * maxResults;
        return productService.search(name, type, startPrice, endPrice, offset, maxResults);
    }

    @GetMapping(value = "/detail")
    public String productDetail(@RequestParam(value = "id") long id) {
        return productService.getDetailById(id);
    }

    @PostMapping(value = "/delete")
    public String deleteById(@RequestParam(value = "id") long id) {
        return productService.deleteById(id);
    }


}
