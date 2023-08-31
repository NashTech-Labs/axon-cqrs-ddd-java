package com.nashtech.whseinven.aggregate;

import com.nashtech.whseinven.command.CreateWarehouseInventory;
import com.nashtech.whseinven.command.ReceivePOItem;
import com.nashtech.whseinven.command.IncreaseQuantityOnOrder;
import com.nashtech.whseinven.events.ItemReceivedAtWarehouse;
import com.nashtech.whseinven.events.QuantityOnOrderIncreased;
import com.nashtech.whseinven.events.WarehouseInventoryCreated;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate(snapshotTriggerDefinition = "whseAggregateSnapshotTriggerDefinition")
public class WarehouseInventoryAggregate {

    static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup()
            .lookupClass());
    @AggregateIdentifier
    private WarehouseItemId itemId;

    @AggregateMember
    private Double quantityOnHand;
    @AggregateMember
    private Double quantityOnOrder;

    @AggregateMember
     private List<String> openPurchaseOrders;

    @CommandHandler
    public WarehouseInventoryAggregate(CreateWarehouseInventory command) {
        logger.info("Received command CreateWarehouseInventory with itemId {}", command.getItemId());
        apply(new WarehouseInventoryCreated(command.getItemId(), command.getQuantityOnHand(), command.getQuantityOnOrder()));
    }

    @CommandHandler
    public void handle(ReceivePOItem command) {
        apply(new ItemReceivedAtWarehouse(command.getWarehouseItemId(), command.getQtyReceived()));
    }
    @CommandHandler
    public void handle(IncreaseQuantityOnOrder command) {
        apply(new QuantityOnOrderIncreased(command.getWarehouseItemId(), command.getQuantity(), command.getPoId()));
    }

    @EventSourcingHandler
    public void on(WarehouseInventoryCreated event) {
        this.itemId = event.getItemId();
        this.quantityOnHand = event.getQuantityOnHand();
        this.quantityOnOrder = event.getQuantityOnOrder();
        this.openPurchaseOrders = List.of();
    }

    @EventSourcingHandler
    public void on(ItemReceivedAtWarehouse event) {
        this.quantityOnOrder = this.quantityOnOrder - event.getQtyReceived();
        this.quantityOnHand = this.quantityOnHand + event.getQtyReceived();
    }

    protected WarehouseInventoryAggregate() {
        //Required by Axon to build a default Aggregate prior to Event Sourcing
    }

}
