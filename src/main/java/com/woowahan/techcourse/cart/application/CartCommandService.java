package com.woowahan.techcourse.cart.application;

import com.woowahan.techcourse.cart.dao.CartItemDao;
import com.woowahan.techcourse.cart.domain.CartItem;
import com.woowahan.techcourse.cart.dto.CartItemQuantityUpdateRequest;
import com.woowahan.techcourse.cart.dto.CartItemRequest;
import com.woowahan.techcourse.cart.exception.CartItemNotFoundException;
import com.woowahan.techcourse.product.application.ProductQueryService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartCommandService {

    private final ProductQueryService productQueryService;
    private final CartItemDao cartItemDao;

    public CartCommandService(ProductQueryService productQueryService, CartItemDao cartItemDao) {
        this.productQueryService = productQueryService;
        this.cartItemDao = cartItemDao;
    }

    public Long add(long memberId, CartItemRequest cartItemRequest) {
        productQueryService.findById(cartItemRequest.getProductId());
        return cartItemDao.save(new CartItem(cartItemRequest.getProductId(), memberId));
    }

    public void updateQuantity(long memberId, Long id, CartItemQuantityUpdateRequest request) {
        CartItem cartItem = cartItemDao.findById(id)
                .orElseThrow(CartItemNotFoundException::new);
        cartItem.checkOwner(memberId);

        if (request.getQuantity() == 0) {
            cartItemDao.deleteById(id);
            return;
        }

        cartItem.changeQuantity(request.getQuantity());
        cartItemDao.updateQuantity(cartItem);
    }

    public void remove(long memberId, Long id) {
        CartItem cartItem = cartItemDao.findById(id)
                .orElseThrow(CartItemNotFoundException::new);
        cartItem.checkOwner(memberId);

        cartItemDao.deleteById(id);
    }

    public void removeAllByCartIds(Long memberId, List<Long> cartItemIds) {
        cartItemDao.deleteAll(memberId, cartItemIds);
    }
}
