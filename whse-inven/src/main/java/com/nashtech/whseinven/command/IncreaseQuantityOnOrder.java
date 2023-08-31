package com.nashtech.whseinven.command;

import com.nashtech.whseinven.aggregate.WarehouseItemId;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class IncreaseQuantityOnOrder {
    @TargetAggregateIdentifier
    WarehouseItemId warehouseItemId;

    Double quantity;

    String poId;

}
