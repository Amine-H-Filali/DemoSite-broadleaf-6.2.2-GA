package com.community.workflow.checkout;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutSeed;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("customDiscountActivity")
public class CustomDiscountActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {

    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        CheckoutSeed seed = context.getSeedData();
        Order order = seed.getOrder();

        // Apply discount if the total is above a certain threshold
        if (order.getItemCount() > 2) {
            Money discount = new Money(order.getTotal().getAmount().multiply(BigDecimal.valueOf(0.1)), order.getTotal().getCurrency());
            Money newTotal = order.getTotal().subtract(discount);
            order.setTotal(newTotal);
        }

        // Continue the workflow
        return context;
    }
}