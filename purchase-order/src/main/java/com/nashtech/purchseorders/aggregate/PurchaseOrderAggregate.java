package com.nashtech.purchseorders.aggregate;

import com.nashtech.purchseorders.command.CreatePurchaseOrder;
import com.nashtech.purchseorders.events.PurchaseOrderCreated;
import com.nashtech.purchseorders.model.LineItem;
import com.nashtech.purchseorders.model.PurchaseOrderStatus;
import com.nashtech.purchseorders.model.Receipt;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Set;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate(snapshotTriggerDefinition = "purchaseOrderAggregateSnapshotTriggerDefinition")
public class PurchaseOrderAggregate {

    private EventGateway eventGateway;

    @AggregateMember
    PurchaseOrderStatus status;
    @AggregateMember
    Set<Receipt> receipts;
    @AggregateIdentifier
    private String purchaseOrderId;
    @AggregateMember
    private Set<LineItem> lineItems;
    @AggregateMember
    private String warehouseLocation;


    @CommandHandler
    public PurchaseOrderAggregate(CreatePurchaseOrder command) {
        apply(new PurchaseOrderCreated(command.getPurchaseOrderId(), command.getLineItems(), command.getWarehouseLocation()));

    }

    @EventSourcingHandler
    public void on(PurchaseOrderCreated event) {
        this.purchaseOrderId = event.getPurchaseOrderId();
        this.warehouseLocation = event.getWarehouseLocation();
        this.status = PurchaseOrderStatus.OPEN;
        this.lineItems = event.getLineItems();
        this.receipts = Set.of();
    }

    protected PurchaseOrderAggregate() {
        //Required by Axon to build a default Aggregate prior to Event Sourcing
    }
}
