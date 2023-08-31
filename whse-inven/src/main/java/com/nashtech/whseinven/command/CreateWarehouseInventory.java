package com.nashtech.whseinven.command;

import com.nashtech.whseinven.aggregate.WarehouseItemId;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CreateWarehouseInventory {
    @TargetAggregateIdentifier
    WarehouseItemId itemId;
    Double quantityOnHand;
    Double quantityOnOrder;

    public CreateWarehouseInventory(WarehouseItemId itemId) {
        this.itemId = itemId;
        this.quantityOnHand = 0.0;
        this.quantityOnOrder = 0.0;
    }
}
