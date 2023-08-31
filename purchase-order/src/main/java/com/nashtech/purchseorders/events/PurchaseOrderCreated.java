package com.nashtech.purchseorders.events;

import com.nashtech.purchseorders.model.LineItem;
import lombok.Value;

import java.util.Set;

@Value
public class PurchaseOrderCreated {

    String purchaseOrderId;
    Set<LineItem> lineItems;
    String warehouseLocation;
}
