package com.nashtech.whseinven.events.model;

import java.util.HashSet;
import java.util.Set;

public class WarehouseItemId {
    private final String itemId;
    private final String warehouseLocation;
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
