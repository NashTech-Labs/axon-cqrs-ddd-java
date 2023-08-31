package com.nashtech.whseinven.events;

import com.nashtech.whseinven.aggregate.WarehouseItemId;
import lombok.Value;

@Value
public class ItemReceivedAtWarehouse {
    WarehouseItemId itemId;
    Double qtyReceived;
}
