package com.nashtech.purchseorders.command;

import com.nashtech.purchseorders.model.LineItem;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Set;

@Value
public class CreatePurchaseOrder {
    @TargetAggregateIdentifier
    String purchaseOrderId;
    Set<LineItem> lineItems;
    String warehouseLocation;
}
