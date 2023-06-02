package com.woowahan.techcourse.product.application;

import com.woowahan.techcourse.product.application.dto.ProductRequest;
import com.woowahan.techcourse.product.dao.ProductDao;
import com.woowahan.techcourse.product.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductCommandService {

    private final ProductDao productDao;

    public ProductCommandService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public long createProduct(ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getPrice(),
                productRequest.getImageUrl());
        return productDao.insert(product);
    }

    public void updateProduct(Long productId, ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getPrice(),
                productRequest.getImageUrl());
        productDao.updateProduct(productId, product);
    }

    public void deleteProduct(Long productId) {
        productDao.deleteProduct(productId);
    }
}
