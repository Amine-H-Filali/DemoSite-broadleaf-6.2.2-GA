package com.community.configuration;

import com.community.workflow.checkout.CustomDiscountActivity;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutSeed;
import org.broadleafcommerce.core.workflow.Activity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.broadleafcommerce.core.workflow.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WorkflowConfiguration {

    @Bean
    public Processor<CheckoutSeed, CheckoutSeed> checkoutWorkflow(CustomDiscountActivity customDiscountActivity) {
        List<Activity<ProcessContext<CheckoutSeed>>> activities = new ArrayList<>();
        activities.add(customDiscountActivity);
        // Add other default activities
        return new DefaultProcessor<>(activities);
    }
}
