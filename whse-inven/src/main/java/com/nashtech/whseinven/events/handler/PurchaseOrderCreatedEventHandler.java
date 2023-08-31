package com.nashtech.whseinven.events.handler;



import com.nashtech.whseinven.aggregate.WarehouseItemId;
import com.nashtech.whseinven.command.IncreaseQuantityOnOrder;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import com.nashtech.purchseorders.events.PurchaseOrderCreated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;


@Component
@ProcessingGroup("purchase")
public class PurchaseOrderCreatedEventHandler {

    static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup()
            .lookupClass());
    private final CommandGateway commandGateway;

    public PurchaseOrderCreatedEventHandler(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @EventHandler
    protected void on(PurchaseOrderCreated event) {
       logger.info("Received event purchaseOrder created with purchseOrderId {}", event.getPurchaseOrderId());
       event.getLineItems().forEach( lineItem -> {
           commandGateway.send(new IncreaseQuantityOnOrder(new WarehouseItemId(lineItem.getItemId(),
                   event.getWarehouseLocation()), lineItem.getQty(), event.getPurchaseOrderId()));
       });
    }
}
