package com.community.service;

import org.broadleafcommerce.common.extension.ExtensionResultHolder;
import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.OrderServiceExtensionHandler;
import org.broadleafcommerce.core.order.service.OrderServiceImpl;
import org.broadleafcommerce.core.order.service.exception.IllegalCartOperationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;


@Service("OrderServiceDefault")
public class OrderServiceDefault extends OrderServiceImpl {

    @Value("${order.discount.threshold}")
    private BigDecimal discountThreshold;

    @Value("${order.discount.percentage}")
    private BigDecimal discountPercentage;

    @Override
    public void preValidateCartOperation(Order cart) {
        if (cart.getTotal().greaterThan(new Money(discountThreshold, cart.getCurrency()))) {
            Money discount = cart.getTotal().multiply(discountPercentage);
            Money newTotal = cart.getTotal().subtract(discount);
            cart.setTotal(newTotal);
        }

        // Appel à l'extension du service
        ExtensionResultHolder erh = new ExtensionResultHolder();
        ((OrderServiceExtensionHandler) this.extensionManager.getProxy()).preValidateCartOperation(cart, erh);

        // Gestion des exceptions
        if (erh.getThrowable() instanceof IllegalCartOperationException) {
            throw (IllegalCartOperationException) erh.getThrowable();
        } else if (erh.getThrowable() != null) {
            throw new RuntimeException(erh.getThrowable());
        }
    }
}
