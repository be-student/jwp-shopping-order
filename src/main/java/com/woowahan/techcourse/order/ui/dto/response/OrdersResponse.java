package com.woowahan.techcourse.order.ui.dto.response;

import java.util.List;

public class OrdersResponse {

    private List<OrderResponse> orders;

    private OrdersResponse() {
    }

    public OrdersResponse(List<OrderResponse> orders) {
        this.orders = orders;
    }

    public List<OrderResponse> getOrders() {
        return orders;
    }
}
