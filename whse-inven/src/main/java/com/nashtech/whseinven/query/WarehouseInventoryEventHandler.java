package com.nashtech.whseinven.query;


import com.nashtech.whseinven.entity.WarehouseInventoryQueryEntity;
import com.nashtech.whseinven.events.ItemReceivedAtWarehouse;
import com.nashtech.whseinven.events.QuantityOnOrderIncreased;
import com.nashtech.whseinven.events.WarehouseInventoryCreated;
import com.nashtech.whseinven.repository.WarehouseInventoryQueryRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;


@Component
public class WarehouseInventoryEventHandler {

    @Autowired
    DataSource dataSource;

    @Autowired
    WarehouseInventoryQueryRepository repository;

    static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup()
            .lookupClass());
    private final CommandGateway commandGateway;

    public WarehouseInventoryEventHandler(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @EventHandler
    protected void on(QuantityOnOrderIncreased event) {
        int rowsUpdated = repository.IncreaseQuantityOnOrder(event.getItemId().toString(), event.getQuantityOnOrder(), event.getPoId());
        if(rowsUpdated > 0){
            logger.info("Updated QuantityOnOrderIncreased for item {}", event.getItemId());
        }
    }

    @EventHandler
    protected void on(WarehouseInventoryCreated event) {
       logger.info("Persisting inventory for item {}", event.getItemId().getItemId());
       WarehouseInventoryQueryEntity entity = new WarehouseInventoryQueryEntity();
       entity.setId(event.getItemId().toString());
       entity.setQuantityOnHand(event.getQuantityOnHand());
       entity.setQuantityOnOnder(event.getQuantityOnOrder());
       entity.setWhseLoc(event.getItemId().getWarehouseLocation());
       entity.setItemId(event.getItemId().getItemId());
       entity.setOpenPurchaseOrders("");
       repository.save(entity);
    }

    @EventHandler
    protected void on(ItemReceivedAtWarehouse event) {
       logger.info("Increase QOH and decrease QOO by {} for item {}", event.getQtyReceived(), event.getItemId().getItemId());
       repository.updateInventory(event.getItemId().toString(), event.getQtyReceived());
    }
}
