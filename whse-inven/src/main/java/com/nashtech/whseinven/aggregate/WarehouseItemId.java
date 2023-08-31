package com.nashtech.whseinven.aggregate;

import lombok.Value;

import java.util.HashSet;
import java.util.Set;

@Value
public class WarehouseItemId {
    String itemId;
    String warehouseLocation;
    public WarehouseItemId(String itemId, String warehouseLocation) {
        this.itemId = itemId;
        this.warehouseLocation = warehouseLocation;
    }

    @Override
    public String toString() {
        Set<String> set = new HashSet<>();
        set.add(itemId);
        set.add(warehouseLocation);
        return String.valueOf(set.hashCode());
    }
}
