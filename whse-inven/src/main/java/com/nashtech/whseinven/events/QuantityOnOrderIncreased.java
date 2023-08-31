package com.nashtech.whseinven.events;

import com.nashtech.whseinven.aggregate.WarehouseItemId;
import lombok.Value;

@Value
public class QuantityOnOrderIncreased {
    WarehouseItemId itemId;
    Double quantityOnOrder;
    String poId;
}
