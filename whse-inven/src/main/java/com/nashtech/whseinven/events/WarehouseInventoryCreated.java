package com.nashtech.whseinven.events;

import com.nashtech.whseinven.aggregate.WarehouseItemId;
import lombok.Value;

@Value
public class WarehouseInventoryCreated {
    WarehouseItemId itemId;
    Double quantityOnHand;
    Double quantityOnOrder;
}
